package net.protheos.crawler.controller;


import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import net.protheos.crawler.model.PageCrawler;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlerParser {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PageCrawler p = new PageCrawler("https://www.nytimes.com/2017/08/28/us/politics/trump-tower-putin-felix-sater.html?mcubz=1");

        LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz", "-retainTmpSubcategories");
        TreebankLanguagePack tlp = lp.getOp().langpack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        String[] extractedSentences = p.getExtractedSentences();

        for(int i = 0; i< extractedSentences.length; i++) {
            System.out.println("Sentence["+ i + "] = " + extractedSentences[i]);

            Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(extractedSentences[i]));
            List<? extends HasWord> sentenceTokenized = toke.tokenize();
            Tree parse = lp.parse(sentenceTokenized);

            //System.out.println("--------------------------TREE--------------------------");
            //parse.pennPrint();

            ArrayList<TaggedWord> taggedArray = parse.taggedYield();
            System.out.println("--------------------------------------------------------");

           System.out.println("\nTagged Yield: " + taggedArray);

           for(int jz = 0; jz<taggedArray.size(); jz++)
               System.out.println("\nTagged Yield: " + taggedArray.get(jz).word() + " - " + taggedArray.get(jz).tag());
            System.out.println("--------------------------------------------------------");


            /*
            System.out.println("\n---------------------NEW-----------------------------");
            Sentence newSentence = new Sentence(extractedSentences[i]);
            List<String> nerTags = newSentence.nerTags();  // [PERSON, O, O, O, O, O, O, O]
            String firstPOSTag = newSentence.posTag(0);   // NNP

            System.out.println("For the sentence: " + newSentence);
            System.out.println("The tags are: " + nerTags);
            System.out.println("The POS Tag is: " + firstPOSTag);
            */
        }



    }
}
