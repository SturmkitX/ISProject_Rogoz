package utils;

import model.ArticleDTO;

public class UserSession {

    private static ArticleDTO currentArticle = null;

    private UserSession() {

    }

    public static ArticleDTO getCurrentArticle() {
        return currentArticle;
    }

    public static void setCurrentArticle(ArticleDTO currentArticle) {
        UserSession.currentArticle = currentArticle;
    }
}
