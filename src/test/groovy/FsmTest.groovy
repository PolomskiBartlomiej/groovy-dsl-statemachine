import spock.lang.Specification


class FsmTest extends Specification {

    def "Fsm is immutable"() {
        given:
        def fms = new Fsm(["events": new Tuple2("A", "B")])
        when:
        fms.events.put("events2", new Tuple2("C", "D"))

        then:
        thrown(UnsupportedOperationException)
    }


    def "load"() {
        when:
        def fsm = Fsm.load {
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
        }

        then:
        def event1 = fsm.events[_event1]
        def event2 = fsm.events[_event2]

        with(event1) {
            first == _state1
            second == _state2
        }

        with(event2) {
            first == _state2
            second == _state3
        }

        where:
        _event1  | _event2  | _state1  | _state2  | _state3
        "event1" | "event2" | "state1" | "state2" | "state3"
    }
}


