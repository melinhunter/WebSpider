package org.melin.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlReg {
	
	public static void main(String args[]) {
		test1();
		test2();
	}

	static String html2 = "<li>foo <a href='link1'>bar</a></li> \r\n baz <a href='link2'>qux</a> foo";
	
	public static void test1() {
		String nameofCurrMethod = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 		
		System.out.println(nameofCurrMethod);
		String str = "http://www.jb51.net";
		String pattern = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		System.out.println(m.matches());
		System.out.println(nameofCurrMethod + " END");
	}
	
	
	
	public static void test2() {
		String nameofCurrMethod = new Throwable() 
                .getStackTrace()[0] 
                .getMethodName(); 
		System.out.println(nameofCurrMethod);
		
		Pattern p = Pattern.compile("<a href='(.*?)'>");
		Matcher m = p.matcher(html2);
		while(m.find()) {
		   System.out.println(m.group(0));
		   System.out.println(m.group(1));
		}
		System.out.println(nameofCurrMethod + " END");
	}

}