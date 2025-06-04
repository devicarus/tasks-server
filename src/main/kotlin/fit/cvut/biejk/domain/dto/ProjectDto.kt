package fit.cvut.biejk.domain.dto

data class ProjectDto(
    val id: Long,
    val name: String?,
    val tasks: List<TaskDto>
)
