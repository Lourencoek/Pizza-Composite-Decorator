O que você vai achar nesse projeto

Estrutura de itens de pedido com três níveis ou mais (produto simples, conjuntos de itens e sub-conjuntos).

Cálculo de total com possibilidade de custo extra aplicado a um conjunto (ex.: borda, entrega).

Classe Pedido que recebe dados do cliente (nome, email, sms, whatsapp) e a árvore de itens.

Métodos: obterTotal(), gerarResumoTexto() (imprime o pedido) e realizarCheckout(notificador) que altera o status para “PAGO” e dispara a mensagem de confirmação “Pedido confirmado! Total: R$ X,XX”.

Sistema de notificação baseado em Notificador (base) e NotificadorEmail, com decoradores NotificadorSMS e NotificadorWhatsApp encadeados para enviar a mesma mensagem por email, SMS e WhatsApp na ordem.

Estrutura de classes e padrões de projeto

ItemDePedido: contrato comum (interface) para todos os itens de pedido.

Produto: folha (leaf) com métodos obterNome() e obterPreco().

ConjuntoDeItens: composite que agrupa itens de Pedido (podendo conter Produtos e/ou outros Conjuntos).

Pedido: representa um pedido completo, contendo dados do cliente e a árvore de itens; expõe obterTotal() e gerarResumoTexto(); realizarCheckout(notificador) gerencia o estado de pagamento e notificação.

Notificadores:

Notificador: interface/base para envio de mensagens.

NotificadorEmail: implementação básica de envio por e-mail.

NotificadorSMS: decorator que expande a notificação para SMS.

NotificadorWhatsApp: decorator que expande a notificação para WhatsApp.

Encadeamento: os decorators são conectados para enviar a mesma mensagem por e-mail, SMS e WhatsApp na sequência.

Requisitos de implementação

Construir pelo menos um pedido com pelo menos três níveis de itens (ex.: pizza simples, com borda, combinação de itens, etc.).

Aplicar custo extra em pelo menos um conjunto (ex.: borda de sabor, entrega).

A árvore de itens deve ser capaz de calcular o total incluindo custos adicionais.

A classe Pedido deve receber: nome do cliente, email, sms, whatsapp e a árvore de itens.

Notificações devem enviar a mensagem “Pedido confirmado! Total: R$ X,XX” por email, SMS e WhatsApp na ordem através do encadeamento de Notificador, NotificadorEmail, NotificadorSMS e NotificadorWhatsApp.

Como funciona (fluxo de uso)

Criar itens de pedido (Produtos) e aninhar em ConjuntosDeItens para formar uma estrutura hierárquica com pelo menos três níveis.

Aplicar custos extras a conjuntos apropriados (ex.: borda, entrega).

Criar um objeto Pedido com os dados do cliente e a árvore de itens.

Chamar obterTotal() para calcular o total.

Chamar gerarResumoTexto() para imprimir o resumo do pedido.

Chamar realizarCheckout(notificador) para confirmar o pagamento e disparar a notificação para email, SMS e WhatsApp na sequência.

Arquitetura de notificação

Notificador inicia o fluxo de envio com a mensagem.

NotificadorEmail envia por e-mail.

NotificadorSMS decorador adiciona envio por SMS.

NotificadorWhatsApp decorador adiciona envio por WhatsApp.

O encadeamento garante que a mesma mensagem seja enviada por todos os canais na ordem definida.

Como começar

Este projeto é estruturado para facilitar a extensão de itens, pagamentos e canais de notificação.

Estruturas recomendadas de pastas: src/models (ItemDePedido, Produto, ConjuntoDeItens, Pedido), src/notificacoes (Notificador, NotificadorEmail, NotificadorSMS, NotificadorWhatsApp), src/utils e testes correspondentes.

Requisitos comuns: Node.js e um gerenciador de pacotes (npm ou yarn) para execução e testes.

Benefícios

Flexibilidade para construir pedidos complexos com várias camadas de itens.

Extensibilidade fácil para adicionar novos tipos de itens ou novos canais de notificação.

Fluxo de checkout claro com confirmação automática ao concluir o pagamento.
