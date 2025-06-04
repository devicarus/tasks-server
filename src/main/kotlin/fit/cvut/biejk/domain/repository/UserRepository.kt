package fit.cvut.biejk.domain.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

import fit.cvut.biejk.domain.entity.User

@ApplicationScoped
class UserRepository : PanacheRepository<User> {
    fun findByUsername(username: String): User? {
        return find("username", username).firstResult()
    }
}