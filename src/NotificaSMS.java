class NotificadorSMS extends NotificadorDecorator {
    enviar(mensagem, cliente) {
        // 1. Chama o método do objeto encapsulado (pode ser o Email ou outro)
        super.enviar(mensagem, cliente);
        // 2. Adiciona sua própria funcionalidade
        if (cliente.sms) {
            console.log(`[SMS ENVIADO para ${cliente.sms}] -> ${mensagem}`);
        }
    }
}
