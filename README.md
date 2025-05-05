# FURIA CS Bot

## Descrição  
O **FURIA CS Bot** é um bot do Telegram que fornece atualizações sobre o time de CS da FURIA Esports, incluindo jogos, lineup e enquetes.

## Tecnologias

- **Linguagem**: Kotlin  
- **Bibliotecas**: `kotlin-telegram-bot`, `Retrofit`, `OkHttp`, `Gson`  
- **Ferramentas**: Gradle, Coroutines

## Como Usar

### 1. Configure o Ambiente

  - Instale Kotlin (1.9+), Java (17+) e Gradle.
  - A API já está integrada ao repositório, dentro da pasta `api`.
  - Certifique-se de que ela está rodando localmente com os seguintes endpoints disponíveis:
    - `GET /v1/partidas`
    - `GET /v1/lineup`
  - A URL esperada pelo bot é: `http://localhost:8080/`

### 2. Obtenha o Token

Crie um bot no Telegram via [@BotFather](https://t.me/BotFather) e copie o token.

Defina o token como uma variável de ambiente:

```bash
# Linux / Mac
export FURIA_CS_BOT_TOKEN=SEU_TOKEN

# Windows
set FURIA_CS_BOT_TOKEN=SEU_TOKEN
```

### 3. Clone e Instale

```bash
git clone https://github.com/LucasMoreiraLana/bot-furia-cs.git
cd bot-furia-cs
./gradlew build
```

### 4. Execute o Bot

```bash
./gradlew run
```

### 5. Use no Telegram

Adicione o bot ao seu chat (usando o nome criado no BotFather) e envie os comandos:

- `/comandos`: Veja a lista de comandos.
- `/partidas`: Veja os últimos jogos da FURIA.
- `/lineup`: Confira a lineup titular.
- `/enquete`: Participe de uma enquete sobre a FURIA.

## Comandos

| Comando      | Descrição                                  |
|--------------|----------------------------------------------|
| `/comandos`  | Lista os comandos disponíveis                |
| `/partidas`  | Mostra os últimos 4 jogos com detalhes       |
| `/lineup`    | Exibe a lineup atual                         |
| `/enquete`   | Inicia uma enquete (ex.: PGL Astana 2025)    |

## Contato

Dúvidas? Contate: [lucasmoreiralana04@gmail.com](mailto:lucasmoreiralana04@gmail.com)
