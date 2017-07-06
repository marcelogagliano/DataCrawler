package net.protheos.crawler;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */

/**
 * @author oo7021
 *
 */
public class PageCrawler {

	private String url;
	private Document doc;
	private Elements links; 
	private Elements media; 
	private Elements imports;
	private static Logger LOGGER;	

	public PageCrawler(String url) {
		LOGGER = Logger.getLogger(this.getClass().getName() + " - " + url);
		this.setUrl(url);
		try{
			this.setDoc(Jsoup.connect(url).get());
		}
		catch(Exception e){
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		LOGGER.log( Level.INFO, "Crawler created for URL " + this.getUrl() + ".");

	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Document getDoc() {
		return doc;
	}



	public void setDoc(Document doc) {
		this.doc = doc;
	}



	public Elements getLinks() {
		return links;
	}



	public void setLinks(Elements links) {
		this.links = links;
	}



	public Elements getMedia() {
		return media;
	}



	public void setMedia(Elements media) {
		this.media = media;
	}



	public Elements getImports() {
		return imports;
	}



	public void setImports(Elements imports) {
		this.imports = imports;
	}


	public String getExtractedText(){

		String s = this.getDoc().text();
		Document d = Jsoup.parse(s);
		LOGGER.log(Level.INFO, "Crawler captured the text from URL " + this.getUrl() + ".");
		
		return(d.body().text());
	}

	public void saveExtractedText(String filename){

		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
			bw.write(this.getExtractedText());
			bw.close();
		}
		catch (IOException ioex) {
			LOGGER.log(Level.SEVERE, ioex.getMessage(), ioex);
		}
		LOGGER.log(Level.INFO, "Crawler saved the text from URL " + this.getUrl() + " in the file " + filename + ".");
	}

	public Set<String> getExtractedLinks(){

		Set<String> extractedLinks = new HashSet<String>();
		links = this.getDoc().select("a[href]");

		try{
			for (Element link : links){ 
				//System.out.println(link.attr("abs:href"));
				extractedLinks.add(link.attr("abs:href").toString());
			}
		}catch(NullPointerException npe){
			LOGGER.log(Level.SEVERE, npe.getMessage(), npe);
		}
		LOGGER.log(Level.INFO, "Crawler captured the links from URL " + this.getUrl() + ".");
		return extractedLinks;
	}


	public void saveExtractedLinks(String filename){
		String output = "";

		try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
			Set<String> sp = this.getExtractedLinks();
			for (String z: sp) 
				output += z + "\n";

			bw.write(output);
			bw.close();
		}
		catch (IOException ioex) {
			LOGGER.log(Level.SEVERE, ioex.getMessage(), ioex);
		}
		LOGGER.log(Level.INFO, "Crawler saved the links from URL " + this.getUrl() + " in the file " + filename + ".");

	}

	public void activate(String textOutput,String linkOutput){
		this.getExtractedText();
		this.getExtractedLinks();
		this.saveExtractedText(textOutput);
		this.saveExtractedLinks(linkOutput);
	}
	
	public void activate(){
		this.getExtractedText();
		this.getExtractedLinks();
		this.saveExtractedText("T- " + this.getUrl().replace(".","").replaceAll(":","").replace("/", ""));
		this.saveExtractedLinks("L-" + this.getUrl().replace(".","").replaceAll(":","").replace("/", ""));
	}
	
	public void finalize(){

		LOGGER.log(Level.INFO, "Crawler deactivated for URL ", this.getUrl() + ".");


	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PageCrawler p = new PageCrawler("https://en.wikipedia.org/wiki/Hamlet");

		Set<String> sp = p.getExtractedLinks();

		// for (String z: sp) System.out.println(z);
		System.out.println(p.getExtractedText());

		p.saveExtractedLinks("links.txt");
		p.saveExtractedText("text.txt");

	}
}
