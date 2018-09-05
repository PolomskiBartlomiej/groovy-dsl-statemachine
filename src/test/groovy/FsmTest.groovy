import spock.lang.Specification

class FsmTest extends Specification {

    def "Fsm is immutable"() {

        given:
        def fsm = new Fsm("transitions": ["event": ["state1", "state2"]], initialState: "state0")

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


    def "load() building transitions map with grammar word"() {

        when:
        def fsm = Fsm.load {
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
        }

        then:
        fsm.transitions[_event1][0] == _state1
        fsm.transitions[_event1][1] == _state2
        fsm.transitions[_event2][0] == _state2
        fsm.transitions[_event2][1] == _state3

        where:
        _event1  | _event2  | _state1  | _state2  | _state3
        "event1" | "event2" | "state1" | "state2" | "state3"
    }
}


