package org.kata

import spock.lang.Specification

import java.time.LocalDate

import static org.kata.TransactionType.CREDIT
import static org.kata.TransactionType.DEBIT

class AccountStatementTest extends Specification {


    def "A transaction can be printed by an account statement"() {
        when:
        def transaction = generateTransaction(transactionType, date, amount)

        then:
        new AccountStatement().printTransaction(transaction) == statementLine

        where:
        transactionType | date                      | amount || statementLine
        CREDIT          | LocalDate.of(2020, 2, 26) | 10     || "2020-02-26 || || 10 || 30"
        DEBIT           | LocalDate.of(2020, 3, 27) | 40     || "2020-03-27 || 40 || || 30"
    }


    Transaction generateTransaction(TransactionType transactionType, LocalDate date, int amount) {
        new Transaction(new Money(30), transactionType, date, new Money(amount))
    }
}
