package com.koog.example

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.params.LLMParams

suspend fun main() {
    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val model: LLModel = OpenAIModels.Chat.GPT4_1Mini
    val prompt = prompt(
        id = "tool-by-hand",
        params = LLMParams(temperature = 0.7)
    ) {
        system(
            content = """
                You're a banking assistant. You can send money by writing the following JSON:
                {
                    "name": "send_money",
                    "params": {
                        "recipient": <recipient_name>,
                        "amount": <amount_in_euros>,
                        "purpose": <purpose_of_the_transaction>
                    }
                }
            """.trimIndent()
        )
        user(content = "Send 20 euros to Daniel for dinner at the restaurant")
    }
    val responses = client.execute(prompt, model)
    with (responses.first()) {
        println("Response: ${this.content}")
        println("Meta.info: ${this.metaInfo}")
    }
}
