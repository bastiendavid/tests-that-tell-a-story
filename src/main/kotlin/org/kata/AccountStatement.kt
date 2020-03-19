package org.kata

import java.time.format.DateTimeFormatter

class AccountStatement {
    fun printTransaction(transaction: Transaction): String {
        return "${formatDate(transaction)} ||${debit(transaction)}||${credit(transaction)}|| ${transaction.balance.value}"
    }

    private fun debit(transaction: Transaction): String = transactionAmount(transaction,
        TransactionType.CREDIT
    )

    private fun credit(transaction: Transaction): String = transactionAmount(transaction,
        TransactionType.DEBIT
    )

    private fun transactionAmount(transaction: Transaction, transactionType: TransactionType): String {
        if (transaction.transactionType == transactionType) {
            return " "
        }
        return " ${transaction.amount.value} "
    }

    private fun formatDate(transaction: Transaction) = transaction.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
}
