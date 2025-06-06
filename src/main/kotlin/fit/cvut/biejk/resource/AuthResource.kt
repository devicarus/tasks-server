package fit.cvut.biejk.resource

import fit.cvut.biejk.config.JwtConfig
import fit.cvut.biejk.domain.dto.Credentials
import fit.cvut.biejk.domain.dto.TokenResponse
import fit.cvut.biejk.exception.AuthException
import fit.cvut.biejk.domain.repository.UserRepository
import fit.cvut.biejk.service.UserService
import fit.cvut.biejk.util.CookieUtils
import fit.cvut.biejk.util.JwtUtils
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/auth")
class AuthResource(
    private val jwtConfig: JwtConfig,
    private val userService: UserService,
    private val userRepository: UserRepository
) {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun token(credentials: Credentials): Response {
        val user = userService.verifyUser(credentials.username, credentials.password)
        val tokens = userService.getToken(user)

        val refreshCookie = CookieUtils.createRefreshTokenCookie(tokens.first, jwtConfig.refreshTokenExpiry())

        return Response.ok(TokenResponse(tokens.second)).cookie(refreshCookie).build()
    }

    @DELETE
    @Path("/token")
    fun deleteToken(): Response {
        val clearCookie = CookieUtils.clearRefreshTokenCookie()
        return Response.noContent().cookie(clearCookie).build()
    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    fun refresh(@CookieParam("refresh_token") refreshToken: String?): Response {
        if (refreshToken.isNullOrBlank())
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(AuthException("Refresh token missing")).build()

        val jwt = JwtUtils.parseToken(refreshToken)

        val user = userRepository.findByUsername(jwt!!.subject)
            ?: return Response.status(Response.Status.UNAUTHORIZED).build()

        val accessToken = userService.refreshToken(user)
        return Response.ok(TokenResponse(accessToken)).build()
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun register(credentials: Credentials): Response {
        userService.createUser(credentials.username, credentials.password)
        return Response.status(Response.Status.CREATED).build()
    }

}