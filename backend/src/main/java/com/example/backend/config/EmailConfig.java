package com.example.backend.config;

import com.example.backend.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Configuration
public class EmailConfig {

    private final JavaMailSender mailSender;

    public EmailConfig(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /***
     * Method to send an email and a link to verify the registered account of a client
     * @param user data of a client that was registered
     * @param siteURL URL to create a link to send to the email of a client
     * @param verificationEndpointEmployee endpoint to verification email
     * @throws MessagingException exception
     * @throws UnsupportedEncodingException exception
     */
    public void sendVerificationEmail(User user, String siteURL, String verificationEndpointEmployee) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "elderarias2015@gmail.com";
        String senderName = "losprimos";
        String subject = "Por favor, verifiquese para terminar con la registracion.";
        String content = "Querido [[name]],<br>"
                + "Haga click en el enlace de abajo para verificar su cuenta:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Muchas gracias,<br>"
                + "losprimos.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/api/v1/"+ verificationEndpointEmployee +"/verifyRegisteredAccount?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
}
