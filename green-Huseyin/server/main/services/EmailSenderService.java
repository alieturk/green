package app.service;

public interface EmailSenderService {
    void sendEmail(String toWhom, String subject, String message );
}