package fit.cvut.biejk.domain.entity

import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.*

@Entity
@Table(name = "projects")
class Project(
    var name: String? = null
) : PanacheEntity() {

    @ManyToOne
    lateinit var user: User

    @OneToMany(mappedBy = "project", cascade = [CascadeType.REMOVE])
    var tasks: MutableList<Task> = mutableListOf()
}
