package org.melin.webspider;

/**
 * 武神主宰
 * @author 9204909
 *
 */
public class WuShenZuZie {
    public static void main(String[] args) throws Exception {
        String url = "https://www.biquge.com.cn/book/31281/";
        BookSpider a = new 武神主宰();
        a.spider(url);
    }
}
