package org.kata

data class Money(val value: Int) {
    companion object {
        @JvmStatic
        val NO_MONEY = Money(0)
    }

    operator fun plus(money: Money) = Money(this.value + money.value)
    operator fun minus(money: Money) = Money(this.value - money.value)
    operator fun compareTo(money: Money) = this.value.compareTo(money.value)
}
