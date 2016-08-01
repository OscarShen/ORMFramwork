package com.oscarshen09.orm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.oscarshen09.orm.bean.Configuration;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 * @author ruiyao.shen
 *
 */
public class DBManager {
	private static Configuration conf;

	static { // 静态代码块
		Properties pros = new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		conf = new Configuration();
		conf.setDriver(pros.getProperty("driver"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setPwd(pros.getProperty("pwd"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUser(pros.getProperty("user"));
		conf.setUsingDB(pros.getProperty("usingDB"));
	}

	public static Connection getConn() {
		try {
			Class.forName(conf.getDriver());
			return DriverManager.getConnection(conf.getUrl(), 
					conf.getUser(), conf.getPwd());//后期增加连接池处理
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Configuration getConf(){
		return conf;
	}
}
