package org.kata

import java.time.LocalDate

class Transaction(
    val balance: Money,
    val transactionType: TransactionType,
    val date: LocalDate,
    val amount: Money
)

enum class TransactionType {
    CREDIT,
    DEBIT
}
