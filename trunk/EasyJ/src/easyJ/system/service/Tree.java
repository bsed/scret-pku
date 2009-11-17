package easyJ.system.service;

import java.util.List;
import java.util.Hashtable;
import easyJ.common.EasyJException;

public interface Tree {
    public void createTree() throws EasyJException;

    public void createTree(List valueList) throws EasyJException;

    public Hashtable getHashData();

    public List getListData();

    public Object getRoot();

    public String getIdProperty();

    public String getParentIdProperty();

    public String getDisplayProperty();

    public String getFunctionProperty();

    public int getSize();
}
