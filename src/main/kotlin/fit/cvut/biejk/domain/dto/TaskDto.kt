package fit.cvut.biejk.domain.dto

import java.time.LocalDate

data class TaskDto(
    val id: Long,
    val name: String?,
    val done: Boolean,
    val note: String?,
    val dueDate: LocalDate?,
    val deadlineDate: LocalDate?,
)
