package fit.cvut.biejk.resource

import fit.cvut.biejk.dto.TaskDto
import fit.cvut.biejk.mapper.toDto
import fit.cvut.biejk.service.TaskService
import io.quarkus.panache.common.Sort
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/tasks")
class TaskResource(
    val taskService: TaskService,
) {
    private val ALLOWED_SORT_FIELDS: List<String> = listOf("dueDate", "deadlineDate")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("User")
    fun getCurrentUserTasks(
        @QueryParam(value="sortBy") sortBy: String?,
        @QueryParam(value="sortDir") sortDir: String?,
        @QueryParam(value="filter") filter: String?
    ): List<TaskDto>  {
        if (!sortBy.isNullOrEmpty() && !ALLOWED_SORT_FIELDS.contains(sortBy))
            throw IllegalArgumentException("Invalid sort field: $sortBy")

        if (!sortDir.isNullOrEmpty() && !listOf("asc", "ASC", "desc", "DESC").contains(sortDir))
            throw IllegalArgumentException("Invalid sort direction: $sortDir")

        val match = filter?.let {
            Regex("^(?<attribute>deadlineDate|dueDate)(?<operator>[=<>])(?<value>\\w+)\$").matchEntire(it)
        }

        if (!filter.isNullOrEmpty() && match == null)
            throw IllegalArgumentException("Invalid filter format: $filter")


        val sortDirection = if (sortDir?.lowercase() == "desc") {
            Sort.Direction.Descending
        } else {
            Sort.Direction.Ascending
        }

        val sort = if (!sortBy.isNullOrEmpty()) {
            Sort.by(sortBy, sortDirection)
        } else {
            Sort.by("id", sortDirection)
        }

        return taskService.getCurrentUserTasks(sort).map { it.toDto() }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("User")
    fun createTask(taskDto: TaskDto): Response {
        val newTaskDto = taskService.createTask(taskDto)
        return Response.status(Response.Status.CREATED).entity(newTaskDto).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("User")
    @Path("/{id}")
    fun updateTask(@PathParam("id") id: Long, taskDto: TaskDto): Response {
        taskService.updateTask(id, taskDto)
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    @DELETE
    @RolesAllowed("User")
    @Path("/{id}")
    fun deleteTask(@PathParam("id") id: Long): Response {
        taskService.deleteTask(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}