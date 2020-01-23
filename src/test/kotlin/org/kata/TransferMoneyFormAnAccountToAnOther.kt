package org.kata

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kata.Message.Companion.depositOperationFailureMessage
import org.kata.Money.Companion.NO_MONEY

class TransferMoneyFormAnAccountToAnOther {

    @Test
    fun `I transfer money between two personal accounts`() {
        val credit = Account()
        credit.deposit(someMoney(30))
        val debit = Account()

        credit.transferInbetweenMyAccounts(someMoney(10), debit)

        assertAccountHasMoney(credit, someMoney(20))
        assertAccountHasMoney(debit, someMoney(10))
    }

    @Test
    fun `I transfer money from my account to an account from another bank`() {
        val credit = Account()
        credit.deposit(someMoney(30))
        val debit: ExternalAccount = mockk(relaxed = true)

        credit.transferToExternalAccount(someMoney(10), debit)

        assertAccountHasMoney(credit, someMoney(20))
        verify { debit.deposit(someMoney(10)) }
    }


    @Test
    fun `I transfer money from my account to an account from the same bank`() {
        val credit = Account()
        credit.deposit(someMoney(30))
        val debit: InternalAccount = InternalAccount()
        credit.transferToInternalAccount(someMoney(10), debit)

        assertAccountHasMoney(credit, someMoney(20))
        assertThat(debit.money).isEqualTo(someMoney(10))
    }

    @Test
    fun `Nothing happens when I do not have enough money in the credit account`() {
        val credit = Account()
        credit.deposit(someMoney(10))
        val debit = Account()

        credit.transferInbetweenMyAccounts(someMoney(40), debit)

        assertAccountHasMoney(credit, someMoney(10))
        assertAccountHasMoney(debit, NO_MONEY)
    }

    @Test
    fun `Nothing happens when money cannot be deposited in the debit account`() {
        val credit = Account()
        credit.deposit(someMoney(10))
        val debit = spyk<Account>()
        every { debit.deposit(any()) } returns depositOperationFailureMessage

        credit.transferInbetweenMyAccounts(someMoney(5), debit)

        assertAccountHasMoney(credit, someMoney(10))
        assertAccountHasMoney(debit, NO_MONEY)
    }

    private fun assertAccountHasMoney(account: Account, money: Money) {
        assertThat(account.money).isEqualTo(money)
    }

}
