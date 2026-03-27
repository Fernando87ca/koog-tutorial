package com.koog.example.tools

import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.agents.core.tools.annotations.Tool
import ai.koog.agents.core.tools.reflect.ToolSet
import com.koog.example.mock_data.contactList
import com.koog.example.mock_data.contactMap

class MoneyTransferTools : ToolSet {

    @Tool
    @LLMDescription("Returns a list of all contacts")
    fun getContacts(
        @LLMDescription("The unique identifier of the user whose contact list is being retrieved.")
        userID: Int,
    ): String {
        return contactList.joinToString(separator = "\n") {
            "${it.id} - ${it.name} ${it.lastName}"
        }
    }

    @Tool
    @LLMDescription("Helps to identify the correct recipient when multiple contacts have similar names")
    fun chooseRecipient(
        @LLMDescription("The unique identifier of the user who initiated the transfer")
        userID: Int,
        @LLMDescription("The ambiguous name that needs to be clarified")
        confusingName: String,
    ): String {
        val matches = contactList.filter {
            it.name.contains(other = confusingName, ignoreCase = true) ||
                    it.lastName.contains(other = confusingName, ignoreCase = true)
        }

        if (matches.isEmpty()) return "No matching contacts found"
        if (matches.size == 1) return matches.first().id.toString()

        println("Multiple matches found. Please select the correct recipient:")
        matches.forEachIndexed { index, contact ->
            println("$index) ${contact.name} ${contact.lastName} (ID: ${contact.id})")
        }

        print("Enter the number of the correct recipient: ")
        return when(val selection = readln().toIntOrNull()) {
            null -> "Invalid selection"
            !in matches.indices -> "Invalid selection"
            else -> matches[selection].id.toString()
        }
    }

    @Tool
    @LLMDescription("Sends money to specified recipient with given amount and purpose")
    fun sendMoney(
        @LLMDescription("The ID of the user initializing the transfer")
        senderID: Int,
        @LLMDescription("Amount of money to send in euros")
        amount: Double,
        @LLMDescription("Name of the recipient")
        recipientID: Int,
        @LLMDescription("Purpose of the money transfer")
        purpose: String,
    ): String {
        val recipient = contactMap[recipientID] ?: return "invalid recipient"

        println("--------")
        println("Sending $amount EUR to ${recipient.name} ${recipient.lastName} ${recipient.phone} with purpose: $purpose")
        println("Please confirm the transaction by typing 'yes'")
        println("--------")

        val userMessage = readln()
        return if (userMessage.lowercase() == "yes") "Money sent" else "Transaction declined"
    }
}
