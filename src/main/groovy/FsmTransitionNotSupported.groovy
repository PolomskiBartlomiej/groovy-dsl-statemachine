
class FsmTransitionNotSupported extends RuntimeException {

    FsmTransitionNotSupported(String event) {
        super("FSM transition is not supported : $event")
    }
}
