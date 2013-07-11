package util;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import model.Node;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisUtil {
	private static SqlMapClient sqlMapClient = null;

	public static SqlMapClient getClient() {
		if (sqlMapClient != null) {
			return sqlMapClient;
		}
		try {
			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlMapClient;

	}
	public static List<Node> getNode(String date) throws Exception{
		ArrayList<Node> nodes = (ArrayList<Node>) getClient().queryForList("FetchInfo",date);
		System.out.println(nodes.size());
		return nodes;
	}
}
