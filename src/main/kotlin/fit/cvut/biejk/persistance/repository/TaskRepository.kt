package fit.cvut.biejk.persistance.repository

import fit.cvut.biejk.persistance.entity.Task
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TaskRepository : PanacheRepository<Task>