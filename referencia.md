/*
 * =======================================================================
 * PARTE 1: ESTRUTURA DO PEDIDO (PADRÃO COMPOSITE)
 * =======================================================================
 * Define a estrutura hierárquica dos itens de um pedido.
 * Um pedido pode ser composto por produtos individuais (folhas) ou
 * por conjuntos de outros itens (composites), que podem conter tanto
 * produtos quanto outros conjuntos.
 */

/**
 * @interface ItemDePedido
 * Define o contrato comum para todos os elementos de um pedido (folhas e composites).
 * classe base que lança erros para simular uma interface.
 */
class ItemDePedido {
    obterNome() {
        throw new Error("Método 'obterNome()' deve ser implementado.");
    }

    obterPreco() {
        throw new Error("Método 'obterPreco()' deve ser implementado.");
    }

    // Adicionado para facilitar a impressão da árvore de itens
    gerarEstrutura(indent = "") {
        return `${indent}${this.obterNome()} - R$ ${this.obterPreco().toFixed(2)}\n`;
    }
}

/**
 * @class Produto
 * Representa a "folha" (leaf) no padrão Composite.
 * É um item individual que não pode conter outros itens.
 * @extends ItemDePedido
 */
class Produto extends ItemDePedido {
    constructor(nome, preco) {
        super();
        this.nome = nome;
        this.preco = preco;
    }

    obterNome() {
        return this.nome;
    }

    obterPreco() {
        return this.preco;
    }
}

/**
 * @class ConjuntoDeItens
 * Representa o "composite" no padrão.
 * É um grupo de itens que pode incluir tanto Produtos quanto outros ConjuntosDeItens.
 * Pode ter um custo adicional próprio (ex: taxa de montagem de combo, borda recheada).
 * @extends ItemDePedido
 */
class ConjuntoDeItens extends ItemDePedido {
    constructor(nome, custoAdicional = 0) {
        super();
        this.nome = nome;
        this.custoAdicional = custoAdicional;
        this.itens = [];
    }

    adicionar(item) {
        if (item instanceof ItemDePedido) {
            this.itens.push(item);
        } else {
            throw new Error("Somente instâncias de ItemDePedido podem ser adicionadas.");
        }
    }

    remover(item) {
        const index = this.itens.indexOf(item);
        if (index > -1) {
            this.itens.splice(index, 1);
        }
    }

    obterNome() {
        return this.nome;
    }

    obterPreco() {
        // O preço do conjunto é a soma do seu custo adicional mais o preço de todos os seus itens internos.
        const precoDosItens = this.itens.reduce((total, item) => total + item.obterPreco(), 0);
        return this.custoAdicional + precoDosItens;
    }

    // Sobrescreve para mostrar a estrutura hierárquica
    gerarEstrutura(indent = "") {
        let estrutura = `${indent}${this.obterNome()}`;
        if (this.custoAdicional > 0) {
            estrutura += ` (Custo extra: R$ ${this.custoAdicional.toFixed(2)})`;
        }
        estrutura += ` - Subtotal: R$ ${this.obterPreco().toFixed(2)}\n`;

        this.itens.forEach(item => {
            estrutura += item.gerarEstrutura(indent + "  ");
        });
        return estrutura;
    }
}

/*
 * =======================================================================
 * PARTE 2: SISTEMA DE NOTIFICAÇÃO (PADRÃO DECORATOR)
 * =======================================================================
 * Cria um sistema flexível para notificar o cliente por diferentes canais.
 * Começamos com uma notificação base (Email) e a "decoramos" com
 * funcionalidades adicionais (SMS, WhatsApp) em tempo de execução.
 */

/**
 * @interface Notificador
 * Contrato para todos os notificadores.
 */
class Notificador {
    enviar(mensagem, cliente) {
        throw new Error("Método 'enviar()' deve ser implementado.");
    }
}

/**
 * @class NotificadorEmail
 * Implementação concreta base do notificador. Sempre será executado.
 * @extends Notificador
 */
class NotificadorEmail extends Notificador {
    enviar(mensagem, cliente) {
        console.log(`[EMAIL ENVIADO para ${cliente.email}] -> ${mensagem}`);
    }
}

/**
 * @class NotificadorDecorator
 * Classe base abstrata para os decoradores.
 * Mantém uma referência ao objeto notificador que ele "decora".
 * @extends Notificador
 */
class NotificadorDecorator extends Notificador {
    constructor(notificador) {
        super();
        if (!(notificador instanceof Notificador)) {
            throw new Error("O decorador deve envolver uma instância de Notificador.");
        }
        this.notificadorEncapsulado = notificador;
    }

    enviar(mensagem, cliente) {
        // A lógica de envio será implementada pelos decoradores concretos,
        // mas todos devem chamar o método do objeto encapsulado.
        this.notificadorEncapsulado.enviar(mensagem, cliente);
    }
}

/**
 * @class NotificadorSMS
 * Decorador concreto para enviar notificações via SMS.
 * @extends NotificadorDecorator
 */
class NotificadorSMS extends NotificadorDecorator {
    enviar(mensagem, cliente) {
        // 1. Chama o método do objeto encapsulado (pode ser o Email ou outro decorador)
        super.enviar(mensagem, cliente);
        // 2. Adiciona sua própria funcionalidade
        if (cliente.sms) {
            console.log(`[SMS ENVIADO para ${cliente.sms}] -> ${mensagem}`);
        }
    }
}

/**
 * @class NotificadorWhatsApp
 * Decorador concreto para enviar notificações via WhatsApp.
 * @extends NotificadorDecorator
 */
class NotificadorWhatsApp extends NotificadorDecorator {
    enviar(mensagem, cliente) {
        // 1. Chama o método do objeto encapsulado
        super.enviar(mensagem, cliente);
        // 2. Adiciona sua própria funcionalidade
        if (cliente.whatsapp) {
            console.log(`[WHATSAPP ENVIADO para ${cliente.whatsapp}] -> ${mensagem}`);
        }
    }
}

/*
 * =======================================================================
 * PARTE 3: CLASSE PRINCIPAL DO PEDIDO
 * =======================================================================
 * Gerencia o pedido como um todo, lidando com os dados do cliente,
 * a árvore de itens, o cálculo do total e o processo de checkout.
 */

class Pedido {
    constructor(cliente, arvoreDeItens) {
        this.cliente = cliente;
        this.arvoreDeItens = arvoreDeItens;
        this.status = "PENDENTE";
    }

    obterTotal() {
        return this.arvoreDeItens.obterPreco();
    }

    gerarResumoTexto() {
        console.log("=========================================");
        console.log("        RESUMO DO PEDIDO DA PIZZARIA     ");
        console.log("=========================================");
        console.log(`CLIENTE: ${this.cliente.nome}`);
        console.log(`STATUS: ${this.status}\n`);
        console.log("ITENS:");
        console.log("-----------------------------------------");
        console.log(this.arvoreDeItens.gerarEstrutura());
        console.log("-----------------------------------------");
        console.log(`TOTAL DO PEDIDO: R$ ${this.obterTotal().toFixed(2)}`);
        console.log("=========================================\n");
    }

    realizarCheckout(notificador) {
        this.status = "PAGO";
        console.log("Processando checkout...");
        console.log(`Status do pedido alterado para: ${this.status}`);

        const total = this.obterTotal().toFixed(2);
        const mensagem = `Pedido confirmado! Total: R$ ${total}`;

        notificador.enviar(mensagem, this.cliente);
        console.log("Checkout finalizado.\n");
    }
}

/*
 * =======================================================================
 * PARTE 4: MONTAGEM E DEMONSTRAÇÃO
 * =======================================================================
 */

// 1. Definindo dados do cliente
const cliente = {
    nome: "Ana Silva",
    email: "ana.silva@example.com",
    sms: "+5511987654321",
    whatsapp: "+5511987654321",
};

// 2. Montando a árvore de itens do pedido (Composite) com 3 níveis

// Nível 3 (Itens mais internos)
const ingredienteExtra = new Produto("Queijo extra", 5.00);
const bebida1 = new Produto("Refrigerante 2L", 10.00);
const bebida2 = new Produto("Água Mineral", 4.00);
const sobremesa = new Produto("Pizza de Chocolate pequena", 25.00);

// Nível 2 (Conjuntos que agrupam os itens do nível 3)
const pizzaCalabresa = new ConjuntoDeItens("Pizza de Calabresa G com borda recheada", 5.50); // Custo extra da borda
pizzaCalabresa.adicionar(new Produto("Pizza de Calabresa base", 40.00));
pizzaCalabresa.adicionar(ingredienteExtra); // Adicionando queijo extra à pizza

const comboBebidas = new ConjuntoDeItens("Combo de Bebidas");
comboBebidas.adicionar(bebida1);
comboBebidas.adicionar(bebida2);

// Nível 1 (O pedido principal, que agrupa os conjuntos do nível 2)
const pedidoCompleto = new ConjuntoDeItens("Pedido Principal com Taxa de Entrega", 7.00); // Custo extra da entrega
pedidoCompleto.adicionar(pizzaCalabresa);
pedidoCompleto.adicionar(comboBebidas);
pedidoCompleto.adicionar(sobremesa);

// 3. Criando a instância do Pedido
const meuPedido = new Pedido(cliente, pedidoCompleto);

// 4. Gerando o resumo do pedido antes do pagamento
console.log("### ETAPA 1: Visualizando o pedido antes do pagamento ###\n");
meuPedido.gerarResumoTexto();

// 5. Preparando o sistema de notificação (Decorator)
// A ordem é importante para o encadeamento: a mensagem será enviada na ordem inversa da criação.
// Para enviar E-mail -> SMS -> WhatsApp, criamos assim:
const notificadorBase = new NotificadorEmail();
const notificadorComSms = new NotificadorSMS(notificadorBase);
const notificadorFinal = new NotificadorWhatsApp(notificadorComSms); // O mais externo é chamado primeiro

// 6. Realizando o checkout
console.log("\n### ETAPA 2: Realizando o checkout e enviando notificações ###\n");
meuPedido.realizarCheckout(notificadorFinal);

// 7. Verificando o status final do pedido
console.log("### ETAPA 3: Visualizando o pedido após o pagamento ###\n");
meuPedido.gerarResumoTexto();