import spock.lang.Specification

class FsmTest extends Specification {

    def "Fsm is immutable"() {

        given:
        def fsm = new Fsm("transitions": ["event": ["state1", "state2"]], initState: "state0")

        when:
        fsm.transitions["events2"] = ["state2, state3"]

        then:
        thrown(UnsupportedOperationException)
    }

    def "load() throw FsmWordNotSupported when method is not supported"() {

        when:
        Fsm.load {
            missingMethod
        }

        then:
        def e = thrown(FsmWordNotSupported)
        with(e) {
            message == "Word missingMethod is not supported"
        }
    }

    def "load() throw FsmTransitionNotSupported when argument is not supported"() {

        when:
        Fsm.load {
            apply "a"
        }

        then:
        def e = thrown(FsmTransitionNotSupported)
        with(e) {
            message == "FSM transition is not supported : apply"
        }
    }

    def "load throw IllegalArgumentException when not use initialState" () {

        when:
        def fsm = Fsm.load {
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
        }

        then:
        def e = thrown(IllegalArgumentException)
        with(e) {
            message == "Initial state must be indicated : use method initialState"
        }

        where:
        _event1  | _event2  | _state0  | _state1  | _state2 | _state3
        "event1" | "event2" | "state0" | "state1" | "state2"| "state3"
    }


    def "load() building transitions map with grammar word"() {

        when:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
        }

        then:
        fsm.transitions[_event1][0] == _state1
        fsm.transitions[_event1][1] == _state2
        fsm.transitions[_event2][0] == _state2
        fsm.transitions[_event2][1] == _state3

        where:
        _event1  | _event2  | _state0  | _state1  | _state2 | _state3
        "event1" | "event2" | "state0" | "state1" | "state2"| "state3"
    }

    def "load() order of method in load is not important" () {

        when:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
        }
        and:
        def fsm1 = Fsm.load {
            apply { on _event2, { from _state2, { to _state3 } } }
            initialState _state0
            apply { on _event1, { from _state1, { to _state2 } } }
        }
        and:
        def fsm2 = Fsm.load {
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
            initialState _state0
        }

        then:
        fsm == fsm1
        fsm1 == fsm2
        fsm == fsm2

        where:
        _event1  | _event2  | _state0  | _state1  | _state2 | _state3
        "event1" | "event2" | "state0" | "state1" | "state2"| "state3"
    }

    def "fire(_event) -> state if _event in transitions and from _ state = current"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state0, { to _state2 } } }
        }

        when:
        fsm = fsm.fire(_event1)

        then:
        fsm.state() == _state2

        where:
        _event1  | _state0  | _state1  | _state2
        "event1" | "state0" | "state1" | "state2"
    }

    def "fire(_event) not change state if _event is in transitions but from _ state != current"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state1, { to _state2 } } }
        }

        when:
        fsm = fsm.fire(_event1)

        then:
        fsm.state() == _state0


        where:
        _event1  | _state0  | _state1  | _state2
        "event1" | "state0" | "state1" | "state2"
    }

    def "fire(_event) not change state if _event is not in transitions"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state1, { to _state2 } } }
        }

        when:
        fsm = fsm.fire(_event1)

        then:
        fsm.state() == _state0


        where:
        _event1  | _event2  | _state0  | _state1  | _state2
        "event1" | "event2" | "state0" | "state1" | "state2"
    }

    def "state() return currentState"() {
        when:
        def fsm = Fsm.load {
            initialState _state0
        }

        then:
        fsm.state() == fsm.currentState

        where:
        _state0  | _
        "state0" | _
    }


    def "clear() return to initial Fsm"() {

        given:
        def fsm = Fsm.load {
            initialState _state0
            apply { on _event1, { from _state0, { to _state2 } } }
        }

        when:
        def fsm1 = fsm.fire(_event1)

        and:
        def fsm2 = fsm1.clear()

        then:
        fsm == fsm2
        fsm1 != fsm2


        where:
        _event1  | _event2  | _state0  | _state1  | _state2
        "event1" | "event2" | "state0" | "state1" | "state2"
    }
}


