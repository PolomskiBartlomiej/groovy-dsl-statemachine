import groovy.transform.PackageScope

@PackageScope
class FsmBuilder {
    private def map = [:]

    def apply(@DelegatesTo(value = Grammar, strategy = Closure.DELEGATE_ONLY) Closure closure) {
        def grammar = Grammar.make(closure)
        map[grammar.transitionEvent] = [grammar.fromState, grammar.toState]
        this
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

    def build() {
        new Fsm(map)
    }
}
