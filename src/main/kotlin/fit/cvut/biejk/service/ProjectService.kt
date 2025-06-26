package fit.cvut.biejk.service

import jakarta.transaction.Transactional
import jakarta.enterprise.context.ApplicationScoped

import fit.cvut.biejk.domain.dto.ProjectBriefDto
import fit.cvut.biejk.domain.dto.ProjectDto
import fit.cvut.biejk.domain.mapper.patchWith
import fit.cvut.biejk.domain.mapper.toBriefDto
import fit.cvut.biejk.domain.mapper.toDto
import fit.cvut.biejk.domain.mapper.toEntity
import fit.cvut.biejk.domain.mapper.update
import fit.cvut.biejk.domain.repository.ProjectRepository
import fit.cvut.biejk.domain.entity.Project
import fit.cvut.biejk.providers.CurrentUserProvider

@ApplicationScoped
class ProjectService(
    val projectRepository: ProjectRepository,
    private val currentUserProvider: CurrentUserProvider,
) {

    @Transactional
    fun createProject(projectDto: ProjectBriefDto): ProjectBriefDto {
        val project = projectDto.toEntity()
        project.user = currentUserProvider.getCurrentUser()!!
        projectRepository.persist(project)
        return project.toBriefDto()
    }

    @Transactional
    fun getProjects(): List<ProjectBriefDto> {
        return projectRepository.find("user", currentUserProvider.getCurrentUser())
            .list<Project>().map { it.toBriefDto() }
    }

    @Transactional
    fun getProject(id: Long): ProjectDto {
        val project = projectRepository.findById(id)
        if (project == null || project.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Project not found")
        return project.toDto()
    }

    @Transactional
    fun updateProject(id: Long, projectDto: ProjectBriefDto) {
        val project = projectRepository.findById(id)
        if (project == null || project.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Project not found")
        project.update(projectDto)
        projectRepository.persist(project)
    }

    @Transactional
    fun patchProject(id: Long, projectDto: ProjectBriefDto) {
        val project = projectRepository.findById(id)
        if (project == null || project.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Project not found")
        project.patchWith(projectDto)
        projectRepository.persist(project)
    }

    @Transactional
    fun deleteProject(id: Long) {
        val project = projectRepository.findById(id)
        if (project == null || project.user != currentUserProvider.getCurrentUser())
            throw IllegalArgumentException("Project not found")
        projectRepository.delete(project)
    }

}