package org.melin.webspider;

public class SpiderRunner {
	public static void main(String args []) throws Exception
	{
		String url = "https://czbooks.net/n/cik3jp";
		CzbooksSpider a = new CzbooksSpider();
		a.spider(url);
	}
}
