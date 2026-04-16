# language: pt

Funcionalidade: Login simples

  Cenário: Abrir página inicial
    Dado que o usuário acessa a página inicial
    Quando a página carregar completamente
    Então o título da página deve ser exibido corretamente