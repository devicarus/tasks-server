package fit.cvut.biejk.resource

import fit.cvut.biejk.domain.dto.TaskDto
import fit.cvut.biejk.domain.mapper.toDto
import fit.cvut.biejk.domain.entity.Task
import fit.cvut.biejk.service.TaskService
import fit.cvut.biejk.filtering.Filter
import io.quarkus.panache.common.Sort
import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/tasks")
@RolesAllowed("User")
class TaskResource(
    val taskService: TaskService,
) {
    companion object {
        private val ALLOWED_SORT_FIELDS: List<String> = listOf("dueDate", "deadlineDate")
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getCurrentUserTasks(
        @QueryParam(value="sortBy") sortBy: String?,
        @QueryParam(value="sortDir") sortDir: String?,
        @QueryParam(value="filter") filterBy: String?
    ): List<TaskDto>  {
        if (!sortBy.isNullOrEmpty() && !ALLOWED_SORT_FIELDS.contains(sortBy))
            throw IllegalArgumentException("Invalid sort field: $sortBy")

        if (!sortDir.isNullOrEmpty() && !listOf("asc", "desc").contains(sortDir.lowercase()))
            throw IllegalArgumentException("Invalid sort direction: $sortDir")

        val filter = filterBy?.let { Filter.from(Task::class, it) }

        val sortDirection = if (sortDir?.lowercase() == "desc") {
            Sort.Direction.Descending
        } else {
            Sort.Direction.Ascending
        }

        val sort: Sort? = sortBy?.let { Sort.by(sortBy, sortDirection) }

        return taskService.getCurrentUserTasks(sort, filter).map { it.toDto() }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createTask(taskDto: TaskDto): Response {
        val newTaskDto = taskService.createTask(taskDto)
        return Response.status(Response.Status.CREATED).entity(newTaskDto).build()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    fun updateTask(@PathParam("id") id: Long, taskDto: TaskDto): Response {
        taskService.updateTask(id, taskDto)
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteTask(@PathParam("id") id: Long): Response {
        taskService.deleteTask(id)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}