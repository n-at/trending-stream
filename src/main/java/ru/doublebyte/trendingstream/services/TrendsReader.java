package ru.doublebyte.trendingstream.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.doublebyte.trendingstream.structs.Trend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Read trends list from GitHub
 */
public class TrendsReader {

    private static final Logger logger = LoggerFactory.getLogger(TrendsReader.class);

    public static final String URL_DAILY = "https://github.com/trending?since=daily";
    public static final String URL_WEEKLY = "https://github.com/trending?since=weekly";
    public static final String URL_MONTHLY = "https://github.com/trending?since=monthly";

    ///////////////////////////////////////////////////////////////////////////

    public TrendsReader() {

    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Read trends by URL
     * @param url Url
     * @return Trends list
     */
    public List<Trend> getTrends(String url) {
        Elements repositories;

        logger.info("reading trends from {}", url);

        try {
            Document document = Jsoup.connect(url).get();
            repositories = document.select("ol.repo-list li");
        } catch (Exception e) {
            logger.error("trends read error", e);
            return new ArrayList<>();
        }

        return repositories.stream()
                .map(repo -> {
                    try {
                        Elements repoChildren = repo.children();

                        Element nameElement = repoChildren.get(0);
                        Element urlElement = nameElement.select("a").first();
                        Element descriptionElement = repoChildren.get(2);
                        Elements infoElements = repoChildren.get(3).children();

                        Element languageElement, starsElement, forksElement, starsCountElement;

                        if (infoElements.get(0).nodeName().equals("span")) {
                            languageElement = infoElements.get(0);
                            starsElement = infoElements.get(1);
                            forksElement = infoElements.get(2);
                            starsCountElement = infoElements.get(4);
                        } else {
                            languageElement = null;
                            starsElement = infoElements.get(0);
                            forksElement = infoElements.get(1);
                            starsCountElement = infoElements.get(3);
                        }

                        Trend trend = new Trend();
                        trend.setName(nameElement.text());
                        trend.setUrl(urlElement.attr("href"));
                        trend.setDescription(descriptionElement.text());
                        trend.setLanguage(languageElement == null ? "" : languageElement.text());
                        trend.setStars(starsElement.text());
                        trend.setForks(forksElement.text());
                        trend.setStarsCurrent(starsCountElement.text());
                        return trend;
                    } catch (Exception e) {
                        logger.warn("trend read error from {}: {}", url, e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
