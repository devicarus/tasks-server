package fit.cvut.biejk.mapper

import fit.cvut.biejk.dto.ProjectBriefDto
import fit.cvut.biejk.dto.ProjectDto
import fit.cvut.biejk.persistance.entity.Project

fun Project.toDto(): ProjectDto = ProjectDto(
    id = this.id,
    name = this.name,
    tasks = this.tasks.map { task -> task.toDto() }
)

fun Project.toBriefDto(): ProjectBriefDto = ProjectBriefDto(
    id = this.id,
    name = this.name
)

fun Project.patchWith(dto: ProjectBriefDto) {
    dto.id?.let { this.id = it }
    this.name = dto.name
}

fun ProjectBriefDto.toEntity(): Project {
    val entity = Project()
    entity.patchWith(this)
    return entity
}