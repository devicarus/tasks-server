package fit.cvut.biejk.util

import jakarta.ws.rs.core.NewCookie

object CookieUtils {
    fun createRefreshTokenCookie(value: String, maxAgeSeconds: Int): NewCookie =
        NewCookie.Builder("refresh_token")
        .value(value)
        .path("/api/auth/refresh")
        .maxAge(maxAgeSeconds)
        //.httpOnly(true)
        //.secure(true) // TODO: CHANGE IN PRODUCTION !!!!!!!
        //.sameSite(NewCookie.SameSite.STRICT)
        .build()

    fun clearRefreshTokenCookie(): NewCookie {
        return createRefreshTokenCookie("", 0)
    }
}
