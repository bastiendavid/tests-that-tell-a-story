package org.kata

class MoneyBuilder {

    private var value: Int = 0

    fun withDefaultValue(): MoneyBuilder {
        this.value = 0
        return this
    }

    fun build(): Money {
        return Money(value)
    }

    fun withValue(value: Int): MoneyBuilder {
        this.value = value
        return this
    }

}

fun noMoney() = MoneyBuilder().withDefaultValue().build()

fun someMoney(value: Int) = MoneyBuilder().withValue(value).build()
