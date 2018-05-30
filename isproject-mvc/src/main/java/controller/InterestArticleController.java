package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ArticleDTO;
import utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterestArticleController implements Initializable {

    @FXML
    private ListView<ArticleDTO> articleView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articleView.setItems(UserSession.getNearFuture());

        articleView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleDTO>() {
            @Override
            public void changed(ObservableValue<? extends ArticleDTO> observable, ArticleDTO oldValue, ArticleDTO newValue) {
                UserSession.setCurrentArticle(newValue);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../xmls/article.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setTitle("Article");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
