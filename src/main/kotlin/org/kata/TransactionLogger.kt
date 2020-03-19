package org.kata

import org.kata.TransactionType.CREDIT
import org.kata.TransactionType.DEBIT
import java.time.LocalDate

class TransactionLogger(
    private var date: LocalDate,
    private val accountStatement: AccountStatement = AccountStatement()
) {

    fun logDebit(balance: Money, debit: Money) {
        log(balance, DEBIT, debit)
    }

    fun logCredit(balance: Money, credit: Money) {
        log(balance, CREDIT, credit)
    }

    private fun log(balance: Money, transactionType: TransactionType, debit: Money) {
        allLogs.add(Transaction(balance, transactionType, date, debit))
    }

    fun logCreditFailure(balance: Money, credit: Money, message: Message) {
        allLogs.add(FailureRecord(balance, CREDIT, date, credit, message))
    }

    fun printStatementsOfTheDay(statementsPrinter: StatementsPrinter) {
        val statementToPrint =
            allLogs.filterIsInstance<Transaction>()
                .reversed()
                .fold(STATEMENT_HEADER) { acc, transaction ->
                    acc + System.lineSeparator() + accountStatement.printTransaction(transaction)
                }
        statementsPrinter.print(statementToPrint)
    }

    fun setDate(date: LocalDate) {
        this.date = date
    }


    companion object {
        @JvmStatic
        val DEFAULT = TransactionLogger(LocalDate.now())
        @JvmStatic
        val STATEMENT_HEADER = "date || credit || debit || balance"
    }

    val allLogs = ArrayList<Record>()
}
