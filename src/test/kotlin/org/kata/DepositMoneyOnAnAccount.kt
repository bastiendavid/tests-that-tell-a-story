package org.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kata.Message.Companion.successMessage

class DepositMoneyOnAnAccount {

    @Test
    fun `When I create a new account, it has no money`() {
        // When
        val newAccount = Account()

        // Then
        assertThat(newAccount.money).isEqualTo(noMoney())
    }

    @Test
    fun `I want to deposit some money to my empty account`() {
        // Given
        val emptyAccount = Account()
        val someMoney = someMoney(20)

        // When
        emptyAccount.deposit(someMoney)

        // Then
        // Money is in my account
        assertThat(emptyAccount.money).isEqualTo(someMoney)
    }

    @Test
    fun `I want to add more money to my account`() {
        // Given
        val account = Account()
        val allOurMoney = someMoney(40)
        val firstDeposit = someMoney(10)
        val moreMoney = someMoney(30)

        // When
        account.deposit(firstDeposit)
        account.deposit(moreMoney)

        // Then
        assertThat(account.money).isEqualTo(allOurMoney)
    }

    @Test
    fun `When I deposit money on my account, I get a success message`() {
        val account = Account()
        val money = someMoney(30)

        val message = account.deposit(money)

        assertThat(message).isEqualTo(successMessage)
    }
}
