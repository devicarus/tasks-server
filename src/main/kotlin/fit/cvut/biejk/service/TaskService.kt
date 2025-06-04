package fit.cvut.biejk.service

import fit.cvut.biejk.domain.dto.TaskDto
import fit.cvut.biejk.domain.mapper.toDto
import fit.cvut.biejk.domain.mapper.toEntity
import fit.cvut.biejk.domain.mapper.update
import fit.cvut.biejk.domain.entity.Task
import fit.cvut.biejk.domain.repository.TaskRepository
import fit.cvut.biejk.filtering.Filter
import fit.cvut.biejk.domain.repository.ProjectRepository
import fit.cvut.biejk.providers.CurrentUserProvider
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class TaskService (
    val taskRepository: TaskRepository,
    val projectRepository: ProjectRepository,
    val userService: UserService,
    private val currentUserProvider: CurrentUserProvider
) {

    @Transactional
    fun getCurrentUserTasks(sort: Sort? = null, filter: Filter<Task>? = null): List<Task> {
        return taskRepository.list(
            "user = :user" + (if (filter != null) " AND $filter" else ""), sort,
            mapOf(
                "user" to currentUserProvider.getCurrentUser(),
            ) + (filter?.parameters ?: emptyMap())
        )
    }

    @Transactional
    fun createTask(taskDto: TaskDto): TaskDto {
        val task = taskDto.toEntity()
        task.user = currentUserProvider.getCurrentUser()!!
        taskRepository.persist(task)
        return task.toDto()
    }

    @Transactional
    fun createProjectTask(projectId: Long, taskDto: TaskDto): TaskDto {
        val project = projectRepository.findById(projectId)
        val user = currentUserProvider.getCurrentUser()!!

        if (project == null || project.user != user)
            throw IllegalArgumentException("Project not found")

        val task = taskDto.toEntity()
        task.user = user
        task.project = project
        taskRepository.persist(task)
        return task.toDto()
    }

    @Transactional
    fun updateTask(id: Long, taskDto: TaskDto) {
        val task = taskRepository.findById(id);
        if (task == null || task.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Task not found");
        task.update(taskDto);
        taskRepository.persist(task);
    }

    @Transactional
    fun deleteTask(id: Long) {
        val task = taskRepository.findById(id);
        if (task == null || task.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Task not found");
        taskRepository.delete(task);
    }

}