import groovy.transform.Immutable


@Immutable
class Fsm {

    Map transitions
    String initState
    String currentState

    static Fsm load(@DelegatesTo(value = FsmBuilder,strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def fsmBuilder = new FsmBuilder()
        def fmsRecipe = closure.rehydrate(fsmBuilder, this, fsmBuilder)
        fmsRecipe.resolveStrategy = Closure.DELEGATE_ONLY
        fmsRecipe().build()
    }

    def state() {
        currentState
    }

    def clear() {
       ofCurrent(initState)
    }

    def fire(event) {
        ofCurrent from(event)
    }

    @Override
    String toString() {
        "Fsm : $transitions"
    }

    def methodMissing(String name, def args) {
       throw new FsmTransitionNotSupported(name)
    }

    def propertyMissing(String name) {
        throw new FsmWordNotSupported(name)
    }

    def propertyMissing(String name, def arg) {
        throw new FsmWordNotSupported(name)
    }

    private def ofCurrent(state){
        new Fsm(transitions, initState, state ? state : currentState)
    }

    private def from(event) {
        def evaluateState = {
            transitions[event][0] == currentState ? transitions[event][1] : currentState
        }

        transitions[event] ? evaluateState() : currentState
    }
}
