package org.kata

class Message(val message: String) {

    companion object {
        @JvmStatic
        val failureMessage = Message("you do not have enough money")
        @JvmStatic
        val successMessage = Message("Success")
    }

}
