package fit.cvut.biejk.persistance.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

import fit.cvut.biejk.persistance.entity.Project

@ApplicationScoped
class ProjectRepository : PanacheRepository<Project>