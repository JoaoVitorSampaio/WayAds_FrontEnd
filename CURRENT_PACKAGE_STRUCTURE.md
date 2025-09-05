# Detalhamento da Estrutura de Pacotes Atual do Projeto Android

Este documento descreve a estrutura de pacotes e os arquivos *atualmente* presentes no módulo `frontend-android/app/src/main/java/com/wayads`.

## Localização Raiz dos Pacotes

`C:\Users\junin\OneDrive\Documentos\WayAds_FrontEnd\frontend-android\app\src\main\java\com\wayads`

## Conteúdo Atual dos Pacotes

### `di` (Dependency Injection)
*   **Propósito**: Contém módulos de injeção de dependência.
*   **Arquivos Atuais**:
    *   `AppModule.kt`: Provavelmente um módulo Hilt ou Koin para fornecer dependências globais da aplicação.

### `mobile_app`
*   **Propósito**: Contém a Activity principal da aplicação.
*   **Arquivos Atuais**:
    *   `MainActivity.kt`: A Activity de entrada principal da aplicação.

### `model`
*   **Propósito**: Define as classes de modelo de dados.
*   **Arquivos Atuais**:
    *   `Ad.kt`: Classe de dados para representar um anúncio.
    *   `Category.kt`: Classe de dados para representar uma categoria.

### `network`
*   **Propósito**: Contém interfaces e configurações relacionadas à comunicação de rede.
*   **Arquivos Atuais**:
    *   `ApiService.kt`: Provavelmente uma interface Retrofit para definir os endpoints da API.

### `repository`
*   **Propósito**: Contém as implementações dos repositórios de dados.
*   **Arquivos Atuais**:
    *   `AdRepository.kt`: Repositório para gerenciar dados de anúncios.
    *   `CategoryRepository.kt`: Repositório para gerenciar dados de categorias.

### `ui` (User Interface)
*   **Propósito**: Contém os componentes da interface do usuário, organizados por funcionalidade ou tela.
*   **Sub-pacotes e Arquivos Atuais**:
    *   **`atualidades`**:
        *   `AtualidadesScreen.kt`: Provavelmente um Composable que representa a tela de "Atualidades".
    *   **`gastronomia`**:
        *   `GastronomiaScreen.kt`: Provavelmente um Composable que representa a tela de "Gastronomia".
    *   **`home`**:
        *   `HomeScreen.kt`: Provavelmente um Composable que representa a tela inicial.
        *   `HomeViewModel.kt`: Um ViewModel específico para a tela inicial.
        *   **`theme`**:
            *   `Color.kt`: Definições de cores para o tema da UI.
            *   `Theme.kt`: Definição do tema principal do Jetpack Compose.
            *   `Type.kt`: Definições de tipografia para o tema da UI.

### `viewmodel`
*   **Propósito**: Contém ViewModels.
*   **Arquivos Atuais**:
    *   `HomeViewModel.kt`: Um ViewModel.

---

**Observações sobre a Estrutura Atual e MVVM:**

A estrutura atual demonstra uma abordagem híbrida:
*   **Organização por Camadas (MVVM tradicional)**: Pacotes como `di`, `model`, `network`, `repository`, e o `viewmodel` de nível superior seguem a organização por camadas, onde cada pacote agrupa classes de um tipo específico (ex: todos os modelos em `model`).
*   **Organização por Feature (Package by Feature)**: O pacote `ui` e seus sub-pacotes (`atualidades`, `gastronomia`, `home`) demonstram uma organização por feature, onde todos os componentes relacionados a uma funcionalidade específica (UI, ViewModel, etc.) são agrupados. A presença de `HomeViewModel.kt` dentro de `ui/home` e também no pacote `viewmodel` de nível superior sugere uma possível inconsistência ou transição entre abordagens.

Para uma consistência maior com uma abordagem "Package by Feature" pura, o `HomeViewModel.kt` que está no pacote `viewmodel` de nível superior deveria ser movido para dentro de `ui/home` (se já não for o mesmo arquivo, o que é improvável). Se a intenção é ter todos os ViewModels em um único pacote `viewmodel` de nível superior, então `HomeViewModel.kt` deveria ser removido de `ui/home`.

A presença de `MainActivity.kt` em `mobile_app` é funcional, mas em uma estrutura "Package by Feature" ou mesmo em uma organização por camadas mais estrita, `MainActivity.kt` seria tipicamente encontrado em `ui/activities` ou diretamente em `ui` se não houver sub-pacotes de atividades.

Esta análise detalhada deve ajudar a validar a configuração atual com outra IA.