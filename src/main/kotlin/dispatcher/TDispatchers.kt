package dispatcher

sealed class TDispatchers {
    object Main : TDispatchers()
    object Parallel : TDispatchers()
    object Serial : TDispatchers()

    override fun toString(): String {
        return super.toString()
    }
}