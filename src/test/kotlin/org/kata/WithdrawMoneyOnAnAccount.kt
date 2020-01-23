package org.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kata.Message.Companion.withdrawOperationFailureMessage
import org.kata.Message.Companion.successMessage

class WithdrawMoneyOnAnAccount {

    @Test
    fun `I can withdraw some money on an account on which I have made a deposit first`() {
        // Given
        val account = Account()
        val balance = someMoney(10)

        // When
        account.deposit(someMoney(40))
        account.withdraw(someMoney(30))

        // Then
        assertThat(account.money).isEqualTo(balance)
    }

    @Test
    fun `When I withdraw money on an account, I receive a success message`() {
        // Given
        val account = Account()
        val balance = someMoney(10)

        // When
        account.deposit(someMoney(40))
        val message = account.withdraw(someMoney(30))

        // Then
        assertThat(message).isEqualTo(successMessage)

    }

    @Test
    fun `When I try to withdraw money on an empty account, I receive a message that I do not have enough money`() {
        // Given
        val account = Account()

        // When
        val message = account.withdraw(someMoney(30))

        // Then
        assertThat(message).isEqualTo(withdrawOperationFailureMessage)
    }

    @Test
    fun `When I try to withdraw money on an empty account, my balance is not changed`() {
        // Given
        val account = Account()

        // When
        account.withdraw(someMoney(30))

        // Then
        assertThat(account.money).isEqualTo(noMoney())
    }
}
