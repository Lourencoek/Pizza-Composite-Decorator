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