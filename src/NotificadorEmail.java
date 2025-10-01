class NotificadorEmail extends Notificador {
    enviar(mensagem, cliente) {
        console.log(`[EMAIL ENVIADO para ${cliente.email}] -> ${mensagem}`);
    }
}
