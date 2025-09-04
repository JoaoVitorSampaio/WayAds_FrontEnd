# WayAds Frontend - Android

Este projeto é o frontend Android para o aplicativo WayAds.

## Tecnologias Utilizadas

*   **Linguagem:** Kotlin
*   **Framework UI:** Jetpack Compose
*   **Plataforma:** Android

## Arquitetura do Projeto: MVVM (Model-View-ViewModel)

### Por que MVVM?

A arquitetura MVVM foi escolhida para este projeto devido às suas diversas vantagens no desenvolvimento de aplicações Android modernas:

1.  **Separação de Responsabilidades:** O MVVM promove uma clara separação entre a lógica de negócio (Model), a interface do usuário (View) e a lógica de apresentação (ViewModel). Isso torna o código mais organizado, fácil de entender e manter.
2.  **Testabilidade Aprimorada:** A separação permite que a lógica de negócio e de apresentação (ViewModels) sejam testadas de forma independente da interface do usuário, facilitando a criação de testes unitários e melhorando a qualidade do código.
3.  **Reatividade e Observabilidade:** Com o uso de LiveData ou StateFlow (no Jetpack Compose), o MVVM facilita a criação de interfaces reativas, onde as mudanças nos dados do Model são automaticamente refletidas na View, sem a necessidade de manipulação manual da UI.
4.  **Ciclo de Vida da UI:** Os ViewModels são projetados para serem independentes do ciclo de vida da View, o que significa que eles sobrevivem a mudanças de configuração (como rotações de tela), evitando a perda de dados e a necessidade de recarregar informações.
5.  **Colaboração Simplificada:** A clara divisão de papéis permite que desenvolvedores de UI e desenvolvedores de lógica de negócio trabalhem de forma mais independente e paralela.

### Vantagens Adicionais

*   **Manutenibilidade:** O código é mais fácil de depurar e modificar devido à sua estrutura modular.
*   **Escalabilidade:** A arquitetura suporta o crescimento do projeto, permitindo a adição de novas funcionalidades sem comprometer a base de código existente.
*   **Melhor Experiência do Desenvolvedor:** A combinação de Kotlin, Jetpack Compose e MVVM oferece uma experiência de desenvolvimento mais moderna, produtiva e agradável.

---

Este README será atualizado à medida que o projeto evoluir, fornecendo mais detalhes sobre as funcionalidades e a implementação.
