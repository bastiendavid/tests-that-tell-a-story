package org.kata

import spock.lang.Specification
import spock.lang.Unroll

class AccountTest extends Specification {

    @Unroll
    def "After a deposit of #amount, with balance #initialBalance, the final balance is #expectedFinalBalance"() {
        when: "deposit"
        def account = new Account()
        account.deposit(new Money(initialBalance))
        account.deposit(new Money(amount))

        then:
        expectedFinalBalance == account.money.value

        where:
        initialBalance | amount || expectedFinalBalance
        0              | 1       | 1
        20             | 30      | 50
    }

    @Unroll
    def "After a withdrawal of #amount, with balance #initialBalance, the final balance is #expectedFinalBalance"() {
        when: "amount"
        def account = new Account()
        account.deposit(new Money(initialBalance))
        def message = account.withdraw(new Money(amount))

        then:
        expectedFinalBalance == account.money.value
        expectedMessage == message.message

        where:
        initialBalance | amount || expectedFinalBalance | expectedMessage
        30             | 20      | 10                   | "Success"
        20             | 30      | 20                   | "you do not have enough money"
        20000          | 10000   | 10000                | "Success"
        20000          | 10001   | 20000                | "you have exceeded your daily withdrawal limit"
    }
}
