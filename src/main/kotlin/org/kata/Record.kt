package org.kata

import java.time.LocalDate

sealed class Record

data class Transaction(
    val balance: Money,
    val transactionType: TransactionType,
    val date: LocalDate,
    val amount: Money
) : Record()

data class FailureRecord(
    val balance: Money,
    val transactionType: TransactionType,
    val date: LocalDate,
    val amount: Money,
    val message: Message
) : Record()

enum class TransactionType {
    CREDIT,
    DEBIT
}
