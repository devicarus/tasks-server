package fit.cvut.biejk.domain.repository

import fit.cvut.biejk.domain.entity.Task
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TaskRepository : PanacheRepository<Task>