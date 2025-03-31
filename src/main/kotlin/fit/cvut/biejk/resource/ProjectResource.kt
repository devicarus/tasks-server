package fit.cvut.biejk.resource

import fit.cvut.biejk.dto.ProjectDto
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

import fit.cvut.biejk.service.ProjectService
import jakarta.annotation.security.RolesAllowed

@Path("/projects")
@RolesAllowed("User")
class ProjectResource(
    private val projectService: ProjectService
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createProject(projectDto: ProjectDto): Response {
        projectService.createProject( projectDto )
        return Response.ok().build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getProjects(): Response {
        return Response.status(Response.Status.OK)
            .entity(projectService.getProjects())
            .build()
    }

}