package com.koog.example

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.agent.singleRunStrategy
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.ext.tool.file.EditFileTool
import ai.koog.agents.ext.tool.file.ListDirectoryTool
import ai.koog.agents.ext.tool.file.ReadFileTool
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.executor.model.PromptExecutor
import ai.koog.rag.base.files.JVMFileSystemProvider

suspend fun main(args: Array < String >) {
    if (args.size < 2) {
        println("Error: Please provide the project absolute path and a task as arguments")
        println("Usage: <absolute_path> <task>")
        return
    }

    val (path, task) = args
    val input = "Project absolute path: $path\n\n## Task\n$task"

    val client: LLMClient = OpenAILLMClient(apiKey = BuildConfig.openIAApiKey)
    val executor = MultiLLMPromptExecutor(client)
    val agent = createAgent(executor)

    runCatching {
        val result = agent.run(input)
        println(result)
    }.getOrElse {
        executor.close()
    }
}

private fun createAgent(promptExecutor: PromptExecutor) = AIAgent(
    promptExecutor = promptExecutor,
    llmModel = OpenAIModels.Chat.GPT4_1Mini,
    toolRegistry = ToolRegistry {
        tool(ListDirectoryTool(JVMFileSystemProvider.ReadOnly))
        tool(ReadFileTool(JVMFileSystemProvider.ReadOnly))
        tool(EditFileTool(JVMFileSystemProvider.ReadWrite))
    },
    systemPrompt = """
            You are a highly skilled programmer tasked with updating the provided codebase according to the given task.
            Your goal is to deliver production-ready code changes that integrate seamlessly with the existing codebase 
            and solve given task.
        """.trimIndent(),
    strategy = singleRunStrategy(),
    maxIterations = 100,
) {
    handleEvents {
        onToolCallStarting { ctx -> println("Tool '${ctx.toolName}' called with args:" +
                " ${ctx.toolArgs.toString().take(100)}")
        }
    }
}