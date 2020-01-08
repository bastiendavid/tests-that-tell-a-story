package org.kata

import org.kata.Message.Companion.failureMessage
import org.kata.Message.Companion.successMessage
import org.kata.Money.Companion.NO_MONEY

class Account {


    var money: Money = NO_MONEY

    fun deposit(money: Money) {
        this.money = this.money + money
    }


    fun withdraw(money: Money): Message {
        if (money > this.money) {
            return failureMessage
        }
        this.money = this.money - money
        return successMessage
    }

}

