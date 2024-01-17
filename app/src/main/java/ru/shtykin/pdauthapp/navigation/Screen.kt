package ru.shtykin.pdauthapp.navigation

sealed class Screen(
    val route: String
) {
    object Auth: Screen(ROUTE_AUTH)

    private companion object {
        const val ROUTE_AUTH = "route_auth"
    }
}
