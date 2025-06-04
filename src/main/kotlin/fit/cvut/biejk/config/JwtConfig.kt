package fit.cvut.biejk.config

import io.smallrye.config.ConfigMapping
import jakarta.enterprise.context.ApplicationScoped

@ConfigMapping(prefix = "jwt")
@ApplicationScoped
interface JwtConfig {
    fun accessTokenExpiry(): Int
    fun refreshTokenExpiry(): Int
}