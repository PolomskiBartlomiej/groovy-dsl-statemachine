import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-08-29.
 */
class GrammarTest extends Specification {
    
    def "test on - immutability"() {
        given:
        def transition_event = "test"
        def grammar = new Grammar()

        when:
        grammar.on(transition_event)

        then:'grammar should not change'
        !grammar.transitionEvent
    }
    
    def "test on"() {
        given:
        def transition_event = "test"
        def grammar = new Grammar()

        when:
        def grammar_after_on = grammar.on(transition_event)

        then:
        grammar_after_on.transitionEvent == transition_event
    }

    def "test from - immutability"() {
        given:
        def from_state = "test"
        def grammar = new Grammar()

        when:
        grammar.from(from_state)

        then:'grammar should not change'
        !grammar.fromState
    }

    def "test from"() {
        given:
        def from_state = "test"
        def grammar = new Grammar()

        when:
        def grammar_after_from = grammar.from(from_state)

        then:
        grammar_after_from.fromState == from_state
    }

    def "test to - immutability"() {
        given:
        def to_state = "test"
        def grammar = new Grammar()

        when:
        grammar.to(to_state)

        then:'grammar should not change'
        !grammar.toState
    }

    def "test to"() {
        given:
        def to_state = "test"
        def grammar = new Grammar()

        when:
        def grammar_after_to = grammar.to(to_state)

        then:
        grammar_after_to.toState == to_state
    }
    
    def "test full tense: on().from().to()"() {
        given:
        def transition_state = "event"
        def from_state = "fromState"
        def to_state = "toState"

        def grammar = new Grammar()

        when:
        def grammar_after_on_from_to = grammar.on(transition_state).from(from_state).to(to_state)
        
        then:
        with(grammar_after_on_from_to) {
            transitionEvent == transition_state
            fromState == from_state
            toState == to_state
        }
    }
    
    def "if word appears many times, only the last one occurrence is important"() {
        when:
        def grammar_multiple_from = new Grammar().from("from1").from("from2")
        
        then:
        grammar_multiple_from.fromState == "from2"
    }
    
    def "test toString"() {
        given:
        def grammar = new Grammar(transitionEvent: "event", toState: "toState", fromState: "fromState")

        expect:
        grammar.toString() == "event: fromState->toState"
    }
}
