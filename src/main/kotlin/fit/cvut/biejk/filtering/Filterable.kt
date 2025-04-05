package fit.cvut.biejk.filtering

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Filterable(val name: String = "")
