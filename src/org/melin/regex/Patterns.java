package org.melin.regex;

public class Patterns {
	
	/*
	 * + 表示出現1次以上 * 表示出現0次以上.
	 * . 表示任何一個字元
	 * ? 表示
	 * [bcd] 第一個字元只能是b, c, d，並且只能有一個字元
	 * [a-zA-Z][0-9] 第一位為字母，第二位為數字
	 * [^abc]第一位不可a, b, c
	 * [a-c[e-g]] a到c或e到g
	 * [a-g&&[b-d]] b,c,d 交集
	 * \d 也可以表示輸入為[0-9]，使用時要注意特殊符號
	 * \D 表示[^0-9]
	 * \s 表示空白字元
	 * \S表示非空白字元
	 * \w 表示數字或英文字
	 * \W表示非數字或非英文字
	 * 
	 */
	public static final String linkName = "<a.+?>(.+?)<\\/a>";
	public static final String linkUrl = "<a.+?href=['|\"](.+?)['|\"].*>.+?<\\/a>";
	public static final String link = "<a.+?href=['|\"](.+?)['|\"].*>(.+?)<\\/a>";
	public static final String pl = "<p>(.+?)<\\/p>";
}
