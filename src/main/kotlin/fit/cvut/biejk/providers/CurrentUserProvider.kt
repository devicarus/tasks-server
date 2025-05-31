package fit.cvut.biejk.providers

import fit.cvut.biejk.persistance.entity.User
import fit.cvut.biejk.persistance.repository.UserRepository
import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class CurrentUserProvider(
    val securityIdentity: SecurityIdentity,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun getCurrentUser(): User? {
        val username = securityIdentity.principal.name
        return userRepository.findByUsername(username)
    }
}