* Contrato para todos os notificadores.
 */
class Notificador {
    enviar(mensagem, cliente) {
        throw new Error("Método 'enviar()' deve ser implementado.");
    }
}
