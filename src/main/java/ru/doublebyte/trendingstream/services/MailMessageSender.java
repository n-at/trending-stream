package ru.doublebyte.trendingstream.services;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.doublebyte.trendingstream.structs.Trend;

import java.util.List;

/**
 * Message sender
 */
public class MailMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MailMessageSender.class);

    private final JavaMailSender javaMailSender;
    private final MailRenderer mailRenderer;
    private final String from;
    private final String to;
    private final String subject;

    public MailMessageSender(
            JavaMailSender javaMailSender,
            MailRenderer mailRenderer,
            String from,
            String to,
            String subject
    ) {
        this.javaMailSender = javaMailSender;
        this.mailRenderer = mailRenderer;
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Send trends email
     * @param trendsDaily Daily trends list
     * @param trendsWeekly Weekly trends list
     * @param trendsMonthly Monthly trends list
     */
    public void sendTrends(List<Trend> trendsDaily, List<Trend> trendsWeekly, List<Trend> trendsMonthly) {
        try {
            logger.info("sending message...");

            String message = mailRenderer.render(trendsDaily, trendsWeekly, trendsMonthly);
            MimeMessage mimeMessage = createMessage(message);
            javaMailSender.send(mimeMessage);

            logger.info("message sent");
        } catch (Exception e) {
            logger.warn("message send error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create HTML mail message
     * @param html HTML-code
     * @return Message
     */
    private MimeMessage createMessage(String html) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        } catch (Exception e) {
            logger.warn("mail message error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return message;
    }

}
