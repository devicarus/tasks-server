package fit.cvut.biejk.domain.mapper

import fit.cvut.biejk.domain.dto.UserDto
import fit.cvut.biejk.domain.entity.User

fun User.toDto(): UserDto = UserDto(
    username = this.username
)