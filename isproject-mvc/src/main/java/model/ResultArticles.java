package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ResultArticles {

    private ResultArticles() {

    }

    public static List<Article> getArticles(JsonElement elements) {

        JsonArray postArray = elements.getAsJsonObject().getAsJsonArray("posts");
        List<Article> articles = new ArrayList<>();

        for(JsonElement o : postArray) {
//            System.out.println(o);
            JsonElement thread = o.getAsJsonObject().get("thread");
            Article a = new Article();
            a.setUrl(thread.getAsJsonObject().get("url").getAsString());

            a.setTitle(thread.getAsJsonObject().get("title").getAsString());
            a.setAuthor(o.getAsJsonObject().get("author").getAsString());
            a.setDescription(o.getAsJsonObject().get("text").getAsString());
            a.setSource(thread.getAsJsonObject().get("site").getAsString());

            if(!thread.getAsJsonObject().get("main_image").isJsonNull()) {
                a.setUrlToImage(thread.getAsJsonObject().get("main_image").getAsString());
            } else {
                a.setUrlToImage("white.png");
            }

            a.setPublishedAt(thread.getAsJsonObject().get("published").getAsString());
            articles.add(a);
        }

        return articles;
    }

}
