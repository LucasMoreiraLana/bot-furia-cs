package org.example

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.polls.PollType
import com.github.kotlintelegrambot.logging.LogLevel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

fun main() {
    val botScope = CoroutineScope(Dispatchers.Default)

    // Configuração do Gson
    val gson = GsonBuilder()
        .setLenient()
        .create()

    // Configuração do OkHttp com logging
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Configuração do Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // Instâncias dos serviços da API
    val apiServicePartidas = retrofit.create(FuriaApiServicePartidas::class.java)
    val apiServiceLinup = retrofit.create(FuriaApiServiceLinup::class.java)

    val bot = bot {
        token = System.getenv("FURIA_CS_BOT_TOKEN")


        dispatch {
            command("comandos") {

                botScope.launch {
                    val text = """
                    🔥 *Bem\-vindo ao FURIA CS Bot, Furioso\!* 🔥
                    
                    Aqui você fica por dentro do time de CS da FURIA!
                    
                    *Comandos disponíveis:*
                    • /partidas - Últimos resultados e próximos jogos
                    • /lineup - Linup (titular)
                    • /enquete - Vote na FURIA\!
                """.trimIndent()


                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = text
                    )
                }

            }

            command("partidas") {
                botScope.launch {
                    val jogos = buscarUltimosJogosFuria(apiServicePartidas)

                    try {
                        if (jogos.isNotEmpty() && !jogos[0].startsWith("*Erro")) {
                            val resposta = jogos.joinToString("\n\n") { it }

                            bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = "*🏆 Últimos jogos da FURIA:*\n\n$resposta",
                                parseMode = ParseMode.MARKDOWN_V2
                            )
                        } else {
                            bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = "⚠️ *Não foi possível obter os últimos jogos da FURIA. \n Tente novamente mais tarde!*",
                                parseMode = ParseMode.MARKDOWN_V2
                            )
                        }

                        bot.sendMessage(
                            chatId = ChatId.fromId(update.message!!.chat.id),
                            text = "📲 *Acesse mais detalhes dos jogos da FURIA clicando abaixo:*",
                            parseMode = ParseMode.MARKDOWN_V2,
                            replyMarkup = InlineKeyboardMarkup.createSingleButton(
                                InlineKeyboardButton.Url(
                                    text = "🔗 Visite o HLTV",
                                    url = "https://www.hltv.org/team/8297/furia#tab-matchesBox"
                                )
                            )
                        )
                    } catch (e: Exception) {
                        println("Erro ao enviar mensagem para o Telegram: ${e.message}")
                    }
                }
            }

            command("lineup") {
                botScope.launch {
                    val jogadores = buscarLineupFuria(apiServiceLinup)

                    println("Lista de jogadores recebida: $jogadores")
                    println("Condição para exibir jogadores: ${jogadores.isNotEmpty() && !jogadores[0].startsWith("*Erro")}")

                    try {
                        if (jogadores.isNotEmpty() && !jogadores[0].startsWith("*Erro")) {
                            val resposta = jogadores.joinToString("\n\n") { it }
                            println("Mensagem a ser enviada: 🔥 A lineup FURIOSA titular!\n\n$resposta")

                            bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = "🔥 A lineup FURIOSA titular!\n\n$resposta",
                                parseMode = null // Desativa o Markdown
                            )
                        } else {
                            println("Exibindo mensagem de erro...")
                            bot.sendMessage(
                                chatId = ChatId.fromId(update.message!!.chat.id),
                                text = "⚠️ Não foi possível obter a lineup da FURIA. Tente novamente mais tarde!",
                                parseMode = null
                            )
                        }

                        bot.sendMessage(
                            chatId = ChatId.fromId(update.message!!.chat.id),
                            text = "📲 Conheça mais sobre a equipe clicando abaixo:",
                            parseMode = null,
                            replyMarkup = InlineKeyboardMarkup.createSingleButton(
                                InlineKeyboardButton.Url(
                                    text = "🔗 Visite o HLTV",
                                    url = "https://www.hltv.org/team/8297/furia#tab-infoBox"
                                )
                            )
                        )
                    } catch (e: Exception) {
                        println("Erro ao enviar mensagem para o Telegram: ${e.message}")
                    }
                }
            }

            command("enquete") {
                bot.sendPoll(
                    chatId = ChatId.fromId(update.message!!.chat.id),
                    question = "A Fúria leva a PGL Astana 2025? 🏆",
                    options = listOf("Sim, é nosso!", "ÓBVIO, AQUI É FÚRIA!"),
                    isAnonymous = false,
                    type = PollType.REGULAR
                )
            }
        }
    }

    bot.startPolling()
}

// Interface para definir os endpoints da API
interface FuriaApiServicePartidas {
    @GET("v1/partidas")
    fun getPartidas(): Call<List<Partida>>
}

interface FuriaApiServiceLinup {
    @GET("v1/lineup")
    fun getLineup(): Call<List<Jogador>>
}

// Modelo de dados para as partidas retornadas pela API
data class Partida(
    val data: String,
    val camp: String,
    val adversario: String,
    val status: String
)

data class Jogador(
    val nome: String,
    val nacionalidade: String,
    val funcao: String
)

// Função para buscar os últimos jogos da API usando Retrofit
suspend fun buscarUltimosJogosFuria(apiService: FuriaApiServicePartidas): List<String> {
    val jogos = mutableListOf<String>()
    try {
        val response = apiService.getPartidas().execute()
        if (response.isSuccessful) {
            val partidas = response.body() ?: return listOf("*Nenhum jogo encontrado. \n Verifique a API!*")

            for (partida in partidas.take(4)) {
                // Escapa caracteres especiais para Markdown V2
                val adversarioEscapado = partida.adversario.replace(".", "\\.")
                val campEscapado = partida.camp.replace(".", "\\.")
                val statusEscapado = partida.status.replace(".", "\\.")
                val dataEscapada = partida.data.replace("/", "\\/")

                jogos.add(
                    """
                    🆚 *FURIA vs $adversarioEscapado*
                    🏟 *Campeonato:* $campEscapado
                    📊 *Resultado:* $statusEscapado
                    📅 *Data:* $dataEscapada
                    """.trimIndent()
                )
            }
        } else {
            return listOf("*Erro ao buscar jogos:* Status ${response.code()}")
        }
    } catch (e: Exception) {
        return listOf("*Erro ao buscar jogos:* ${e.message}")
    }

    return if (jogos.isEmpty()) {
        listOf("*Nenhum jogo encontrado. \n Verifique a API!*")
    } else {

        jogos
    }
}

// Função auxiliar para escapar caracteres especiais no Markdown V2
fun escapeMarkdownV2(text: String): String {
    val specialChars = listOf("_", "*", "[", "]", "(", ")", "~", "`", ">", "#", "+", "-", "=", "|", "{", "}", ".", "!")
    var escapedText = text
    for (char in specialChars) {
        escapedText = escapedText.replace(char, "\\$char")
    }
    return escapedText
}

suspend fun buscarLineupFuria(apiService: FuriaApiServiceLinup): List<String> {
    val jogadores = mutableListOf<String>()
    try {
        println("Fazendo requisição para a API (lineup)...")
        val response = apiService.getLineup().execute()

        println("Resposta recebida: Status=${response.code()}")
        if (response.isSuccessful) {
            val lineup = response.body() ?: return listOf("*Nenhum jogador encontrado. Verifique a API!*")
            println("Dados desserializados: $lineup")

            for (jogador in lineup) {
                // Escapa todos os caracteres especiais para Markdown V2
                val nomeEscapado = escapeMarkdownV2(jogador.nome)
                val nacionalidadeEscapada = escapeMarkdownV2(jogador.nacionalidade)
                val funcaoEscapada = escapeMarkdownV2(jogador.funcao)

                val jogadorFormatado = """
                    👤 *${nomeEscapado}*
                    🌎 *Nacionalidade:* ${nacionalidadeEscapada}
                    🎯 *Função:* ${funcaoEscapada}
                """.trimIndent()
                println("Jogador formatado: $jogadorFormatado")
                jogadores.add(jogadorFormatado)
            }
        } else {
            println("Erro na requisição: Status=${response.code()}, Mensagem=${response.message()}")
            return listOf("*Erro ao buscar lineup:* Status ${response.code()}")
        }
    } catch (e: Exception) {
        println("Exceção capturada: ${e.message}")
        return listOf("*Erro ao buscar lineup:* ${e.message}")
    }

    return if (jogadores.isEmpty()) {
        println("Nenhum jogador encontrado na resposta.")
        listOf("*Nenhum jogador encontrado. Verifique a API!*")
    } else {
        println("Jogadores processados: $jogadores")
        jogadores
    }
}