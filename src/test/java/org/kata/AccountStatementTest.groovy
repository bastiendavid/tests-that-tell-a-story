package org.kata

import spock.lang.Specification

import java.time.LocalDate

import static org.kata.TransactionType.CREDIT
import static org.kata.TransactionType.DEBIT

class AccountStatementTest extends Specification {

    def "Account with #operation will generate the statement"() {
        when:
        def account = new Account()
        account.deposit(new Money(1000))
        account.withdraw(new Money(100))
        account.deposit(new Money(30))
        account.deposit(new Money(20))
        account.withdraw(new Money(200))

        then:
        new AccountStatement(account).toString() == statement

        where:
        date                      | amount | balance || statement
        LocalDate.of(2020, 2, 26) | 10     | 30      || "date || amount || balance\n26/02/2020 || 10 || 30"
    }


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
