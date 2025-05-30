package fit.cvut.biejk.service

import fit.cvut.biejk.dto.UserDto
import fit.cvut.biejk.exception.AuthException
import fit.cvut.biejk.mapper.toDto
import fit.cvut.biejk.persistance.entity.User
import fit.cvut.biejk.persistance.repository.UserRepository
import fit.cvut.biejk.providers.CurrentUserProvider
import fit.cvut.biejk.util.HashUtils
import fit.cvut.biejk.util.JwtUtils

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UserService(
    val userRepository: UserRepository,
    private val currentUserProvider: CurrentUserProvider
) {
    companion object {
        private const val ACCESS_TOKEN_EXPIRY = 900L // 15 min
        private const val REFRESH_TOKEN_EXPIRY = 7 * 24 * 3600L // 7 days
    }

    @Transactional
    fun createUser(username: String, password: String) {
        userRepository.findByUsername(username)?.let {
            throw IllegalArgumentException("User with username $username already exists")
        }

        val user = User().apply {
            this.username = username
            this.password = HashUtils.hashPassword(password)
        }

        userRepository.persist(user)
    }

    @Transactional
    fun verifyUser(username: String, password: String): User {
        val user: User = userRepository.findByUsername(username)
            ?: throw AuthException("User with username $username not found")

        if (!HashUtils.verifyPassword(user.password, password))
            throw AuthException("Wrong credentials")

        return user
    }

    @Transactional
    fun getCurrentUser(): UserDto {
        return currentUserProvider.getCurrentUser()?.toDto()
            ?: throw AuthException("User with not found")
    }

    @Transactional
    fun getToken(user: User): Pair<String, String> {
        val refreshToken = JwtUtils.generateToken(user.username, emptyList(), REFRESH_TOKEN_EXPIRY)
        val accessToken = JwtUtils.generateToken(user.username, listOf("User"), ACCESS_TOKEN_EXPIRY)
        return Pair(refreshToken, accessToken)
    }

    @Transactional
    fun refreshToken(user: User): String {
        val accessToken = JwtUtils.generateToken(user.username, listOf("User"), ACCESS_TOKEN_EXPIRY)
        return accessToken
    }

}