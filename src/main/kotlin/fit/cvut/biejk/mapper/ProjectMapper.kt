package fit.cvut.biejk.mapper

import fit.cvut.biejk.dto.ProjectDto
import fit.cvut.biejk.persistance.entity.Project


fun Project.toDto(): ProjectDto = ProjectDto(
    id = this.id,
    name = this.name
)

fun Project.patchWith(dto: ProjectDto) {
    dto.id?.let { this.id = it }
    dto.name?.let { this.name = it }
}

fun ProjectDto.toEntity(): Project {
    val entity = Project()
    entity.patchWith(this)
    return entity
}