package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.ArticleDTO;
import utils.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleController implements Initializable {

    private ArticleDTO article = UserSession.getCurrentArticle();

    @FXML // fx:id="articleTitle"
    private Text articleTitle; // Value injected by FXMLLoader

    @FXML // fx:id="articleSource"
    private Text articleSource; // Value injected by FXMLLoader

    @FXML // fx:id="articleAuthor"
    private Text articleAuthor; // Value injected by FXMLLoader

    @FXML // fx:id="articleUrl"
    private Text articleUrl; // Value injected by FXMLLoader

    @FXML // fx:id="articleDate"
    private Text articleDate; // Value injected by FXMLLoader

    @FXML // fx:id="articleSentiment"
    private Text articleSentiment; // Value injected by FXMLLoader

    @FXML // fx:id="articleNarTime"
    private Text articleNarTime; // Value injected by FXMLLoader

    @FXML // fx:id="articleImage"
    private ImageView articleImage; // Value injected by FXMLLoader

    @FXML // fx:id="articleDescription"
    private TextArea articleDescription; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articleTitle.textProperty().bind(article.titleProperty());
        articleSource.textProperty().bind(article.sourceProperty());
        articleAuthor.textProperty().bind(article.authorProperty());
        articleUrl.textProperty().bind(article.urlProperty());
        articleDate.textProperty().bind(article.publishedAtProperty());
        articleSentiment.textProperty().bind(article.sentimentProperty());
        articleNarTime.textProperty().bind(article.narTimeProperty());
        articleImage.imageProperty().set(article.getImage());
        articleDescription.textProperty().bind(article.descriptionProperty());
    }
}
