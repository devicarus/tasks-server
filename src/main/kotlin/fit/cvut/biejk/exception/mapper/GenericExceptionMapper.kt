package fit.cvut.biejk.exception.mapper

import fit.cvut.biejk.domain.dto.ErrorResponse
import io.quarkus.runtime.configuration.ConfigUtils
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GenericExceptionMapper : ExceptionMapper<Throwable> {
    override fun toResponse(exception: Throwable): Response {
        val profiles = ConfigUtils.getProfiles()
        val isDevMode = profiles.contains("dev")
        val errorMessage: String = if (isDevMode && !exception.message.isNullOrEmpty()) exception.message!! else "An unexpected error occurred"
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ErrorResponse(errorMessage))
            .build()
    }
}
