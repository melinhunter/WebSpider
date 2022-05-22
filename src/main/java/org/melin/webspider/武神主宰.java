package org.melin.webspider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class 武神主宰 extends CzbooksSpider{
    private String mainUrl;
    private String protocol;
    private String webUrl;
    public static final int URL_PROTOCOL = 1;
    public static final int URL_URL = 2;    
    @Override
    public void spider(String mainPageUrl) throws Exception{
        this.mainUrl = mainPageUrl;
        this.protocol = splitUrl(mainPageUrl, URL_PROTOCOL);
        this.webUrl = mainPageUrl.replace(this.protocol+"://", "");
        this.webUrl = this.webUrl.substring(0, this.webUrl.indexOf("/"));
        URL realUrl = new URL(mainPageUrl);
        URLConnection connection = realUrl.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        
        connection.setDoInput(true);
        connection.setUseCaches(false);
        String result = "";
        int max = 10;
        int now = 0;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))){
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null)
            {
                // 遍历抓取到的每一行并将其存储到result里面
                //System.out.println(line);
                result += line;
                result += "\r\n";
                now++;
                //if(now > max) break;
            }
        } catch (Exception e)
        {
            //logger.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }   
        //System.out.println(result);
        String patternStr = "(?s)<div class = \"nav chapter-list(.+?).</ul>";
        patternStr = "(?s)<div class=\"box_con\">[\\s*|\t|\r|\n]*<div id=\"list(.+?).</div>[\\\\s*|\\t|\\r|\\n]*</div>";
        
        String tmpStr = getHtml(result, patternStr);
        
        
        
//        patternStr = "(?i)<li><a (.*).</a></li>";
//        //(?i)<a([^>]+)>(.+?)</a>
//        patternStr = "(?i)<li><a([^>]+)>(?s)(.+?).</li>";
//        patternStr = "(?s)(<li>)(.+?)(<li>)";
//        patternStr = "(?s)<li>(.+?)<li>";
//        splitHtml(tmpStr, patternStr);
        patternStr = "(?i)<dd><a([^>]+)>(?s)(.+?).</dd>";
        splitHtml(tmpStr, patternStr);
//        System.out.println(tmpStr.substring(0, 1000));        
    }
    
    public void splitHtml(String str, String patternStr)
    {
        String t [] = str.split(patternStr);
        String result = "";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        // 如果找到了

        while(matcher.find()) 
        {
            String element = matcher.group();
            element = "  " + element.trim();
            element = element.replaceAll("(^\\s+)", "");
            String url = getUrl(element);
            String name = getName(element);
            if(url.indexOf("/") ==0)
            {
                url = this.webUrl + url;
            }
            System.out.println(url);
            System.out.println(name);
            String contentUrl = protocol + "://" + url;
            String content = this.getContents(contentUrl);
            System.out.println(content);
            
        }
    }
    
    public String getContents(String url)
    {
        String htmlPage = "";
        try {
            htmlPage = Jsoup.connect(url).get().toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(htmlPage);
        //System.out.println(doc);
        String body = doc.select("div[id=content]").html();//.text();
        body = body.replaceAll("\n", "");
        body = body.replaceAll("<br>", "\n");
        body = body.replaceAll("&nbsp;", " ");
        
        return body;
    }    

    public static void main(String[] args) {
        武神主宰 t = new 武神主宰();
        String url = "https://www.biquge.com.cn/book/31281/287805.html";
        System.out.println(t.getContents(url));
    }
}
