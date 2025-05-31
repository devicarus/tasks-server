package fit.cvut.biejk.mapper

import fit.cvut.biejk.dto.UserDto
import fit.cvut.biejk.persistance.entity.User

fun User.toDto(): UserDto = UserDto(
    username = this.username
)