package fit.cvut.biejk.domain.entity

import fit.cvut.biejk.filtering.Filterable
import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "tasks")
class Task(
    @Column(name = NAME)
    @Filterable
    var name: String? = null,

    @Column(name = DONE)
    @Filterable
    var done: Boolean = false,

    @Column(name = NOTE)
    var note: String? = null,

    @Column(name = DUE_DATE)
    @Filterable("due")
    var dueDate: LocalDate? = null,

    @Column(name = DEADLINE_DATE)
    @Filterable("deadline")
    var deadlineDate: LocalDate? = null,
) : PanacheEntity() {

    companion object {
        private const val NAME = "name"
        private const val DONE = "done"
        private const val NOTE = "note"
        private const val DUE_DATE = "due_date"
        private const val DEADLINE_DATE = "deadline_date"
    }

    @ManyToOne
    lateinit var user: User

    @ManyToOne
    var project: Project? = null
}
