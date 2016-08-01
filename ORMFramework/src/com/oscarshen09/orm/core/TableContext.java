package com.oscarshen09.orm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.oscarshen09.orm.bean.ColumnInfo;
import com.oscarshen09.orm.bean.TableInfo;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构
 * @author ruiyao.shen
 *
 */
public class TableContext {
	/**
	 * 表名为key，表信息对象为value
	 */
	public static Map<String,TableInfo> tables = new HashMap<>();
	
	/**
	 * 将po的class对象和表信息对象关联起来，便于重用
	 */
	public static Map<Class<?>,TableInfo> poClassTableMap = new HashMap<>();
	
	public TableContext() {}
	
	static{
		try {
			//初始化获得表的信息
			Connection conn = DBManager.getConn();
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while(tableRet.next()){
				String tableName = (String) tableRet.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new HashMap<String,ColumnInfo>(), new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
				while(set.next()){
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
				while(set2.next()){
					ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);//设为主键类型
					ti.getPriKeys().add(ci2);
				}
				
				/*************************************/
				if(ti.getPriKeys().size()>0){//取唯一主键
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
