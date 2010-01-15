package easyJ.database.dao;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import easyJ.common.BeanUtil;
import java.sql.SQLException;
import easyJ.common.EasyJException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.lang.reflect.Field;
import easyJ.database.ColumnPropertyMapping;
import java.sql.Statement;
import easyJ.database.TypeMapping;
import easyJ.logging.EasyJLog;

public class ExecuteSQL {
	public ExecuteSQL() {
	}

	public static void executeUpdate(String updateSQL,
			ArrayList paramValueList, Connection conn) throws EasyJException {
		executeUpdate(updateSQL, paramValueList, null, conn);
	}

	public static void executeUpdate(String updateSQL,
			ArrayList paramValueList, ArrayList paramTypeList, Connection conn)
			throws EasyJException {
		PreparedStatement psst = null;
		try {
			psst = conn.prepareStatement(updateSQL);
			if (paramTypeList == null || paramTypeList.size() == 0) {
				setElement(psst, paramValueList);
			} else {
				setElement(psst, paramValueList, paramTypeList);
			}
			psst.execute();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.executeUpdate(String updateSQL,ArrayList paramValueList,Connection conn)";
			String logMessage = "SQL语句执行时错误，请检查SQL语句";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} catch (Exception e) {
			if (e instanceof EasyJException) {
				throw (EasyJException) e;
			}
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.executeUpdate(String updateSQL,ArrayList paramValueList,Connection conn)";
			String logMessage = "运行时异常";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} finally {
			try {
				if (psst != null) {
					psst.close();
					psst = null;
				}
			} catch (Exception e) {

			}

		}
	}

	public static Object executeMySQLInsert(String insertSQL,
			ArrayList paramValueList, ArrayList paramTypeList, Connection conn)
			throws EasyJException {
		PreparedStatement psst = null;
		try {
			psst = conn.prepareStatement(insertSQL);
			ExecuteSQL.setElement(psst, paramValueList, paramTypeList);
			psst.execute();
			int autoIncKeyFromApi = -1;
			ResultSet rs = psst.getGeneratedKeys();
			if (rs.next()) {
				autoIncKeyFromApi = rs.getInt(1);
			} else {
				// throw an exception from here
			}
			Long no = new Long(autoIncKeyFromApi);
			return no;
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.create(Object o,Connection conn)";
			String logMessage = "SQL语句执行时错误，请检查SQL语句";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} catch (Exception e) {
			if (e instanceof EasyJException) {
				throw (EasyJException) e;
			}
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.create(Object o,Connection conn)";
			String logMessage = "运行时异常";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		}

		finally {
			try {
				if (psst != null) {
					psst.close();
					psst = null;
				}
			} catch (Exception e) {

			}

		}

	}

	/**
	 * @param insertSQL
	 *            String 要执行的插入语句
	 * @param paramValueList
	 *            ArrayList
	 * @param paramTypeList
	 *            ArrayList
	 * @param conn
	 *            Connection
	 * @throws EasyJException
	 * @return Object 自增主键
	 */
	public static Object executeInsert(String insertSQL,
			ArrayList paramValueList, ArrayList paramTypeList, Connection conn)
			throws EasyJException {
		PreparedStatement psst = null;
		try {
			psst = conn.prepareStatement(insertSQL);
			ExecuteSQL.setElement(psst, paramValueList, paramTypeList);
			psst.execute();
			psst.getMoreResults();
			ResultSet rs = psst.getResultSet();
			rs.next();
			Long no = new Long(rs.getInt(1));
			return no;
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.create(Object o,Connection conn)";
			String logMessage = "SQL语句执行时错误，请检查SQL语句";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} catch (Exception e) {
			if (e instanceof EasyJException) {
				throw (EasyJException) e;
			}
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.create(Object o,Connection conn)";
			String logMessage = "运行时异常";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		}

		finally {
			try {
				if (psst != null) {
					psst.close();
					psst = null;
				}
			} catch (Exception e) {

			}

		}

	}

	public static ResultSet executeQueryResultSet(String sql,
			ArrayList paramValueList, Connection conn) throws EasyJException {
		PreparedStatement psst = null;
		ResultSet rs = null;
		try {
			// EasyJLog.debug ("executeQueryResultSet:"+sql);
			psst = conn.prepareStatement(sql);
			setElement(psst, paramValueList);
			rs = psst.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.ExecuteSQL.executeQueryResultSet(String sql,ArrayList paramValueList,Connection conn)";
			String logMessage = "SQL语句执行出错，请检查SQL语句";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		}
	}

	public static ArrayList executeQuery(String sql, ArrayList paramValueList,
			Class c, Connection conn) throws EasyJException {
		// System.out.println(sql);
		ResultSet rs = executeQueryResultSet(sql, paramValueList, conn);
		return rsToArrayList(rs, c);
	}

	public static ArrayList rsToArrayList(ResultSet rs, Class oclass)
			throws EasyJException {
		ArrayList result = new ArrayList();
		try {
			while (rs.next()) {
				Object or = oclass.newInstance();
				Field[] fields = oclass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i].getName();
					if(fieldName.equals("fromDictionary")){
						System.out.println("FieldName = " +fieldName);
					}
					System.out.println("===="+fieldName);
					if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())
							&& BeanUtil.getSubClass(oclass, fieldName) == null) {
						if (rs.getObject(ColumnPropertyMapping.getColumnName(fieldName)) == null) {
							BeanUtil.setFieldValue(or, fieldName, null);
						} else {
							if (fields[i].getType().equals(java.sql.Date.class)) {
								BeanUtil.setFieldValue(or, 
										fieldName, 
										rs.getDate((ColumnPropertyMapping.getColumnName(fieldName))));
							} else if (fields[i].getType().equals(Long.class)) {
								BeanUtil.setFieldValue(or, fieldName, 
										new Long(rs.getLong((ColumnPropertyMapping.getColumnName(fieldName)))));
							} else if (fields[i].getType().equals(Short.class)) {
								BeanUtil.setFieldValue(
												or,
												fieldName,
												new Short(
														rs
																.getShort((ColumnPropertyMapping
																		.getColumnName(fieldName)))));
							} else if (fields[i].getType()
									.equals(Integer.class)) {
								BeanUtil
										.setFieldValue(
												or,
												fieldName,
												new Integer(
														rs
																.getInt((ColumnPropertyMapping
																		.getColumnName(fieldName)))));
							} else if (fields[i].getType().equals(
									java.math.BigDecimal.class)) {
								BeanUtil.setFieldValue(or, fieldName, rs
										.getBigDecimal((ColumnPropertyMapping
												.getColumnName(fieldName))));
							} else if (fields[i].getType().equals(
									java.lang.Byte.class)) {
								BeanUtil.setFieldValue(or, fieldName, new Byte(
										rs.getByte(ColumnPropertyMapping
												.getColumnName(fieldName))));
							} else if (fields[i].getType().equals(
									java.lang.Boolean.class)) {
								BeanUtil
										.setFieldValue(
												or,
												fieldName,
												new Boolean(
														rs
																.getBoolean(ColumnPropertyMapping
																		.getColumnName(fieldName))));
							} else {
								BeanUtil.setFieldValue(or, fieldName, rs
										.getObject((ColumnPropertyMapping
												.getColumnName(fieldName))));
							}

						}
					}
				}
				result.add(or);
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.rsToArrayList(ResultSet rs, Class oclass)";
			String logMessage = "数据从ResultSet中读取时出错，检查数据库字段";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} catch (InstantiationException e) {
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.rsToArrayList(ResultSet rs, Class oclass)";
			String logMessage = "类实例化时出错，请检查类：" + oclass + " 是否有不带参数的构造方法";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.rsToArrayList(ResultSet rs, Class oclass)";
			String logMessage = "类实例化时出错，请检查类：" + oclass
					+ "的不带参数的构造方法是否是public的";
			EasyJException ee = new EasyJException(e, location, logMessage,
					clientMessage);
			throw ee;
		} finally {
			try {
				if (rs != null) {
					Statement st = rs.getStatement();
					rs.close();
					rs = null;
					st.close();
					st = null;
				}

			} catch (Exception e) {

			}
		}
		return result;
	}

	public static PreparedStatement setElement(PreparedStatement psst,
			ArrayList paramValueList) throws EasyJException {
		if (paramValueList == null) {
			return psst;
		}
		try {
			for (int i = 0; i < paramValueList.size(); i++) {
				psst.setObject(i + 1, paramValueList.get(i));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.setElement(PreparedStatement psst, ArrayList paramValueList)";
			String logMessage = "对PreparedStatement进行设置的时候出现错误，检查数据的类型是否和数据库的类型匹配";
			EasyJException ee = new EasyJException(sqle, location, logMessage,
					clientMessage);
			throw ee;
		}
		return psst;
	}

	public static PreparedStatement setElement(PreparedStatement psst,
			ArrayList paramValueList, ArrayList paramTypeList)
			throws EasyJException {
		if (paramValueList == null) {
			return psst;
		}
		try {
			for (int i = 0; i < paramValueList.size(); i++) {
				if (paramValueList.get(i) == null) {
					psst.setNull(i + 1, TypeMapping
							.getJavaSQLType((Class) paramTypeList.get(i)));
				} else {
					psst.setObject(i + 1, paramValueList.get(i));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			String clientMessage = "服务器忙";
			String location = "easyJ.database.dao.SQLServerDAOImpl.setElement(PreparedStatement psst, ArrayList paramValueList)";
			String logMessage = "对PreparedStatement进行设置的时候出现错误，检查数据的类型是否和数据库的类型匹配";
			EasyJException ee = new EasyJException(sqle, location, logMessage,
					clientMessage);
			throw ee;
		}
		return psst;
	}

}
