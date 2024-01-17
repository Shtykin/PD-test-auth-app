package ru.shtykin.pdauthapp.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shtykin.pdauthapp.domain.AuthRepository
import ru.shtykin.pdauthapp.presentation.state.ScreenState
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState =
        mutableStateOf<ScreenState>(
            ScreenState.AuthScreen(
                phone = null,
                isVisibleCodeField = false,
                phoneError = null,
                codeError = null
            )
        )

    val uiState: State<ScreenState>
        get() = _uiState

    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    fun getCode(
        phone: String,
        onSuccess: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val code = repository.getCode(phone)
                withContext(Dispatchers.Main) {
                    onSuccess.invoke(code)
                    _uiState.value = ScreenState.AuthScreen(
                        phone = phone,
                        isVisibleCodeField = true,
                        phoneError = null,
                        codeError = null
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = ScreenState.AuthScreen(
                        phone = phone,
                        isVisibleCodeField = false,
                        phoneError = e.message,
                        codeError = null
                    )
                }
            }
        }
    }

    fun login(
        phone: String,
        code: String,
        onSuccess: (String) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = repository.getToken(phone, code)
                withContext(Dispatchers.Main) {
                    onSuccess.invoke("Здравствуйте, “$phone”, Ваш токен “$token")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = ScreenState.AuthScreen(
                        phone = phone,
                        isVisibleCodeField = true,
                        phoneError = null,
                        codeError = e.message
                    )
                }
            }
        }
    }

    fun mainScreenOpened(msg: String) {
        _uiState.value = ScreenState.MainScreen(
            msg = msg
        )
    }

    fun getFlagEmoji(phone: String): String {
        try {
            val region = try {
                val parsedPhone = phoneNumberUtil.parse(
                    phone,
                    Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN.name
                )
                phoneNumberUtil.getRegionCodeForCountryCode(parsedPhone.countryCode)
            } catch (e: Exception) {
                Locale.getDefault().country
            }
            if (region.length != 2 || region == "ZZ") {
                return DEFAULT_FLAG_EMOJI
            }
            val countryCodeCaps = region.uppercase(Locale.getDefault())
            val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6
            if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
                return DEFAULT_FLAG_EMOJI
            }
            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        } catch (e: Exception) {
            return DEFAULT_FLAG_EMOJI
        }
    }


    companion object {
        private const val DEFAULT_FLAG_EMOJI = "\uD83C\uDDF7\uD83C\uDDFA"
    }


}