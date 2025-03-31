package fit.cvut.biejk.resource

import fit.cvut.biejk.dto.Credentials
import fit.cvut.biejk.dto.TokenResponse
import fit.cvut.biejk.persistance.repository.UserRepository
import fit.cvut.biejk.service.UserService
import fit.cvut.biejk.util.JwtUtils
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.NewCookie
import jakarta.ws.rs.core.Response

@Path("/auth")
class AuthResource(
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

        val refreshCookie = NewCookie.Builder("refresh_token")
            .value(tokens.first)
            .path("/api/auth/refresh")
            .maxAge(7 * 24 * 3600)
            //.httpOnly(true)
            //.secure(true) // TODO: CHANGE IN PRODUCTION !!!!!!!
            //.sameSite(NewCookie.SameSite.STRICT)
            .build()

        return Response.ok(TokenResponse(tokens.second)).cookie(refreshCookie).build()
    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    fun refresh(@CookieParam("refresh_token") refreshToken: String): Response {
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