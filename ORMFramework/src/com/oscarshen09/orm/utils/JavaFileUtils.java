package com.oscarshen09.orm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oscarshen09.orm.bean.ColumnInfo;
import com.oscarshen09.orm.bean.JavaFieldGetSet;
import com.oscarshen09.orm.bean.TableInfo;
import com.oscarshen09.orm.core.DBManager;
import com.oscarshen09.orm.core.MysqlTypeConvertor;
import com.oscarshen09.orm.core.TableContext;
import com.oscarshen09.orm.core.TypeConvertor;

/**
 * 封装了生成Java文件（源代码）常用的操作
 * 
 * @author ruiyao.shen
 *
 */
public class JavaFileUtils {

	/**
	 * 根据字段信息生成java属性信息。如：varchar username --> private String
	 * username；以及相应的set和get方法
	 * 
	 * @param column 字段信息
	 * @param convertor 类型转化情
	 * @return java属性和set/get方法源码
	 */
	public static JavaFieldGetSet createFieldGetSetSrc(ColumnInfo column, TypeConvertor convertor) {
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		String javaFieldType = convertor.database2Java(column.getDataType());
		
		//生成属性信息
		jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + column.getName() + ";\n");

		//生成get方法源码
		StringBuilder getSrc = new StringBuilder();
		getSrc.append("\tpublic " + javaFieldType + " get" + 
				StringUtils.firstChar2UpperCase(column.getName()) + "(){\n");
		getSrc.append("\t\treturn " + column.getName() + ";\n");
		getSrc.append("\t}\n");
		jfgs.setGetInfo(getSrc.toString());
		
		//生成set方法源码
		StringBuilder setSrc = new StringBuilder();
		setSrc.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
		setSrc.append(javaFieldType + " " + column.getName() + "){\n");
		setSrc.append("\t\tthis." + column.getName() + "=" + column.getName() + ";\n");
		setSrc.append("\t}\n");
		jfgs.setSetInfo(setSrc.toString());
		
		return jfgs;
	}
	
	public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor){
		Map<String, ColumnInfo> columns = tableInfo.getColumns();
		List<JavaFieldGetSet> javaFields = new ArrayList<>();
		
		for(ColumnInfo c:columns.values()){
			javaFields.add(createFieldGetSetSrc(c, convertor));
		}
		
		StringBuilder src = new StringBuilder();

		//生成package语句
		src.append("package " + DBManager.getConf().getPoPackage() + ";\n\n");
		//生成import语句
		src.append("import java.sql.*;\n");
		src.append("import java.util.*;\n\n");
		//生成类声明语句
		src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname())+" {\n\n");
		//生成属性列表
		for(JavaFieldGetSet f : javaFields){
			src.append(f.getFieldInfo());
		}
		src.append("\n");
		//生成get、set方法列表
		for(JavaFieldGetSet f : javaFields){
			src.append(f.getSetInfo());
			src.append("\n");
			src.append(f.getGetInfo());
			src.append("\n");
		}
		
		//生成类结束
		src.append("}\n");
		System.out.println(src.toString());
		return src.toString();
	}
	public static void main(String[] args) {
//		ColumnInfo ci = new ColumnInfo("username", "varchar", 0);
//		JavaFieldGetSet jfgs = createFieldGetSetSrc(ci, new MysqlTypeConvertor());
//		System.out.println(jfgs);
		Map<String,TableInfo> map = TableContext.tables;
		TableInfo t = map.get("dept");
		createJavaSrc(t, new MysqlTypeConvertor());
	}
}
