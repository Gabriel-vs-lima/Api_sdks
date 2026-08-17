package br.com.estudos.testes.infra;

import br.com.estudos.testes.client.NotificadorDeEmail;
import org.springframework.stereotype.Component;

@Component
public class NotificadorDeEmailConsole implements NotificadorDeEmail {

    @Override
    public void enviar(String destinatario, String assunto, String corpo) {
        System.out.println("[e-mail] para=" + destinatario + " assunto=" + assunto);
        System.out.println("[e-mail] " + corpo);
    }
}
