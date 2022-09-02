package br.com.phs.ktorkmm

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Hello(@SerialName("string")val string: String, @SerialName("lang")val lang: String)

class Greeting {

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }

            }
        }
    }.also { initLogger() }

    @Throws(Throwable::class)
    suspend fun greeting(): String {
        val hello = getHello()?: return "Hello"
        return "${hello.first().string}, ${Platform().platform}!"
    }

    private suspend fun getHello(): List<Hello>? {
        return httpClient.get("https://gitcdn.link/cdn/KaterinaPetrova/greeting/7d47a42fc8d28820387ac7f4aaf36d69e434adc1/greetings.json").body()
    }

}