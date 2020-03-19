package org.kata

class ConsoleStatementsPrinter : StatementsPrinter {

    override fun print(statementToPrint: String) {
        kotlin.io.print(statementToPrint)
    }

}
