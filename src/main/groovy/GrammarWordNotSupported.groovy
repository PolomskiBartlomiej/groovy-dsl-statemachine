/**
 * Created by mtumilowicz on 2018-08-29.
 */
class GrammarWordNotSupported extends RuntimeException {
    GrammarWordNotSupported(String word) {
        super("Word is not supported: $word")
    }
}

