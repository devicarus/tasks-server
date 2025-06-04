package fit.cvut.biejk.exception.mapper

import fit.cvut.biejk.domain.dto.ErrorResponse
import fit.cvut.biejk.exception.AuthException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class AuthExceptionMapper : ExceptionMapper<AuthException> {
    override fun toResponse(exception: AuthException): Response {
        return Response.status(Response.Status.UNAUTHORIZED)
            .entity(ErrorResponse(exception.message?: "Wrong credentials"))
            .build()
    }
}