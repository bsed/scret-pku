package test;

import java.util.List;

import model.Node;
import util.IbatisUtil;

public class TestFetch {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TestFetch tf = new TestFetch();
		List<Node> nodes = IbatisUtil.getNode("2013-07-09");
		System.out.println(nodes.size());
	}

}
