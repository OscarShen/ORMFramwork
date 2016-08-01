package com.oscarshen09.orm.utils;

/**
 * 封装了String常用的操作
 * 
 * @author ruiyao.shen
 *
 */
public class StringUtils {

	/**
	 * 将目标字符串首字母变为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstChar2UpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
