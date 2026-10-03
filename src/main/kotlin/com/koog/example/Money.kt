package com.koog.example

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import com.koog.example.tools.MoneyTransferTools

suspend fun main() {
    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val agent = AIAgent(
        promptExecutor = MultiLLMPromptExecutor(client),
        llmModel = OpenAIModels.Chat.GPT4_1Mini,
        toolRegistry = toolRegistry(),
        systemPrompt = """
            You're a banking assistant. Accompany the user with their request.
        """.trimIndent(),
    ) {
        handleEvents {
            onLLMCallStarting { llmCallContext ->
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
            onLLMCallCompleted { llmCallContext ->
                println("Response from LLM:")
                llmCallContext.response?.let {
                    println("   - $it")
                }
            }
        }
    }

    // tambien se podria pedir el mensaje, pero lo quiero fijar por ahora
    val userMessage = "send 25 to Daniel for diner at restaurant"
    val result = agent.run(userMessage)
    println(result)
}

private fun toolRegistry() = ToolRegistry {
    tools(MoneyTransferTools())
}


//    val agent = AIAgent.builder()
//        .promptExecutor(MultiLLMPromptExecutor(client))
//        .llmModel(model)
//        .toolRegistry(toolRegistry())
//        .systemPrompt("""
//            You're a banking assistant. Accompany the user with their request.
//        """.trimIndent())
//        .install(EventHandler) { config ->
//            config.onLLMCallStarting { llmCallContext ->
//                println("Request to LLM:")
//                println("   # Messages:")
//                llmCallContext.prompt.messages.forEach {
//                    println("   - $it")
//                }
//                println("   # Tools:")
//                llmCallContext.tools.forEach {
//                    println("   - $it")
//                }
//            }
//            config.onLLMCallCompleted { llmCallContext ->
//                println("Response from LLM:")
//                llmCallContext.responses.forEach {
//                    println("   - $it")
//                }
//            }
//        }
//        .build()
