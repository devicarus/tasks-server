package fit.cvut.biejk.resource

import fit.cvut.biejk.dto.ProjectBriefDto
import fit.cvut.biejk.dto.TaskDto
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

import fit.cvut.biejk.service.ProjectService
import fit.cvut.biejk.service.TaskService
import jakarta.annotation.security.RolesAllowed

@Path("/projects")
@RolesAllowed("User")
class ProjectResource(
    private val projectService: ProjectService,
    private val taskService: TaskService,
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createProject(projectDto: ProjectBriefDto): Response {
        val newProjectDto = projectService.createProject( projectDto )
        return Response.status(Response.Status.CREATED).entity(newProjectDto).build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getProjects(): Response {
        return Response.status(Response.Status.OK)
            .entity(projectService.getProjects())
            .build()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    fun getProject(@PathParam("id") id: Long): Response {
        return Response.status(Response.Status.OK)
            .entity(projectService.getProject(id))
            .build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    fun updateProject(@PathParam("id") id: Long, projectDto: ProjectBriefDto): Response {
        projectService.updateProject(id, projectDto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    fun patchProject(@PathParam("id") id: Long, projectDto: ProjectBriefDto): Response {
        projectService.patchProject(id, projectDto);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    fun deleteProject(@PathParam("id") id: Long): Response {
        projectService.deleteProject(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tasks")
    fun createProjectTask(@PathParam("id") id: Long, taskDto: TaskDto): Response {
        val newTaskDto = taskService.createProjectTask(id, taskDto)
        return Response.status(Response.Status.CREATED).entity(newTaskDto).build()
    }

}