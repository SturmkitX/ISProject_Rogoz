package model;

import parser.ui.TreeJPanel;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

    private String source;
    private String title;
    private String author;
    private String description;
    private String url, urlToImage;
    private String publishedAt;
    private String sentiment;
    private String narTime;
    private Date latestDate;
    private transient TreeJPanel treeJPanel;
    private String quotes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getNarTime() {
        return narTime;
    }

    public void setNarTime(String narTime) {
        this.narTime = narTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    public TreeJPanel getTreeJPanel() {
        return treeJPanel;
    }

    public void setTreeJPanel(TreeJPanel treeJPanel) {
        this.treeJPanel = treeJPanel;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
