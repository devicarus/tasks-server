package fit.cvut.biejk.exception.mapper

import fit.cvut.biejk.domain.dto.ErrorResponse
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class IllegalArgumentExceptionMapper : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(ErrorResponse(exception.message ?: "Bad request"))
            .build()
    }
}