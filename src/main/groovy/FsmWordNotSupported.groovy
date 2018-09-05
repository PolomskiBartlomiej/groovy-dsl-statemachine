
class FsmWordNotSupported extends RuntimeException {
    FsmWordNotSupported(String name) {
        super("Word $name is not supported")
    }
}
