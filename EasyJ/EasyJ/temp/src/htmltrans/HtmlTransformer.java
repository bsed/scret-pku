package htmltrans;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlTransformer {
	
	private static final String TEST_STRING = "<span id=\"L1\">" +
			"<span id=\"L2\">This is a level 2 span</span></span>";
	
	private static final String GRAPH_STRING_ID = "__Graph_String__";
	private static final String LINE_BREAK = "__#Line_Break#__";
	
	public static final String SWIMLANE_SEP = "__#Swimlane_Separator#__";
	
	protected Node getElementById(Parser parser, String id) {
		parser.reset();
		NodeFilter filter = new CssSelectorNodeFilter("#" + id);
		try {
			NodeList list = parser.extractAllNodesThatMatch(filter);
			if (list.size() > 0) {
				return list.elementAt(0);
			} 
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public HtmlTransformer() {
		
	}
	
	public String getGraphSourceString(String html) {
		try {
			Parser parser = new Parser(html);
			Node node = getElementById(parser, GRAPH_STRING_ID);
			return node != null ? node.toPlainTextString().replaceAll(LINE_BREAK, "\n") : "";
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void testGetElementById() {
		// Should output:
		//      This is a level 2 span
		try {
			Parser parser = new Parser(TEST_STRING);
			Node node = getElementById(parser, "L2");
			if (node != null) {
				System.out.println(node.toPlainTextString());
			} else {
				System.out.println("null");
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HtmlTransformer ht = new HtmlTransformer();
		ht.testGetElementById();
	}
}
