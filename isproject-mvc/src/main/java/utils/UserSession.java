package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleDTO;

import java.io.*;

public class UserSession {

    private static ArticleDTO currentArticle = null;
    private static ObservableList<ArticleDTO> starred = FXCollections.observableArrayList();

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
            out.writeObject(starred);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStarred() {
        try {
            ObjectInput in = new ObjectInputStream(new FileInputStream("saved.ser"));
            starred = (ObservableList<ArticleDTO>) in.readObject();
        } catch (IOException e) {
            System.out.println("No saved articles, yet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
