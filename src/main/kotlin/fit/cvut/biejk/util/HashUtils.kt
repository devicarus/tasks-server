package fit.cvut.biejk.util

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory

// following parameters recommended by OWASP (https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#argon2id)

object HashUtils {

    private val argon2: Argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id)

    fun hashPassword(password: String): String {
        return argon2.hash(2, 19456, 1, password.toCharArray())
    }

    fun verifyPassword(hash: String, password: String): Boolean {
        return argon2.verify(hash, password.toCharArray())
    }

}