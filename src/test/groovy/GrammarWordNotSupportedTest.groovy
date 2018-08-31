import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-08-29.
 */
class GrammarWordNotSupportedTest extends Specification {
    def "message"() {
        given:
        def word = "test"
        def e = new GrammarWordNotSupported(word)

        expect:
        with(e) {
            getLocalizedMessage() == "Word is not supported: $word"
        }
        
    }
}
