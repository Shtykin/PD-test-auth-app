package ru.shtykin.pdauthapp.navigation

sealed class Screen(
    val route: String
) {
    object Auth: Screen(ROUTE_AUTH)
    object Main: Screen(ROUTE_MAIN)

    private companion object {
        const val ROUTE_AUTH = "route_auth"
        const val ROUTE_MAIN = "route_main"
    }
}
