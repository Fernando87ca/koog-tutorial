package com.koog.example.tools

import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.agents.core.tools.annotations.Tool

@Tool
@LLMDescription("Sends money to specified recipient with given amount and purpose")
fun sendMoney(
    @LLMDescription("Amount of money to send in euros") amount: Double,
    @LLMDescription("Name of the recipient") recipient: String,
    @LLMDescription("Purpose of the money transfer") purpose: String
): String {
    println("--------")
    println("Sending money to $recipient for $amount euros with purpose: $purpose")
    println("Please confirm the transaction by typing 'yes'")
    println("--------")

    val userMessage = readln()
    return if (userMessage.lowercase() == "yes") "Money sent" else "Transaction declined"
}
