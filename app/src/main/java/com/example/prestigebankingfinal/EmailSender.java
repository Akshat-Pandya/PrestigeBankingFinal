package com.example.prestigebankingfinal;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender  extends AsyncTask<Void, Void, Boolean> {
    String to,subject,body;

    public EmailSender(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            // Your email sending code here
            // This is where you should send the email using the JavaMail code
            final String username = "akshatpandya27072004@gmail.com";
            final String password = "nkcywzdsjexcayhr";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Log.d("EMAIL SENT","Email sent successfully");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return true; // Successful email sending
        }
        catch (Exception e) {
            e.printStackTrace();
            return false; // Email sending failed
        }
    }
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            // Email sent successfully
            Log.d("SUCCESS","Email sent successfully ");

        } else {
            Log.d("FAILED","Email could not be sent ");
            // Email sending failed
        }
    }
}
