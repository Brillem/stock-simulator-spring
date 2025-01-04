package ucab.edu.ve.stocksimulator.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import util.PasswordUtil;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private static final String EMAIL_SUBJECT = "Código de Confirmación Stock Simualator";
    private static final String EMAIL_BODY = "Su Código de Confirmación es ${PLACEHOLDER}.}";
    private static final String EMAIL_FROM = "stocksimulator@gmail.com";

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String toEmail, String confirmationCode) {
        String body = EMAIL_BODY.replace("${PLACEHOLDER}", confirmationCode);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(toEmail);
        message.setSubject(EMAIL_SUBJECT);
        message.setText(body);
        mailSender.send(message);
    }
}
