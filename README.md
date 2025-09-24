# AegisCrypt 🛡️

**Status do Projeto: Versão 1.0 - Estável**

AegisCrypt é uma ferramenta de linha de comando (CLI) desenvolvida em Java para criptografia e descriptografia de textos de forma simples e segura. O projeto utiliza padrões de criptografia modernos e robustos para garantir a confidencialidade e a integridade dos seus dados.

### Tabela de Conteúdos

- [Sobre o Projeto](#sobre-o-projeto)
- [Principais Funcionalidades](#principais-funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Como Compilar e Executar](#como-compilar-e-executar)
- [Como Usar](#como-usar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Licença](#licença)

### Sobre o Projeto

O objetivo do AegisCrypt é fornecer uma solução de criptografia acessível e confiável para uso pessoal, diretamente do terminal.

A segurança é garantida pelo uso do algoritmo **AES-256 no modo GCM**, que oferece criptografia autenticada, protegendo não apenas contra a interceptação dos dados, mas também contra sua manipulação. A chave de criptografia é derivada de uma senha fornecida pelo usuário através do **PBKDF2**, um padrão da indústria para derivação de chaves baseadas em senha.

### Principais Funcionalidades

-   **Criptografia Forte:** Utiliza o algoritmo AES-256 com modo GCM, o padrão de ouro para criptografia simétrica.
-   **Derivação de Chave Segura:** Usa PBKDF2 com `salt` para transformar uma senha de texto em uma chave criptográfica robusta.
-   **Interface de Linha de Comando Interativa:** Um menu simples guia o usuário para criptografar ou descriptografar textos.
-   **Entrada de Senha Segura:** A senha é digitada de forma mascarada no terminal, não ficando visível na tela.
-   **Multiplataforma:** Por ser feito em Java, o programa roda em Windows, macOS e Linux.
-   **Autocontido:** Não requer dependências externas, apenas um JDK instalado.

### Pré-requisitos

Para compilar e executar o projeto, você precisa ter instalado:

-   **Java Development Kit (JDK)** - Versão 21 ou superior.


### Como Compilar e Executar

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/aegiscrypt.git](https://github.com/seu-usuario/aegiscrypt.git)
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd aegiscrypt
    ```

3.  **Compile os arquivos-fonte:**
    O comando abaixo irá compilar todos os arquivos `.java` da pasta `src` e colocar os `.class` gerados em uma nova pasta `bin`.
    ```bash
    javac -d bin src/com/aegiscrypt/Main.java src/com/aegiscrypt/config/CryptoConfig.java src/com/aegiscrypt/model/EncryptedPayload.java src/com/aegiscrypt/service/EncryptionService.java src/com/aegiscrypt/service/impl/AegisCryptService.java
    ```

4.  **Execute a aplicação:**
    Este comando inicia o programa, que ficará aguardando suas instruções no terminal.
    ```bash
    java -cp bin com.aegiscrypt.Main
    ```

### Como Usar

Após a execução, a aplicação apresentará um menu interativo:

```
--- BEM-VINDO AO AEGISCRYPT ---

Escolha uma opção:
1. Criptografar Texto
2. Descriptografar Texto
3. Sair
>> 
```

1.  **Para Criptografar:**
    -   Digite `1` e pressione `Enter`.
    -   Insira o texto que deseja criptografar.
    -   Digite a senha que será usada para a criptografia (ela não aparecerá na tela).
    -   O programa exibirá o texto criptografado em formato Base64. Copie e guarde-o em um local seguro.

2.  **Para Descriptografar:**
    -   Digite `2` e pressione `Enter`.
    -   Cole o texto criptografado em Base64.
    -   Digite a **mesma senha** usada para criptografar.
    -   Se a senha estiver correta, o texto original será exibido.

### Estrutura do Projeto

O código-fonte está organizado em pacotes que separam as responsabilidades:

```
aegiscrypt/
└── src/
    └── com/
        └── aegiscrypt/
            ├── Main.java                 # Ponto de entrada da aplicação (CLI)
            ├── config/
            │   └── CryptoConfig.java     # Constantes de configuração
            ├── model/
            │   └── EncryptedPayload.java # Modelo de dados do payload criptografado
            └── service/
                ├── EncryptionService.java  # Interface do serviço
                └── impl/
                    └── AegisCryptService.java # Implementação da lógica de criptografia
```

### Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.