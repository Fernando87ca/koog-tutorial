package com.koog.example

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.core.tools.reflect.tool
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.llm.LLModel
import com.koog.example.tools.sendMoney

suspend fun main() {
    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val model: LLModel = OpenAIModels.Chat.GPT4_1Mini

    val agent = AIAgent.builder()
        .promptExecutor(MultiLLMPromptExecutor(client))
        .llmModel(model)
        .toolRegistry(toolRegistry())
        .systemPrompt("""
            You're a banking assistant. Accompany the user with their request.
        """.trimIndent())
        .build()

    // tambien se podria pedir el mensaje, pero lo quiero fijar por ahora
    val userMessage = "send 25 to Daniel for diner at restaurant"
    val result = agent.run(userMessage)
    println(result)
}


private fun toolRegistry(): ToolRegistry = ToolRegistry {
    tool(::sendMoney)
}
