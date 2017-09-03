package net.protheos.crawler.model;

import net.protheos.crawler.controller.ThreadCrawler;

public class CrawlerSwarm {
	ThreadCrawler threadCrawler;
	public CrawlerSwarm(String initialUrl){
		threadCrawler = new ThreadCrawler(initialUrl);
		
	}
	
	//public void activateSwarm(){}
	
	

}
