package org.melin.mcb;

import org.jsoup.select.Elements;

import okhttp3.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
    private OkHttpClient okHttpClient;
    private final Map<String, List<Cookie>> cookieStore; // 保存 Cookie
    private final CookieJar cookieJar;

    public Reader() throws IOException {
        cookieStore = new HashMap<>();
        cookieJar = new CookieJar() {
            /* 保存每次伺服器端回傳的 Cookie */
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                List<Cookie> cookies = cookieStore.getOrDefault(
                        httpUrl.host(),
                        new ArrayList<>()
                );
                cookies.addAll(list);
                cookieStore.put(httpUrl.host(), cookies);
            }

            /* 每次發送帶上儲存的 Cookie */
            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                return cookieStore.getOrDefault(
                        httpUrl.host(),
                        new ArrayList<>()
                );
            }
        };
        okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();
        /* 獲得網站的初始 Cookie */
        Request request = new Request.Builder().get().url("https://8book.com").build();
        Response response = okHttpClient.newCall(request).execute();

    }
    private List<Map<String, String>> parseArticle(String body) {
        List<Map<String, String>> result = new ArrayList<>();

        //(?s) 表示忽略換行字元;
        String patternStr = "(?s)<ul\\sp=\"1\"\\sclass=\"row\\sp-2\">(.+)</ul>";
        //splitHtml(tmpStr, patternStr);


        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(body);
        // 如果找到了
        int i = 0;
        StringBuffer strBuffer = new StringBuffer();
        while(matcher.find())
        {
            System.out.println(++i);
//            String element0 = matcher.group(0);
//            System.out.println(element0);
//            System.out.println("===================================");
            strBuffer.append(matcher.group(1));

//            element = element.replaceAll("(^\\s+)", "");
//            String url = getUrl(element);
//            String name = getName(element);
//            System.out.println(url);
//            System.out.println(name);
//            System.out.println(protocol + ":" + url);
        }
        String cc = strBuffer.toString();
        cc = cc.replaceAll("\r\n","");
        //System.out.println(cc);
        String linkPatternStr = "(?s)<a(.+)\\shref='(.+)'><span\\sclass=\"mdi\\smdi-rhombus-outline\\sepisode_li_dot\"></span>(.+)</a>";
        linkPatternStr = "<a.+href=['|\\\"](.+)['|\\\"]>(.+)</a>";
        linkPatternStr = "<a.+?>(.+?)</a>";
        Pattern linkPattern = Pattern.compile(linkPatternStr);
        Matcher linkMatcher = pattern.matcher(cc);
        while(linkMatcher.find())
        {
            System.out.println(linkMatcher.group(1));
            System.out.println(linkMatcher.group(2));


//            element = element.replaceAll("(^\\s+)", "");
//            String url = getUrl(element);
//            String name = getName(element);
//            System.out.println(url);
//            System.out.println(name);
//            System.out.println(protocol + ":" + url);
        }
            return result;
    }
    public List<Article> getList() throws ParseException, IOException {
        String uri = "/novelbooks/133271/";
        Request request = new Request.Builder()
                .url(Config.WEB_URL + uri)
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String body = response.body().string();
        //System.out.println(body);
        parseArticle(body);
        return null;
    }
    public static void main(String[] args) throws Exception {
        Reader reader = new Reader();
        reader.getList();


    }

}
