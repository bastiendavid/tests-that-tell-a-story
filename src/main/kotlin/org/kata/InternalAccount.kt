package org.kata

class InternalAccount {
    fun deposit(money: Money) {
        this.money += money
    }

    var money: Money = Money(0)
}
