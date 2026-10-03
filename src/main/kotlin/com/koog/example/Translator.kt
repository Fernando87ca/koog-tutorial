package com.koog.example

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.params.LLMParams
import java.util.UUID

suspend fun main() {
    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val model: LLModel = OpenAIModels.Chat.GPT4_1Mini
    val userMessage = readln()
    val prompt = prompt(
        id = UUID.randomUUID().toString(),
        params = LLMParams(temperature = 0.7),
        build = {
            user("translate this sentence to german: $userMessage")
        }
    )
    val response = client.execute(prompt, model)
    with (response) {
        println("Translation: ${this.textContent()}")
        println("Meta.info: ${this.metaInfo}")
    }
}
