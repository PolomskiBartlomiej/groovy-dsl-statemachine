import spock.lang.Specification

class FsmBuilderTest extends Specification {

    def "apply() building map of Grammar word"() {

        given:
        def builder = new FsmBuilder()

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

        given:
        def builder = new FsmBuilder()

        when:
        builder.apply {
            method "a"
        }

        then:
        thrown(GrammarWordNotSupported)
    }

    def "apply() throw GrammarWordNotSupported if method missing"() {

        given:
        def builder = new FsmBuilder()

        when:
        builder.apply {
            method
        }

        then:
        thrown(GrammarWordNotSupported)
    }

    def "apply() order of usage grammar.on(), grammar.from(), grammar.to() method is not important"() {

        when:
        def fsm = new FsmBuilder().apply {
            on _event, { from _state1, { to _state2 } }
        }
        .initialState(_state0)
        .build()

        and:
        def fsm1 = new FsmBuilder().apply {
            from _state1, { on _event, { to _state2 } }
        }
        .initialState(_state0)
        .build()

        and:
        def fsm2 = new FsmBuilder().apply {
            to _state2, { from _state1, { on _event } }
        }
        .initialState(_state0)
        .build()


        then:
        fsm == fsm1
        fsm1 == fsm2

        where:
        _event  | _state0  | _state1  | _state2
        "event" | "state1" | "state1" | "state2"
    }

    def "build create Fsm"() {

        given:
        def builder = new FsmBuilder()

        when:
        def fsm = builder.apply {
            on _event, { from _state1, { to _state2 } }
        }
        .apply {
            on _event1, { from _state2, { to _state3 } }
        }
        .initialState(_state0)
                .build()

        then:
        fsm == new Fsm(initState: _state0,
                transitions: [(_event): [_state1, _state2], (_event1): [_state2, _state3]],
                currentState: _state0)

        where:
        _event  | _event1  | _state0  | _state1  | _state2  | _state3
        "event" | "event1" | "state0" | "state1" | "state2" | "state3"
    }


    def "build throw IllegalArgumentException when not set the initial state"() {

        given:
        def builder = new FsmBuilder()

        when:
        builder.apply {
            on _event, { from _state1, { to _state2 } }
        }
        .apply {
            on _event1, { from _state2, { to _state3 } }
        }
        .build()

        then:
        def e = thrown(IllegalArgumentException)
        with(e) {
            message == "Initial state must be indicated : use method initialState"
        }

        where:
        _event  | _event1  | _state1  | _state2  | _state3
        "event" | "event1" | "state1" | "state2" | "state3"
    }
}

