package fit.cvut.biejk.domain.mapper

import fit.cvut.biejk.domain.dto.ProjectBriefDto
import fit.cvut.biejk.domain.dto.ProjectDto
import fit.cvut.biejk.domain.entity.Project

fun Project.toDto(): ProjectDto = ProjectDto(
    id = this.id,
    name = this.name,
    tasks = this.tasks.map { task -> task.toDto() }
)

fun Project.toBriefDto(): ProjectBriefDto = ProjectBriefDto(
    id = this.id,
    name = this.name
)

fun Project.update(dto: ProjectBriefDto) {
    this.name = dto.name;
}

fun Project.patchWith(dto: ProjectBriefDto) {
    dto.name?.let { this.name = it }
}

fun ProjectBriefDto.toEntity(): Project {
    val entity = Project()
    entity.update(this)
    return entity
}