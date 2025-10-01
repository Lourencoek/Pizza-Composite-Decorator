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


*
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