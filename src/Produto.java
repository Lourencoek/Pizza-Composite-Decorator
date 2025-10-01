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
