package ru.doublebyte.trendingstream.structs;

public class Trend {

    private String name;
    private String url;
    private String description = "";
    private String language = "";
    private String stars = "";
    private String forks = "";
    private String starsCurrent = "";

    ///////////////////////////////////////////////////////////////////////////

    public Trend() {

    }

    @Override
    public String toString() {
        return String.format("Trend{name='%s', url='%s', description='%s', language='%s', stars='%s', forks='%s', starsCurrent='%s'}",
                name, url, description, language, stars, forks, starsCurrent);
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getStarsCurrent() {
        return starsCurrent;
    }

    public void setStarsCurrent(String starsCurrent) {
        this.starsCurrent = starsCurrent;
    }
}
