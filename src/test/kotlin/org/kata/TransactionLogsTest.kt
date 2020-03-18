package org.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kata.Message.Companion.withdrawOperationFailureMessage
import org.kata.TransactionType.CREDIT
import org.kata.TransactionType.DEBIT
import java.time.LocalDate

class TransactionLogsTest {
    @Test
    fun `For a new account, there are no transactions in the logs`() {
        val logger = TransactionLogger(LocalDate.of(2020, 3, 11))
        val account = Account(logger)
        assertThat(logger.allLogs).isEmpty()
    }

    @Test
    fun `For an account where we deposit some money, there is a deposit transaction logged`() {
        val date = LocalDate.of(2020, 3, 11)
        val logger = TransactionLogger(date)
        val account = Account(logger)
        account.deposit(Money(30))

        val depositTransaction = Transaction(someMoney(30), DEBIT, date, someMoney(30))
        assertThat(logger.allLogs).containsExactly(depositTransaction)
    }

    @Test
    fun `For an account with an initial deposit, when we withdraw some money there are two transactions logs`() {
        val date = LocalDate.of(2020, 3, 11)
        val logger = TransactionLogger(date)
        val account = Account(logger)
        account.deposit(Money(30))
        account.withdraw(Money(10))

        val depositTransaction = Transaction(someMoney(30), DEBIT, date, someMoney(30))
        val withdrawalTransaction = Transaction(someMoney(20), CREDIT, date, someMoney(10))
        assertThat(logger.allLogs).containsExactly(depositTransaction, withdrawalTransaction)
    }

    @Test
    fun `When we try to withdraw more money than we have, a failure record is kept`() {
        val date = LocalDate.of(2020, 3, 11)
        val logger = TransactionLogger(date)
        val account = Account(logger)
        account.deposit(Money(10))
        account.withdraw(Money(100))
        val depositTransaction = Transaction(someMoney(10), DEBIT, date, someMoney(10))
        val failureRecord = FailureRecord(someMoney(10), CREDIT, date, someMoney(100), withdrawOperationFailureMessage)
        assertThat(logger.allLogs).containsExactly(depositTransaction, failureRecord)
    }

    @Test
    fun `When I check my transactions after a week, I see all the records`() {
        val date = LocalDate.of(2020, 3, 11)
        val logger = TransactionLogger(date)
        val account = Account(logger)
        account.deposit(Money(10))
        account.withdraw(Money(100))
        account.deposit(Money(30))
        account.deposit(Money(200))
        account.withdraw(Money(100))
        account.withdraw(Money(10))

        val expectedRecords = listOf(
            Transaction(someMoney(10), DEBIT, date, someMoney(10)),
            FailureRecord(someMoney(10), CREDIT, date, someMoney(100), withdrawOperationFailureMessage),
            Transaction(someMoney(40), DEBIT, date, someMoney(30)),
            Transaction(someMoney(240), DEBIT, date, someMoney(200)),
            Transaction(someMoney(140), CREDIT, date, someMoney(100)),
            Transaction(someMoney(130), CREDIT, date, someMoney(10))
        )
        assertThat(logger.allLogs).containsAll(expectedRecords)
    }
}

