package org.melin.webspider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class Shubao90Spider {
    private String mainUrl;
    private String protocol;
    
    public void spider(String url) throws Exception
    {
        this.mainUrl = url;
        this.protocol = splitUrl(url, URL_PROTOCOL);
        URL realUrl = new URL(url);
        URLConnection connection = realUrl.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        


        connection.setDoInput(true);
        connection.setUseCaches(false);
        String result = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null)
            {
                // 遍历抓取到的每一行并将其存储到result里面
                
                result += line;
                result += "\r\n";
            }
        } catch (Exception e)
        {
            //logger.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            } catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        //System.out.println(result);
        String patternStr = "(?s)<ul class = \"nav chapter-list(.+?).</ul>";
        String tmpStr = getHtml(result, patternStr);
        patternStr = "(?i)<li><a (.*).</a></li>";
        //(?i)<a([^>]+)>(.+?)</a>
        patternStr = "(?i)<li><a([^>]+)>(?s)(.+?).</li>";
        patternStr = "(?s)(<li>)(.+?)(<li>)";
        patternStr = "(?s)<li>(.+?)<li>";
        splitHtml(tmpStr, patternStr);
        
        System.out.println(tmpStr);
        //logger.info("END");
    }
    public static final int URL_PROTOCOL = 1;
    public static final int URL_URL = 2;
    public String splitUrl(String url , int type)
    {
        String patternStr = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()) 
        {
            return matcher.group(type);
        }
        return null;
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
            System.out.println(url);
            System.out.println(name);
            System.out.println(protocol + ":" + url);
        }
    }
    
    public String getUrl(String htmlTagStr)
    {
        String patternStr = "href=\"(.*?)\"";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(htmlTagStr);
        if(matcher.find()) 
        {
            String element = matcher.group(1);
            return element;
            
        }
        return null;
    }
    
    public String getName(String htmlTagStr)
    {
        String patternStr = "<a.+?>(.+?)</a>";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(htmlTagStr);
        if(matcher.find()) 
        {
            String element = matcher.group(1);
            return element;
            
        }
        return null;
    }
    
    public String getHtml(String str, String patternStr)
    {

        String result = "";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(str);
                // 如果找到了
                if (matcher.find())
                {
//                  //System.out.println("-------------------------------------");
//                  // 打印出结果
//                  = matcher.group(0);
//                  System.out.println(r);
//                  result = r;
//                  System.out.println("-------------------------------------");
//                  r = matcher.group(1);
                    //System.out.println(matcher.group());
                    result = matcher.group();
                }
                else
                {
                    System.out.println("no matches string ");
                    
                }
                return result;
    }
    
    public Map getContent(String url)
    {
        Document doc = Jsoup.parse("UTF-8", "url");
        String name = doc.select("#name").text();
        
        return null;
    }    
}
