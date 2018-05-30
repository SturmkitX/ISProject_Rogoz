package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleDTO;

import java.io.*;

public class UserSession {

    private static ArticleDTO currentArticle = null;
    private static ObservableList<ArticleDTO> starred = FXCollections.observableArrayList();
    private static ObservableList<ArticleDTO> nearFuture = FXCollections.observableArrayList();

    private UserSession() {

    }

    public static ArticleDTO getCurrentArticle() {
        return currentArticle;
    }

    public static void setCurrentArticle(ArticleDTO currentArticle) {
        UserSession.currentArticle = currentArticle;
    }

    public static ObservableList<ArticleDTO> getStarred() {
        return starred;
    }

    public static void addStarred(ArticleDTO dto) {
        starred.add(dto);
    }

    public static void saveStarred() {
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream("saved.ser"));
            for(ArticleDTO dto : starred) {
                out.writeObject(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStarred() {
        try {
            ObjectInput in = new ObjectInputStream(new FileInputStream("saved.ser"));
            while(in.available() > 0) {
                ArticleDTO dto = (ArticleDTO) in.readObject();
                starred.add(dto);
            }
        } catch (IOException e) {
            System.out.println("No saved articles, yet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<ArticleDTO> getNearFuture() {
        return nearFuture;
    }
}
