package com.oscarshen09.orm.core;

/**
 * 负责java数据类型和数据库数据类型互相转换
 * @author ruiyao.shen
 *
 */
public interface TypeConvertor {
	
	/**
	 * 将数据库数据类型转化为java的数据类型
	 * @param columnType 数据库字段的数据类型
	 * @return java的数据类型
	 */
	public String database2Java(String columnType);
	
	/**
	 * 将java的数据类型转化为数据库数据类型
	 * @param javaDataType java数据类型
	 * @return 数据库的数据类型
	 */
	public String java2Database(String javaDataType);
}
