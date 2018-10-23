package au.com.hypothesisconsulting.spachallenge.rest;

import au.com.hypothesisconsulting.spachallenge.mail.MailGunSender;
import au.com.hypothesisconsulting.spachallenge.mail.MailProvider;
import au.com.hypothesisconsulting.spachallenge.mail.SendGridSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailSenderResource {

    @Autowired
    MailGunSender mailGunSender;

    @Autowired
    SendGridSender sendGridSender;

    @CrossOrigin
    @PostMapping("/mail")
    public ResponseEntity<ResponseDTO> sendMail(@RequestBody MailDTO mail) {
        try {
            mailGunSender.sendMail(mail.subject,  mail.body, mail.from, mail.to, mail.cc, mail.bcc);
            return new ResponseEntity<>(new ResponseDTO("Successfully sent mail", "MailGun",false), HttpStatus.OK);
        } catch (Exception e) {
            try {
                sendGridSender.sendMail(mail.subject, mail.body, mail.from, mail.to, mail.cc, mail.bcc);
                return new ResponseEntity<>(new ResponseDTO("Successfully sent mail","SendGrid",false), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(new ResponseDTO("Unable to send email", null,true), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
