# WayAds Frontend - Android

Este projeto é o frontend Android para o aplicativo **WayAds**.

## Tecnologias Utilizadas

* **Linguagem:** Kotlin  
* **Framework UI:** Jetpack Compose  
* **Plataforma:** Android  

---

## Arquitetura do Projeto: MVVM (Model-View-ViewModel)

A arquitetura **MVVM** foi escolhida para este projeto por trazer diversas vantagens no desenvolvimento de aplicações Android modernas:

1. **Separação de Responsabilidades** – divide claramente a lógica de negócio (Model), a lógica de apresentação (ViewModel) e a interface do usuário (View).  
2. **Testabilidade Aprimorada** – permite testar lógica de negócio sem depender da interface gráfica.  
3. **Reatividade e Observabilidade** – uso de `State`, `Flow` ou `LiveData` para atualizar a UI automaticamente quando os dados mudam.  
4. **Respeito ao Ciclo de Vida** – os `ViewModels` sobrevivem a mudanças de configuração (como rotação de tela).  
5. **Escalabilidade** – fácil evolução do projeto, mantendo código limpo e modular.  

---

## Estrutura de Pacotes  

A organização dos pacotes dentro de `com.wayads` segue a arquitetura MVVM e princípios de modularidade.  

### 📂 `app`
Contém a **Activity principal** (`WayAdsApplication`) responsável por inicializar a interface do usuário e carregar a tela inicial. É a porta de entrada da aplicação Android.  

---

### 📂 `data`
Agrupa toda a camada de **dados** da aplicação.  

- **`model`** – Define as **data classes** que representam entidades e modelos de dados usados no app. Exemplo: `Ad`.  
- **`network`** – Contém interfaces de serviços de rede (ex: Retrofit) para comunicação com APIs externas. Exemplo: `ApiService`.  
- **`repository`** – Implementa os **repositórios**, que centralizam o acesso a dados (rede, cache, banco local) e expõem métodos para os `ViewModels`. Exemplo: `AdRepository`, `CategoryRepository`.  

---

### 📂 `di`
Contém os módulos de **Injeção de Dependência** (ex: Hilt ou Koin).  
Aqui são declarados os bindings para repositórios, serviços e outras dependências globais. Exemplo: `AppModule`.  

---

### 📂 `ui`
Agrupa tudo relacionado à **interface do usuário**.  

- **`home`** – Tela principal da aplicação (`HomeScreen`) e seu `ViewModel` (`HomeViewModel`).  
- **`atualidades`** – Tela/fluxo relacionado a conteúdos de atualidades.  
- **`gastronomia`** – Tela/fluxo relacionado à categoria de gastronomia.  
- **`navigation`** – Define a navegação entre telas utilizando o sistema de `NavHost` do Jetpack Compose.  
- **`theme`** – Centraliza a configuração visual do app (cores, tipografia e estilos). Arquivos: `Color.kt`, `Theme.kt`, `Type.kt`.  

---

## Benefícios da Estrutura

- **Organização clara:** cada camada tem seu lugar definido.  
- **Fácil colaboração:** novos contribuidores conseguem entender rapidamente onde implementar ou modificar funcionalidades.  
- **Escalabilidade:** novas features podem ser adicionadas criando novos pacotes dentro de `ui`, `repository` ou `model`, sem afetar o restante do código.  

---

## Próximos Passos

- Expandir a documentação de cada módulo (`ui/atualidades`, `ui/gastronomia`) à medida que forem evoluindo.  
- Detalhar convenções de código (ex: sufixos `Repository`, `ViewModel`, `Screen`).  
- Adicionar exemplos de como consumir os repositórios e expor estados via `StateFlow` para a UI.  
