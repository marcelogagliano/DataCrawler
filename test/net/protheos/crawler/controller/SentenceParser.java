package net.protheos.crawler.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import edu.stanford.nlp.simple.*;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.*;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;


import java.awt.BorderLayout;
import java.io.File;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

class SentenceParser {

    /** Usage: ParserDemo2 [[grammar] textFile] */

    public static void main(String[] args) throws IOException, Exception  {

        LexicalizedParser lp = LexicalizedParser.loadModel("englishPCFG.ser.gz", "-retainTmpSubcategories");
        TreebankLanguagePack tlp = lp.getOp().langpack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        Iterable<List<? extends HasWord>> sentences;

        // Showing tokenization and parsing in code a couple of different ways.
        String[] sent = { "This", "is", "an", "easy", "sentence", "." };
        List<HasWord> sentence = new ArrayList<HasWord>();


        String sent2 = ("This is a slightly longer and more complex sentence requiring tokenization.");
        Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
        List<? extends HasWord> sentence2 = toke.tokenize();
        //List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
        //tmp.add(sentence);
        //tmp.add(sentence2);
        //sentences = tmp;

        Tree parse = lp.parse(sentence2);
        parse.pennPrint();
        System.out.println(parse.taggedYield());


        Sentence sent3 = new Sentence("Lucy is in the sky with diamonds.");
        List<String> nerTags = sent3.nerTags();  // [PERSON, O, O, O, O, O, O, O]
        String firstPOSTag = sent3.posTag(0);   // NNP

        System.out.println(sent3);
        System.out.println(nerTags);
        System.out.println(firstPOSTag);

    }
}