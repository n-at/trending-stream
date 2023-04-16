package ru.doublebyte.trendingstream.services;

import org.springframework.scheduling.annotation.Scheduled;

public class Runner {

    private final TrendsReader trendsReader;
    private final MailMessageSender mailMessageSender;

    ///////////////////////////////////////////////////////////////////////////

    public Runner(TrendsReader trendsReader, MailMessageSender mailMessageSender) {
        this.trendsReader = trendsReader;
        this.mailMessageSender = mailMessageSender;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Scheduled(cron = "${trending-stream.refresh-cron}")
    public void run() {
        mailMessageSender.sendTrends(
                trendsReader.getTrends(TrendsReader.URL_DAILY),
                trendsReader.getTrends(TrendsReader.URL_WEEKLY),
                trendsReader.getTrends(TrendsReader.URL_MONTHLY)
        );
    }
}
