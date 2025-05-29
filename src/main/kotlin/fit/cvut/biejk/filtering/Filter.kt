package fit.cvut.biejk.filtering

import kotlin.reflect.KClass

data class Filter<T : Any>(
    private val filters: List<AttributeFilter<T>>,
) {
    val parameters: Map<String, Any> = filters.mapNotNull { it.parameter }.toMap()

    override fun toString(): String = filters.joinToString(separator = " AND ") { it.toString() }

    companion object {
        private val OPERATORS = listOf("<=", ">=", "=", "<", ">", "~")

        private val PATTERN: Regex = Regex("(?<=^|;)(?<attribute>\\w+)(?<operator>${OPERATORS.joinToString(separator = "|")})(?<value>'[^']*'|\\w+)(?=$|;)")

        fun <T : Any> from(clazz: KClass<T>, filter: String): Filter<T> {
            val matches = PATTERN.findAll(filter)

            val filters = matches.map {
                val attribute = it.groups["attribute"]!!.value
                val operator = it.groups["operator"]!!.value
                val value = it.groups["value"]!!.value.removeSurrounding("'")

                AttributeFilter(clazz, attribute, operator, value)
            }.toList()

            return Filter(filters)
        }
    }
}
