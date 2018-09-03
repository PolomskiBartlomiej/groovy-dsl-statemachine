import groovy.transform.Immutable

@Immutable
class Fsm {

    Map<String, Tuple2> events

    static def load(@DelegatesTo(value = FsmBuilder) Closure closure) {
        def fsmBuilder = new FsmBuilder()
        def call = closure.rehydrate(fsmBuilder, this, fsmBuilder)
        call.resolveStrategy = Closure.DELEGATE_ONLY
        call()
        fsmBuilder.build()
    }

    @Override
    String toString() {
        "Fsm : $events"
    }

    private static class FsmBuilder {
        private def map = [:]

        def apply(@DelegatesTo(value = Grammar, strategy = Closure.DELEGATE_ONLY) Closure closure) {
            def grammar = Grammar.make(closure) as Grammar
            map.put(grammar.transitionEvent, new Tuple2(grammar.fromState, grammar.toState))
        }

        private def build() {
            new Fsm(map)
        }
    }
}
