package br.com.estudos.testes.client;

/**
 * Envia e-mails. Classico caso de dependencia que voce NAO quer executar
 * de verdade num teste: use @Mock e verifique com verify(...).
 */
public interface NotificadorDeEmail {

    void enviar(String destinatario, String assunto, String corpo);
}
