package servlet;

import java.io.DataInputStream;
import java.io.FileInputStream;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class Utils {
	private static SqlMapClient client;

	public static SqlMapClient getSqlMapClient(DataInputStream input)
			throws Exception {

		if (client == null) {

			client = SqlMapClientBuilder.buildSqlMapClient(input);

		}
		return client;
	}

	public static SqlMapClient getSqlMapClient() throws Exception {

		if (client == null) {
			client = SqlMapClientBuilder.buildSqlMapClient(new FileInputStream(
					"SqlMapConfig.xml"));
		}
		return client;

	}
}
