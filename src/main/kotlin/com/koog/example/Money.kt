package com.koog.example

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.core.tools.reflect.tool
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.llm.LLModel
import com.koog.example.tools.sendMoney
import io.ktor.events.EventHandler

suspend fun main() {
    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val model: LLModel = OpenAIModels.Chat.GPT4_1Mini

    val agent = AIAgent(
        promptExecutor = MultiLLMPromptExecutor(client),
        llmModel = model,
        toolRegistry = toolRegistry(),
        systemPrompt = """
            You're a banking assistant. Accompany the user with their request.
        """.trimIndent(),
    ) {
        handleEvents {
            onBeforeLLMCall { llmCallContext ->
                println("Request to LLM:")
                println("   # Messages:")
                llmCallContext.prompt.messages.forEach {
                    println("   - $it")
                }
                println("   # Tools:")
                llmCallContext.tools.forEach {
                    println("   - $it")
                }
            }
            onAfterLLMCall { llmCallContext ->
                println("Response from LLM:")
                llmCallContext.responses.forEach {
                    println("   - $it")
                }
            }
        }
    }

//    val agent = AIAgent.builder()
//        .promptExecutor(MultiLLMPromptExecutor(client))
//        .llmModel(model)
//        .toolRegistry(toolRegistry())
//        .systemPrompt("""
//            You're a banking assistant. Accompany the user with their request.
//        """.trimIndent())
//        .install(
//
//        )
//        .build()

    // tambien se podria pedir el mensaje, pero lo quiero fijar por ahora
    val userMessage = "send 25 to Daniel for diner at restaurant"
    val result = agent.run(userMessage)
    println(result)
}


private fun toolRegistry(): ToolRegistry = ToolRegistry {
    tool(::sendMoney)
}
