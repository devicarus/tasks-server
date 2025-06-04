package fit.cvut.biejk.domain.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

import fit.cvut.biejk.domain.entity.Project

@ApplicationScoped
class ProjectRepository : PanacheRepository<Project>