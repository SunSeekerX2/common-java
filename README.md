# JAVA

## 1.Mysql数据库操作封装类

```java
package cn.yoouu.util;

/**
 * 数据库操作的工具类
 * @author YOU
 *2018年6月21日19:14:57
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.rowset.CachedRowSetImpl;

public class DBHelp {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int ret = 0;
	CachedRowSetImpl rowSet;

	/**
	 * 通用的基于MySQL数据库的公共查询功能
	 * @param sql 需要进行查询的SQL命令
	 * @param values nums sql查询命中的参数
	 * @return 返回查询的结果集 todo 调用统一的释放资源的close方法
	 */
	public CachedRowSetImpl getInfoBySql(String sql, String[] values,int[] nums) {
		try {
			// 调用公共的加载驱动和创建链接的方法
			getConnection();
			//实例化缓存集合对象
			rowSet = new CachedRowSetImpl();
			// 创建预编译对象，并将SQL命令提交到预编译对象
			pstmt = conn.prepareStatement(sql);
			// 判断参数数组是否有内容，从而推断出SQL命令中是否带有?
			if (values != null && values[0] != null) {
				// 循环数组参数，将每个值放入预编译对象中
				for (int i = 0; i < values.length; i++) {
					// 将数组中的值放入预编译对象中，
					// i+1 表示预编译对象是从1开始，而循环或数组是从0开
					pstmt.setString(i + 1, values[i]);
				}
			}else if (nums != null) {
				// 循环数组参数，将每个值放入预编译对象中
				for (int i = 0; i < nums.length; i++) {
					// 将数组中的值放入预编译对象中，
					// i+1 表示预编译对象是从1开始，而循环或数组是从0开
					pstmt.setInt(i+1, nums[i]);
				}
			}
			// 获得数据库查询的结果集
			rs = pstmt.executeQuery();
			//将结果集交给离线结果集
			rowSet.populate(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//释放数据库资源
			closeAll();
		}
		//返回查询结果集
		return rowSet;
	}

	/**
	 * 通用的基于MySQL数据库的公共新增、修改、删除功能
	 * 
	 * @param sql
	 *            需要进行查询的SQL命令
	 * @param values
	 *            sql查询命中的参数
	 * @return 返回数据库操作影响的行数
	 */
	public int modifyDB(String sql, String[] values) {
		try {
			// 调用公共的加载驱动和创建链接的方法
			getConnection();
			// 创建预编译对象，并将SQL命令提交到预编译对象
			pstmt = conn.prepareStatement(sql);
			// 判断参数数组是否有内容，从而推断出SQL命令中是否带有?
			if (values != null && values[0] != null) {
				// 循环数组参数，将每个值放入预编译对象中
				for (int i = 0; i < values.length; i++) {
					// 将数组中的值放入预编译对象中，
					// i+1 表示预编译对象是从1开始，而循环或数组是从0开
					pstmt.setString(i + 1, values[i]);
				}
			}
			// 获得数据库增删改操作的返回值
			ret = pstmt.executeUpdate();
			// 关闭数据库资源
			closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回数据库操作结果
		return ret;
	}

	/**
	 * 加载数据库驱动，并创建数据库连接
	 * 
	 * @return 返回连接对象
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/yoouu.cn?useSSL=false", "root", "12345678900");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 释放数据库资源的功能
	 */
	public void closeAll() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pstmt != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

```

