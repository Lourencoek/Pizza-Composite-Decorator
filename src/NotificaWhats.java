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
