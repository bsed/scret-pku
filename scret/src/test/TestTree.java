package test;

import easyJ.system.service.*;
import java.util.List;
import java.util.Hashtable;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.Const;

public class TestTree {
    public TestTree() {}

    public static void main(String[] args) {
        try {
            TreeService ts = TreeServiceFactory.getTreeService();
            Tree tree = ts.getTree("test.Department");
            ts.setIniSpreadLevel(Const.MAX_INT_VALUE);
            tree.createTree();
            StringBuffer buffer = new StringBuffer();
            ts.generateWholeTree(tree, buffer, Const.TREE_SELECT_SPREAD, "");
            System.out.println(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
