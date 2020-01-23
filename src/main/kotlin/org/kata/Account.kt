package org.kata

import org.kata.Message.Companion.depositOperationFailureMessage
import org.kata.Message.Companion.withdrawOperationFailureMessage
import org.kata.Message.Companion.successMessage
import org.kata.Money.Companion.NO_MONEY

class Account {


    var money: Money = NO_MONEY

    fun deposit(money: Money): Message {
        this.money = this.money + money
        return successMessage
    }


    fun withdraw(money: Money): Message {
        if (money > this.money) {
            return withdrawOperationFailureMessage
        }
        this.money = this.money - money
        return successMessage
    }

    fun transferInbetweenMyAccounts(money: Money, debit: Account) {
        if (this.withdraw(money) == withdrawOperationFailureMessage) {
            return
        }
        if (debit.deposit(money) == depositOperationFailureMessage) {
            this.deposit(money)
        }
    }

    fun transferToExternalAccount(money: Money, debit: ExternalAccount) {
        debit.deposit(money)
        this.withdraw(money)
    }

    fun transferToInternalAccount(money: Money, debit: InternalAccount) {
        this.withdraw(money)
    }

}

