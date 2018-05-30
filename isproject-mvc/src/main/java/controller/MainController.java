package controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreQuote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Article;
import model.ArticleDTO;
import model.ResultArticles;
import parser.ui.TreeJPanel;
import project.ProjectPipeline2;
import utils.UserSession;
import webhoseio.WebhoseIOClient;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class MainController implements Initializable {

    private WebhoseIOClient webhoseClient;
    private ObservableList<ArticleDTO> articles;
    private int queryPage = 1;

    @FXML // fx:id="searchField"
    private TextField searchField; // Value injected by FXMLLoader

    @FXML // fx:id="searchBtn"
    private Button searchBtn; // Value injected by FXMLLoader

    @FXML // fx:id="prevPage"
    private Button prevPage; // Value injected by FXMLLoader

    @FXML // fx:id="nextPage"
    private Button nextPage; // Value injected by FXMLLoader

    @FXML // fx:id="articleTable"
    private TableView<ArticleDTO> articleTable; // Value injected by FXMLLoader

    @FXML // fx:id="articleCol"
    private TableColumn<ArticleDTO, String> articleCol; // Value injected by FXMLLoader

    @FXML // fx:id="sentimentCol"
    private TableColumn<ArticleDTO, String> sentimentCol; // Value injected by FXMLLoader

    @FXML // fx:id="timeCol"
    private TableColumn<ArticleDTO, String> timeCol; // Value injected by FXMLLoader

    @FXML // fx:id="addDBCol"
    private TableColumn<ArticleDTO, CheckBox> addDBCol; // Value injected by FXMLLoader

    @FXML // fx:id="addDatabaseBtn"
    private Button addDatabaseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="selectBtn"
    private Button selectBtn; // Value injected by FXMLLoader

    @FXML // fx:id="deselectBtn"
    private Button deselectBtn; // Value injected by FXMLLoader

    @FXML
    void deselectBoxes(ActionEvent event) {
        for(ArticleDTO dto : articles) {
            dto.getChecked().setSelected(false);
        }
    }

    @FXML
    void decrementPage(ActionEvent event) {
        queryPage = Math.min(queryPage - 1, 1);
        Map<String, String> queries = new HashMap<String, String>();
        queries.put("q", searchField.getText());
        queries.put("size", "10");
        queries.put("sort", "relevancy");
        queries.put("from", "" + queryPage);
        queries.put("language", "english");
        try {
            articles.clear();
            articles.addAll(getArticleDTOs(ResultArticles.getArticles(webhoseClient.query("filterWebContent", queries))));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void incrementPage(ActionEvent event) {
        queryPage++;
        Map<String, String> queries = new HashMap<String, String>();
        queries.put("q", searchField.getText());
        queries.put("size", "10");
        queries.put("sort", "relevancy");
        queries.put("from", "" + queryPage);
        queries.put("language", "english");
        try {
            articles.clear();
            articles.addAll(getArticleDTOs(ResultArticles.getArticles(webhoseClient.query("filterWebContent", queries))));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchArticles(ActionEvent event) {
        Map<String, String> queries = new HashMap<String, String>();
        queries.put("q", searchField.getText());
        queries.put("size", "10");
        queries.put("sort", "relevancy");
        queries.put("from", "" + queryPage);
        queries.put("language", "english");
        try {
            articles.clear();
            articles.addAll(getArticleDTOs(ResultArticles.getArticles(webhoseClient.query("filterWebContent", queries))));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectBoxes(ActionEvent event) {
        for(ArticleDTO dto : articles) {
            dto.getChecked().setSelected(true);
        }
    }

    @FXML
    void addToDatabase(ActionEvent event) {
        for(ArticleDTO dto : articles) {
            if(dto.getChecked().isSelected()) {
                UserSession.addStarred(dto);
            }
        }
        UserSession.saveStarred();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webhoseClient = WebhoseIOClient.getInstance("fc49e41e-9b11-4b03-9a93-653839a9df4d");
        articles = FXCollections.observableArrayList();
        UserSession.loadStarred();

        articleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        sentimentCol.setCellValueFactory(new PropertyValueFactory<>("sentiment"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("narTime"));
        addDBCol.setCellValueFactory(new PropertyValueFactory<>("checked"));

        articleTable.setItems(articles);

        articleTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleDTO>() {
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

        // see if there are any articles of interest referring to the near future
        Date rightNow = new Date(System.currentTimeMillis());
        for(ArticleDTO dto : UserSession.getNearFuture()) {
            long timeDiff = dto.getSourceArticle().getLatestDate().getTime() - rightNow.getTime();
            if(timeDiff > 0 && timeDiff < 86400000L) {
                // if there is less than 1 day before the referenced date
                UserSession.getNearFuture().add(dto);
            }
        }

        if(UserSession.getNearFuture().size() > 0) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../xmls/interest.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("Article");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<ArticleDTO> getArticleDTOs(List<Article> articles) {
        ObservableList<ArticleDTO> results = FXCollections.observableArrayList();
        for(Article a : articles) {
            CoreDocument doc = ProjectPipeline2.annotateText(a.getDescription());
            a.setSentiment(doc.sentences().get(0).sentiment());

            TreeJPanel treeJPanel = new TreeJPanel();
            treeJPanel.setTree(doc.sentences().get(0).sentimentTree());
            treeJPanel.setBackground(Color.WHITE);
            a.setTreeJPanel(treeJPanel);

            String quotes = "";
            for(CoreQuote q : doc.quotes()) {
                quotes = quotes + q.toString() + "\n";
            }
            a.setQuotes(quotes);

            // get the latest date
            long sysMillis = System.currentTimeMillis();
            Date today = new Date(sysMillis);


            // set start of day
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            Date mostRecent = new Date(0L);
            for(CoreEntityMention e : doc.entityMentions()) {
                if(e.entityType().equals("DATE")) {
                    String d = e.coreMap().get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                    if(Pattern.compile("\\d{4}").matcher(d).matches()) {
                        // only the year is specified
                        DateFormat df = new SimpleDateFormat("yyyy");
                        try {
                            Date sp = df.parse(d);
                            if(sp.after(mostRecent)) {
                                mostRecent = sp;
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else if(Pattern.compile("\\d{6}").matcher(d).matches()) {
                        // only the year is specified
                        DateFormat df = new SimpleDateFormat("yyyyMM");
                        try {
                            Date sp = df.parse(d);
                            if(sp.after(mostRecent)) {
                                mostRecent = sp;
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    } else if(Pattern.compile("\\d{8}").matcher(d).matches()) {
                        // only the year is specified
                        DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        try {
                            Date sp = df.parse(d);
                            if(sp.after(mostRecent)) {
                                mostRecent = sp;
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

            if(mostRecent.getTime() == 0L) {
                // no time annotation, set publication date
                int index = a.getPublishedAt().indexOf('T');
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    mostRecent = df.parse(a.getPublishedAt().substring(0, index));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            if(mostRecent.before(today)) {
                a.setNarTime("Past");
            } else if(mostRecent.after(today)) {
                a.setNarTime("Future");
            } else {
                a.setNarTime("Present");
            }
            a.setLatestDate(mostRecent);

            System.out.println(new SimpleDateFormat("dd-MM-yyyy").format(mostRecent));
            results.add(new ArticleDTO(a));
        }

        return results;
    }
}
