import groovy.transform.Immutable

/**
 * Created by mtumilowicz on 2018-08-29.
 */
@Immutable
class Grammar {
    String transitionEvent = ""
    String fromState = ""
    String toState = ""

    def on(event) {
        new Grammar(transitionEvent: event ? event : "",
                fromState: fromState,
                toState: toState)
    }

    def from(state) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: state ? state : "",
                toState: toState)
    }

    def to(state) {
        new Grammar(transitionEvent: transitionEvent,
                fromState: fromState,
                toState: state ? state : "")
    }

    String toString() {
        "$transitionEvent: $fromState->$toState"
    }
}
