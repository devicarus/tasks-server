package fit.cvut.biejk.persistance.entity

import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.*

@Entity
@Table(name = "projects")
class Project(
    var name: String = ""
) : PanacheEntity() {

    @ManyToOne
    lateinit var user: User

    @OneToMany(mappedBy = "project")
    var tasks: MutableList<Task> = mutableListOf()
}
