package org.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDate

class StatementPrintingTest {

    private lateinit var capturedStream: ByteArrayOutputStream
    private lateinit var defaultSystemOut: PrintStream

    @BeforeEach
    fun setup() {
        capturedStream = ByteArrayOutputStream()
        defaultSystemOut = System.out
        System.setOut(PrintStream(capturedStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(defaultSystemOut)
    }

    @Test
    fun `When I deposit some money on my account, the statement of the day shows this operation`() {
        // Given
        val transactionLogger = TransactionLogger(LocalDate.of(2020, 3, 18))
        val account = Account(transactionLogger)
        account.deposit(someMoney(30))

        // When
        transactionLogger.printStatementsOfTheDay(ConsoleStatementsPrinter())

        // Then
        val statementOfTheDay = """date || credit || debit || balance
            |2020-03-18 || 30 || || 30
        """.trimMargin()
        assertThat(capturedStream.toString()).isEqualTo(statementOfTheDay)
    }

    @Test
    fun `When I make 2 deposits on my account, the statement of the day shows these operations with the last transaction at the top`() {
        // Given
        val transactionLogger = TransactionLogger(LocalDate.of(2020, 3, 18))
        val account = Account(transactionLogger)
        account.deposit(someMoney(30))
        account.deposit(someMoney(200))

        // When
        transactionLogger.printStatementsOfTheDay(ConsoleStatementsPrinter())

        // Then
        val statementOfTheDay = """date || credit || debit || balance
            |2020-03-18 || 200 || || 230
            |2020-03-18 || 30 || || 30
        """.trimMargin()
        assertThat(capturedStream.toString()).isEqualTo(statementOfTheDay)
    }


    @Test
    fun `When an operation fails, it does not show on the statement of the day`() {
        // Given
        val transactionLogger = TransactionLogger(LocalDate.of(2020, 3, 18))
        val account = Account(transactionLogger)
        account.deposit(someMoney(30))
        account.withdraw(someMoney(100))
        account.withdraw(someMoney(10))

        // When
        transactionLogger.printStatementsOfTheDay(ConsoleStatementsPrinter())

        // Then
        val statementOfTheDay = """date || credit || debit || balance
            |2020-03-18 || || 10 || 20
            |2020-03-18 || 30 || || 30
        """.trimMargin()
        assertThat(capturedStream.toString()).isEqualTo(statementOfTheDay)
    }


    @Test
    fun `When I make several deposits on my account, the statements appear with the most recent at the top`() {
        // Given
        val transactionLogger = TransactionLogger(LocalDate.of(2020, 3, 18))
        val account = Account(transactionLogger)
        account.deposit(someMoney(30))
        transactionLogger.setDate(LocalDate.of(2020, 3, 19))
        account.deposit(someMoney(40))
        transactionLogger.setDate(LocalDate.of(2020, 3, 22))
        account.deposit(someMoney(50))

        // When
        transactionLogger.printStatementsOfTheDay(ConsoleStatementsPrinter())

        // Then
        val statementOfTheDay = """date || credit || debit || balance
            |2020-03-22 || 50 || || 120
            |2020-03-19 || 40 || || 70
            |2020-03-18 || 30 || || 30
        """.trimMargin()
        assertThat(capturedStream.toString()).isEqualTo(statementOfTheDay)
    }


}
