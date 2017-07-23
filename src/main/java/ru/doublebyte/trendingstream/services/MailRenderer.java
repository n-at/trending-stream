package ru.doublebyte.trendingstream.services;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.doublebyte.trendingstream.structs.Trend;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Render trends email message
 */
public class MailRenderer {

    private static final Logger logger = LoggerFactory.getLogger(MailRenderer.class);

    private static final String TEMPLATE_PATH = "templates/mail.pebble";

    private final PebbleTemplate template;

    public MailRenderer() {
        PebbleEngine pebbleEngine = new PebbleEngine.Builder().build();

        try {
            template = pebbleEngine.getTemplate(TEMPLATE_PATH);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * render message
     * @param trendsDaily Daily trends list
     * @param trendsWeekly Weekly trends list
     * @param trendsMonthly Monthly trends list
     * @return Rendered string
     */
    public String render(List<Trend> trendsDaily, List<Trend> trendsWeekly, List<Trend> trendsMonthly) {
        if (trendsDaily == null) {
            trendsDaily = new ArrayList<>();
        }
        if (trendsWeekly == null) {
            trendsWeekly = new ArrayList<>();
        }
        if (trendsMonthly == null) {
            trendsMonthly = new ArrayList<>();
        }

        Map<String, Object> context = new HashMap<>();
        context.put("daily", trendsDaily);
        context.put("weekly", trendsWeekly);
        context.put("monthly", trendsMonthly);

        logger.info("rendering template...");

        try {
            Writer writer = new StringWriter();
            template.evaluate(writer, context);
            return writer.toString();
        } catch (Exception e) {
            logger.warn("rendering error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}