/**
 * 
 */
package net.protheos.crawler.model;

/**
 * @author marcelo.gagliano
 *
 */
public interface Crawler {

	public String getUrl();
	
	public void setUrl(String url);
	
	public String crawl();
	
	
	
	
	
}
