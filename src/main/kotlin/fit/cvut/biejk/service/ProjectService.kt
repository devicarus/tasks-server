package fit.cvut.biejk.service

import fit.cvut.biejk.dto.ProjectDto
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
    fun createProject(projectDto: ProjectDto) {
        val project = projectDto.toEntity()
        project.user = userService.getUser()
        projectRepository.persist(project)
    }

    @Transactional
    fun getProjects(): List<ProjectDto> {
        return projectRepository.find("user", userService.getUser())
            .list<Project>().map { it.toDto() }
    }

}