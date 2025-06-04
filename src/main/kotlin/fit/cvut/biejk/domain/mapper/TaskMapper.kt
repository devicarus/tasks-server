package fit.cvut.biejk.domain.mapper

import fit.cvut.biejk.domain.dto.TaskDto
import fit.cvut.biejk.domain.entity.Task

fun Task.toDto(): TaskDto = TaskDto(
    id = this.id,
    name = this.name,
    done = this.done,
    note = this.note,
    dueDate = this.dueDate,
    deadlineDate = this.deadlineDate,
)

fun Task.update(dto: TaskDto) {
    this.name = dto.name
    this.done = dto.done
    this.note = dto.note
    this.dueDate = dto.dueDate
    this.deadlineDate = dto.deadlineDate
}

fun TaskDto.toEntity(): Task {
    val entity = Task()
    entity.update(this)
    return entity
}
