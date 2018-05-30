package project;

import java.awt.*;
import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import parser.ui.TreeJPanel;

public class ProjectPipeline {

    private static String text = "The first day of spring saw the fourth nor’easter storm " +
            "in three weeks swirl its way up a weather-weary US east coast on Wednesday, " +
            "dumping more than a foot of snow from Virginia to New England and causing widespread disruption. " +
            "He said \"I won't be coming home\".";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref,sentiment,quote");
        props.setProperty("ner.useSUTime", "0");

        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create a document object
        CoreDocument document = new CoreDocument(text);

        // annnotate the document
        pipeline.annotate(document);

        for(CoreSentence sentence : document.sentences()) {
            System.out.println("Sentence:");
            System.out.println(sentence.text());
            System.out.println("Parse tree: ");
            System.out.println(sentence.constituencyParse().toString());
            System.out.println("Sentiment: ");
            System.out.println(sentence.sentiment());
            System.out.println("Sentiment tree: ");
            System.out.println(sentence.sentimentTree());
            System.out.println("Quotes");
            System.out.println(document.quotes());

            TreeJPanel panel = new TreeJPanel();
            panel.setTree(sentence.sentimentTree());
            panel.setBackground(Color.WHITE);
            panel.draw();
        }
    }

}