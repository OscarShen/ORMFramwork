package com.oscarshen09.orm.core;

import java.util.List;

/**
 * 负责查询（对外提供服务的核心类）
 * 
 * @author ruiyao.shen
 *
 */
public interface Query {
	/**
	 * 直接执行一个DML语句
	 * @param sql sql语句
	 * @param params 参数
	 * @return 执行后影响记录的行数
	 */
	public int executeDML(String sql, Object[] params);
	
	/**
	 * 将一个对象存储到数据库中
	 * @param obj 要存储的对象
	 */
	public void insert(Object obj);
	
	/**
	 * 删除Class类对应表中的记录（指定ID）
	 * @param clazz 与表对应类的Class对象
	 * @param id 主键的值
	 * @return 影响的行数
	 */
	public int delete(Class<?> clazz, int id);
	
	/**
	 * 删除对象再数据库中对应的记录（对象所在的类对应到表，对象的主键的值对应到记录）
	 * @param object 所要更新的对象
	 * @return 影响的行数
	 */
	public int delete(Object object);
	
	/**
	 * 更新对象对应的记录，并且只更新指定字段的值
	 * @param obj 所要更新的对象
	 * @param fieldNames 更新的属性列表
	 * @return 影响的行数
	 */
	public int update(Object obj,String[] fieldNames);
	
	/**
	 * 查询返回多行记录，并将每行记录封装到Class指定的类对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 */
	public List<?> queryRows(String sql,Class<?> clazz,Object[] params);
	
	/**
	 * 查询返回一行记录，并将该行记录封装到Class指定的类对象中
	 * @param sql 查询语句
	 * @param clazz 封装数据的javabean类的Class对象
	 * @param params sql的参数
	 * @return 查询到的结果
	 */
	public Object queryUniqueRow(String sql,Class<?> clazz,Object[] params);
	
	/**
	 * 查询返回一个值（一行一列），并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的结果
	 */
	public Object queryValue(String sql,Object[] params);
	
	/**
	 * 查询返回一个数字（一行一列），并将该值返回
	 * @param sql 查询语句
	 * @param params sql的参数
	 * @return 查询到的数字
	 */
	public Number queryNumber(String sql,Object[] params);
}
