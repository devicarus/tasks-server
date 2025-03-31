package fit.cvut.biejk.service

import fit.cvut.biejk.dto.TaskDto
import fit.cvut.biejk.mapper.toDto
import fit.cvut.biejk.mapper.toEntity
import fit.cvut.biejk.mapper.update
import fit.cvut.biejk.persistance.entity.Task
import fit.cvut.biejk.persistance.repository.TaskRepository
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.time.LocalDate

@ApplicationScoped
class TaskService (
    val taskRepository: TaskRepository,
    val userService: UserService
) {

    @Transactional
    fun getCurrentUserTasks(sort: Sort): List<Task> {
        return taskRepository.list("user = :user AND deadlineDate = :deadlineDate", sort,
            mapOf(
                "user" to userService.getUser(),
                "deadlineDate" to LocalDate.of(2025, 4, 12)
            )
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