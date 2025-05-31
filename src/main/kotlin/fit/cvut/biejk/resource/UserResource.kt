package fit.cvut.biejk.resource

import fit.cvut.biejk.dto.UserDto
import fit.cvut.biejk.service.UserService
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/users")
@RolesAllowed("User")
@Produces(MediaType.APPLICATION_JSON)
class UserResource(
    private val userService: UserService,
) {
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCurrentUserInfo(): UserDto = userService.getCurrentUser()
}