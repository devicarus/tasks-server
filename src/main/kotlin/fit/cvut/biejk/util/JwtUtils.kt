package fit.cvut.biejk.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.io.InputStream
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.util.*


object JwtUtils {
    private val publicKey: PublicKey = loadPublicKey("META-INF/resources/publicKey.pem")
    private val privateKey: PrivateKey = loadPrivateKey("META-INF/resources/privateKey.pem")

    fun generateToken(username: String, roles: List<String> = emptyList(), expiresIn: Int): String {
        val jwtBuilder = Jwts.builder()
            .issuer("TasksServer")
            .subject(username)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusSeconds(expiresIn.toLong())))

        jwtBuilder.header().add("typ", "JWT")

        if (roles.isNotEmpty()) {
            jwtBuilder.claim("groups", HashSet(roles))
        }

        return jwtBuilder.signWith(privateKey).compact()
    }

    private fun loadKey(filename: String?, keyType: String): Any {
        val inputStream: InputStream? = JwtUtils::class.java.classLoader.getResourceAsStream(filename)
        val keyBytes = inputStream!!.readAllBytes()
        val keyPEM = String(keyBytes).replace("-----BEGIN $keyType KEY-----", "")
            .replace(System.lineSeparator().toRegex(), "")
            .replace("-----END $keyType KEY-----", "")

        val decoded = Base64.getDecoder().decode(keyPEM)
        val kf = KeyFactory.getInstance("RSA")

        return when (keyType) {
            "PRIVATE" -> {
                val spec = PKCS8EncodedKeySpec(decoded)
                kf.generatePrivate(spec)
            }
            "PUBLIC" -> {
                val spec = X509EncodedKeySpec(decoded)
                kf.generatePublic(spec)
            }
            else -> throw IllegalArgumentException("Invalid key type")
        }
    }

    private fun loadPrivateKey(filename: String?): PrivateKey {
        return loadKey(filename, "PRIVATE") as PrivateKey
    }

    private fun loadPublicKey(filename: String?): PublicKey {
        return loadKey(filename, "PUBLIC") as PublicKey
    }

    fun parseToken(token: String): Claims? {
        return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).payload
    }
}