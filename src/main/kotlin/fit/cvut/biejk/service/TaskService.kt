package fit.cvut.biejk.service

import fit.cvut.biejk.dto.TaskDto
import fit.cvut.biejk.mapper.toDto
import fit.cvut.biejk.mapper.toEntity
import fit.cvut.biejk.mapper.update
import fit.cvut.biejk.persistance.entity.Task
import fit.cvut.biejk.persistance.repository.TaskRepository
import fit.cvut.biejk.filtering.Filter
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class TaskService (
    val taskRepository: TaskRepository,
    val userService: UserService
) {

    @Transactional
    fun getCurrentUserTasks(sort: Sort? = null, filter: Filter<Task>? = null): List<Task> {
        return taskRepository.list(
            "user = :user" + (if (filter != null) " AND $filter" else ""), sort,
            mapOf(
                "user" to userService.getUser(),
            ) + (filter?.parameters ?: emptyMap())
        )
    }

    @Transactional
    fun createTask(taskDto: TaskDto): TaskDto {
        val task = taskDto.toEntity()
        task.user = userService.getUser()
        taskRepository.persist(task)
        return task.toDto()
    }

    @Transactional
    fun updateTask(id: Long, taskDto: TaskDto) {
        val task = taskRepository.findById(id);
        if (task == null || task.user != userService.getUser())
            throw IllegalArgumentException();
        task.update(taskDto);
        taskRepository.persist(task);
    }

    @Transactional
    fun deleteTask(id: Long) {
        val task = taskRepository.findById(id);
        if (task == null || task.user != userService.getUser())
            throw IllegalArgumentException();
        taskRepository.delete(task);
    }

}