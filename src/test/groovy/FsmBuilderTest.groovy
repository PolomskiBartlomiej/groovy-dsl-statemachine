import spock.lang.Shared
import spock.lang.Specification

class FsmBuilderTest extends Specification {

    @Shared
    def builder = new FsmBuilder()

    def "apply() building map of Grammar word"() {

        when:
        builder.apply {
            on _event, { from _state1, { to _state2 } }
        }

        then:
        builder.map[_event] == [_state1, _state2]

        where:
        _event  | _state1  | _state2
        "event" | "state1" | "state2"
    }

    def "apply() throw GrammarWordNotSupported if method with param missing"() {
        when:
        builder.apply {
            method "a"
        }

        then:
        thrown(GrammarWordNotSupported)
    }

    def "apply() throw GrammarWordNotSupported if method missing"() {
        when:
        builder.apply {
            method
        }

        then:
        thrown(GrammarWordNotSupported)
    }

    def "build"() {
        when:
        def fsm = builder.apply {
            on _event, { from _state1, { to _state2 } }
        }
        .apply {
            on _event1, { from _state2, { to _state3 } }
        }
        .build()

        then:
        fsm == new Fsm([(_event): [_state1, _state2], (_event1): [_state2, _state3]])

        where:
        _event  | _event1  | _state1  | _state2  | _state3
        "event" | "event1" | "state1" | "state2" | "state3"
    }
}

