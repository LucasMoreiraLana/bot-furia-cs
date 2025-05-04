FURIA CS Bot
Descrição
O FURIA CS Bot é um bot do Telegram que fornece atualizações sobre o time de CS da FURIA Esports, incluindo jogos, lineup e enquetes.
Tecnologias

Linguagem: Kotlin
Bibliotecas: kotlin-telegram-bot, Retrofit, OkHttp, Gson
Ferramentas: Gradle, Coroutines

Como Usar
1. Configure o Ambiente

Instale Kotlin (1.9+), Java (17+), e Gradle.
Tenha um backend rodando em http://localhost:8080/ com endpoints /v1/partidas e /v1/lineup.

2. Obtenha o Token

Crie um bot no Telegram via @BotFather.
Copie o token e defina como variável de ambiente:
Linux/Mac: export FURIA_CS_BOT_TOKEN=SEU_TOKEN
Windows: set FURIA_CS_BOT_TOKEN=SEU_TOKEN



3. Clone e Instale
   git clone https://github.com/seu-usuario/furia-cs-bot.git
   cd furia-cs-bot
   ./gradlew build

4. Execute o Bot
   ./gradlew run

5. Use no Telegram

Adicione o bot ao seu chat (use o nome do bot criado no BotFather).
Envie os comandos:
/comandos: Veja a lista de comandos.
/partidas: Veja os últimos jogos da FURIA.
/lineup: Confira a lineup titular.
/enquete: Participe de uma enquete sobre a FURIA.



Comandos

/comandos: Lista os comandos disponíveis.
/partidas: Mostra os últimos 4 jogos com detalhes.
/lineup: Exibe a lineup atual.
/enquete: Inicia uma enquete (ex.: PGL Astana 2025).

Contato
Dúvidas? Contate: lucasmoreiralana04@gmail.com
