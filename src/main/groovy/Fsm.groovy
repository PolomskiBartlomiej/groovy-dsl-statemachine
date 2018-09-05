import groovy.transform.Immutable


@Immutable
class Fsm {

    Map transitions

    static def load(@DelegatesTo(value = FsmBuilder,strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def fsmBuilder = new FsmBuilder()
        def fmsRecipe = closure.rehydrate(fsmBuilder, this, fsmBuilder)
        fmsRecipe.resolveStrategy = Closure.DELEGATE_ONLY
        fmsRecipe().build()
    }

    @Override
    String toString() {
        "Fsm : $transitions"
    }

    def methodMissing(String name, def args) {
       throw new FsmTransitionNotSupported(name)
    }

    def propertyMissing(String name) {
        throw new FsmWordNotSupported()
    }

    def propertyMissing(String name, def arg) {
        throw new FsmWordNotSupported()
    }
}
