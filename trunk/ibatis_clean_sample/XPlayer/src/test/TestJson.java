package test;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import model.Node;

public class TestJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node node = new Node();
		node.setId(0);
		node.setOs("ipad");
		node.setPv(100);
		node.setVv(1000);
		node.setDate("2013-07-09");
		JSONObject ja = JSONObject.fromObject(node);
		System.out.println(ja.toString());
	}

}
