package com.yoinerdev.quizzesia.data.dto



data class GeminiReq(
    val contents: List<ContentReq>,
)

data class ContentReq(
    val role: String,
    val parts: List<PartReq>,
)

data class PartReq(
    val text: String,
)



data class GeminiResp(
    val candidates: List<Candidate>,
)

data class Candidate(
    val content: Content,
)

data class Content(
    val parts: List<Part>,
    val role: String,
)

data class Part(
    val text: String,
)


