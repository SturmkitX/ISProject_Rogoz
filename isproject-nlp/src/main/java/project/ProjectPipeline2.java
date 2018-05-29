package project;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class ProjectPipeline2 {

    private ProjectPipeline2() {

    }

    private static StanfordCoreNLP pipeline = null;

    public static CoreDocument annotateText(String text) {
        if(pipeline == null) {
            System.out.println("Creating pipeline for first use");
            pipeline = createPipeline();
        }

        // create a document object
        CoreDocument document = new CoreDocument(text);

        // annnotate the document
        pipeline.annotate(document);

        return document;
    }

    private static StanfordCoreNLP createPipeline() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,parse,lemma,sentiment,ner");
        props.setProperty("ner.useSUTime", "1");
        props.setProperty("threads", "8");

        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        return pipeline;
    }

//    public static void main(String[] args) {
//
//
//
//
//        for(CoreSentence sentence : document.sentences()) {
//            System.out.println("Sentence:");
//            System.out.println(sentence.text());
//            System.out.println("Parse tree: ");
//            System.out.println(sentence.constituencyParse().toString());
//            System.out.println("Sentiment: ");
//            System.out.println(sentence.sentiment());
//            System.out.println("Sentiment tree: ");
//            System.out.println(sentence.sentimentTree());
//            System.out.println("Quotes");
//            System.out.println(sentence);
//
//            TreeJPanel panel = new TreeJPanel();
//            panel.setTree(sentence.sentimentTree());
//            panel.setBackground(Color.WHITE);
//            panel.draw();
//        }
//    }
}
