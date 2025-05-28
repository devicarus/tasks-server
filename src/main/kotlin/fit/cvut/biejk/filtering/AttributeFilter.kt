package fit.cvut.biejk.filtering

import jakarta.persistence.Column
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

data class AttributeFilter<T : Any>(
    val clazz: KClass<T>,
    val attribute: String,
    val operator: String,
    val value: String,
) {

    init {
        if (property == null) {
            throw IllegalArgumentException("Invalid attribute: $attribute")
        }
        if (value.isBlank()) {
            throw IllegalArgumentException("Value cannot be blank")
        }
    }

    private val property: KProperty1<T, *>?
        get() = clazz.memberProperties.find {
            val filterName: String = it.findAnnotation<Filterable>()?.name
                ?: return@find false
            if (filterName.isNotBlank()) {
                return@find filterName == attribute
            } else {
                return@find it.name == attribute
            }
        }

    private val columnName: String
        get() = property!!.findAnnotation<Column>()?.name ?: property!!.name


    override fun toString(): String = when {
        operator == "~" -> "$columnName LIKE '%' || :$columnName || '%'"
        operator == "=" && value == "NULL" -> "$columnName IS NULL"
        else -> "$columnName $operator :$columnName"
    }

    val parameter
        get(): Pair<String, Any>? {
            if (operator == "=" && value == "NULL") return null
            val value = when (property?.returnType?.classifier) {
                Boolean::class -> value.toBoolean()
                LocalDate::class -> LocalDate.parse(value)
                String::class -> value
                else -> throw IllegalArgumentException("Unsupported type: ${property?.returnType}")
            }
            return columnName to value
        }

}
