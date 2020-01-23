package org.kata

class Message(val message: String) {

    companion object {
        @JvmStatic
        val withdrawOperationFailureMessage = Message("you do not have enough money")
        @JvmStatic
        val depositOperationFailureMessage = Message("Deposit operation failed")
        @JvmStatic
        val successMessage = Message("Success")
    }

}
