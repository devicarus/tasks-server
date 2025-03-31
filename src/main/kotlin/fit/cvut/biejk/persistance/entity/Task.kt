package fit.cvut.biejk.persistance.entity

import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "tasks")
class Task(
    var name: String? = null,
    var done: Boolean = false,
    var note: String? = null,
    var dueDate: LocalDate? = null,
    var deadlineDate: LocalDate? = null,
) : PanacheEntity() {

    @ManyToOne
    lateinit var user: User

    @ManyToOne
    var project: Project? = null

}