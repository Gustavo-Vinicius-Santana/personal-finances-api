package br.com.finance_project.personal_finance_api.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {

    @Value("${MAIL_FROM}")
    private String mailFrom;

    @Value("${KEY_RESEND}")
    private String keyResend;

    @Override
    public Void sendResetCode(String to, String code) {
        Resend resend = new Resend(keyResend);
        
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(mailFrom)
                .to(to)
                .subject("Recover password")
                .html("""
                        <h2>Recuperação de senha</h2>
                        <p>Seu código é:</p>
                        <h1>%s</h1>
                        """.formatted(code)
                )
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("Email enviado! ID: " + response.getId());
        } catch (Exception e) {

            System.err.println("Erro ao enviar email para " + to);
            e.printStackTrace();

            throw new RuntimeException(
                    "Não foi possível enviar o código de recuperação. Tente novamente mais tarde."
            );
        }

        return null;
    }
}
