/**
 * 
 */
package net.protheos.crawler;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author oo7021
 *
 */
public class ThreadCrawler extends Thread {
	
	/**
	 * 
	 */ private PageCrawler crawler;


	/**
	 * @param target
	 */
	public ThreadCrawler(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @param name
	 */
	public ThreadCrawler(String url) {
		crawler =  new PageCrawler(url);
			}

	public ThreadCrawler(PageCrawler crawler) {
		this.crawler = crawler;
	}

	  public PageCrawler getCrawler() {
		return crawler;
	}

	public void setCrawler(PageCrawler crawler) {
		this.crawler = crawler;
	}

	public void run() {
		this.crawler.activate();
	    }

	    public static void main(String args[]) {
	        (new ThreadCrawler("https://en.wikipedia.org/wiki/Hamlet")).start();
	    }

}
