package ru.doublebyte.trendingstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MainConfiguration {

    private final JavaMailSender mailSender;

    @Autowired
    public MainConfiguration(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

}
