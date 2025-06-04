package fit.cvut.biejk.domain.entity

import jakarta.persistence.*
import io.quarkus.hibernate.orm.panache.PanacheEntity

@Entity
@Table(name = "users")
class User : PanacheEntity() {
    @Column(unique = true)
    lateinit var username: String
    lateinit var password: String
}
