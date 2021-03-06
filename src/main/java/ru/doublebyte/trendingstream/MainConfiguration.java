package ru.doublebyte.trendingstream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.doublebyte.trendingstream.services.MailMessageSender;
import ru.doublebyte.trendingstream.services.MailRenderer;
import ru.doublebyte.trendingstream.services.Runner;
import ru.doublebyte.trendingstream.services.TrendsReader;

@Configuration
public class MainConfiguration {

    @Value("${trending-stream.mail-from}")
    public String mailFrom;

    @Value("${trending-stream.mail-to}")
    public String mailTo;

    @Value("${trending-stream.mail-subject}")
    public String mailSubject;

    private final JavaMailSender mailSender;

    @Autowired
    public MainConfiguration(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public MailRenderer mailRenderer() {
        return new MailRenderer();
    }

    @Bean
    public MailMessageSender mailMessageSender() {
        return new MailMessageSender(mailSender, mailRenderer(), mailFrom, mailTo, mailSubject);
    }

    @Bean
    public TrendsReader trendsReader() {
        return new TrendsReader();
    }

    @Bean
    public Runner runner() {
        return new Runner(trendsReader(), mailMessageSender());
    }

}
