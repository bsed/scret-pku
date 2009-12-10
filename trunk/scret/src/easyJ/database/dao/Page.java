package easyJ.database.dao;

import java.util.ArrayList;

public class Page implements java.io.Serializable {
    public static final int pageSize = 20;

    private int currentPageNo;

    private ArrayList pageData;

    private Long totalPage;

    private Long totalNum;

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public ArrayList getPageData() {
        return pageData;
    }

    public void setPageData(ArrayList pageData) {
        this.pageData = pageData;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
        if (totalNum.intValue() % pageSize == 0) {
            totalPage = new Long(totalNum.intValue() / pageSize);
        } else {
            totalPage = new Long(totalNum.intValue() / pageSize + 1);
        }
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public String toString() {
        String page = "pageSize is:" + pageSize + " currentPageNo is:"
                + currentPageNo + " totalPage is:" + totalPage
                + " totalNum is:" + totalNum;
        return page;
    }
}
