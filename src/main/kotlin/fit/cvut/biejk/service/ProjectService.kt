package fit.cvut.biejk.service

import fit.cvut.biejk.dto.ProjectBriefDto
import fit.cvut.biejk.dto.ProjectDto
import fit.cvut.biejk.mapper.toBriefDto
import fit.cvut.biejk.mapper.toDto
import fit.cvut.biejk.mapper.toEntity
import jakarta.enterprise.context.ApplicationScoped

import fit.cvut.biejk.persistance.repository.ProjectRepository
import fit.cvut.biejk.persistance.entity.Project
import jakarta.transaction.Transactional

@ApplicationScoped
class ProjectService(
    val userService: UserService,
    val projectRepository: ProjectRepository
) {

    @Transactional
    fun createProject(projectDto: ProjectBriefDto) {
        val project = projectDto.toEntity()
        project.user = userService.getUser()
        projectRepository.persist(project)
    }

    @Transactional
    fun getProjects(): List<ProjectBriefDto> {
        return projectRepository.find("user", userService.getUser())
            .list<Project>().map { it.toBriefDto() }
    }

    @Transactional
    fun getProject(id: Long): ProjectDto {
        val project = projectRepository.findById(id)
        if (project.user != userService.getUser())
            throw IllegalArgumentException("Project with id $id does not exist")
        return project.toDto()
    }

}