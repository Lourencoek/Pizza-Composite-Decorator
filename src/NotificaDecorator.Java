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
