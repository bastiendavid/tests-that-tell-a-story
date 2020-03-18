package org.kata

import org.kata.TransactionType.CREDIT
import org.kata.TransactionType.DEBIT
import java.time.LocalDate

class TransactionLogger(private val date: LocalDate) {

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

    companion object {
        @JvmStatic
        val DEFAULT = TransactionLogger(LocalDate.now())
    }

    val allLogs = ArrayList<Record>()
}
