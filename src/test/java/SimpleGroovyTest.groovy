import spock.lang.Specification

class SimpleGroovyTest extends Specification {

    def "a simple test"() {
        expect:
        true == true
    }

    def "another simple test"() {
        given:
        def a = 3

        when:
        a = a + 2

        then:
        a == 5
    }

    def "a data driven test"() {
        expect:
        Math.addExact(a, b) == c

        where:
        a | b | c
        2 | 3 | 5
        1 | 1 | 2
    }
}
