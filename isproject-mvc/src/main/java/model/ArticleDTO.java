package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

import java.io.Serializable;

public class ArticleDTO implements Serializable {

    private StringProperty source;
    private StringProperty title;
    private StringProperty author;
    private StringProperty description;
    private StringProperty url;
    private Image image;
    private StringProperty publishedAt;
    private StringProperty sentiment;
    private StringProperty narTime;
    private CheckBox checked;

    public ArticleDTO(Article src) {
        System.out.println("Image URL: " + src.getSource() + " " + src.getUrlToImage());
        this.source = new SimpleStringProperty(src.getSource());
        this.author = new SimpleStringProperty(src.getAuthor());
        this.description = new SimpleStringProperty(src.getDescription());
        this.url = new SimpleStringProperty(src.getUrl());
        try {
            this.image = new Image(src.getUrlToImage());
        } catch(IllegalArgumentException e) {
            this.image = new Image("white.png");
        }

        this.publishedAt = new SimpleStringProperty(src.getPublishedAt());
        this.sentiment = new SimpleStringProperty(src.getSentiment());
        this.narTime = new SimpleStringProperty(src.getNarTime());
        this.title = new SimpleStringProperty(src.getTitle());

        this.checked = new CheckBox();
        this.checked.selectedProperty().setValue(true);
    }

    public String getSource() {
        return source.get();
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPublishedAt() {
        return publishedAt.get();
    }

    public StringProperty publishedAtProperty() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt.set(publishedAt);
    }

    public String getSentiment() {
        return sentiment.get();
    }

    public StringProperty sentimentProperty() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment.set(sentiment);
    }

    public String getNarTime() {
        return narTime.get();
    }

    public StringProperty narTimeProperty() {
        return narTime;
    }

    public void setNarTime(String narTime) {
        this.narTime.set(narTime);
    }

    public CheckBox getChecked() {
        return checked;
    }

    public void setChecked(CheckBox checked) {
        this.checked = checked;
    }
}
