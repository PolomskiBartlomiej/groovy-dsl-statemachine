import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-08-29.
 */
@Immutable
class Grammar {
    String transitionEvent = ""
    String fromState = ""
    String toState = ""

    def static make(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = Grammar) Closure closure) {
        new Grammar().upgrade closure
    }
    
    def upgrade(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = Grammar) Closure closure) {
        def code = closure.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    def methodMissing(String methodName, args) {
        throw new GrammarWordNotSupported(methodName)
    }

    def propertyMissing(String name) {
        throw new GrammarWordNotSupported(name)
    }

    def propertyMissing(String name, def arg) {
        throw new GrammarWordNotSupported(name)
    }

    def on(event) {
        new Grammar(transitionEvent: event ? event : "",
                fromState: fromState,
                toState: toState)
    }

    def on(event, Closure closure) {
        new Grammar(transitionEvent: event ? event : "",
                fromState: fromState,
                toState: toState).upgrade closure
    }

    def from(state) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: state ? state : "",
                toState: toState)
    }

    def from(state, Closure closure) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: state ? state : "",
                toState: toState).upgrade closure
    }

    def to(state) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: fromState,
                toState: state ? state : "")
    }

    def to(state, Closure closure) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: fromState,
                toState: state ? state : "").upgrade closure
    }

    String toString() {
        "$transitionEvent: $fromState->$toState"
    }
}
