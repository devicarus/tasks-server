package fit.cvut.biejk.dto

data class ProjectDto(
    val id: Long,
    val name: String?,
    val tasks: List<TaskDto>
)
