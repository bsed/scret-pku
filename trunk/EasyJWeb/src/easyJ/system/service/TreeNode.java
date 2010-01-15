package easyJ.system.service;

public class TreeNode {
    private boolean isLeaf;

    private int level;

    private TreeNode parent;

    private Object value;

    private Long id;

    private Long parentId;

    private String displayValue;

    public static final String LINE = "line.gif";

    public static final String SPACE = "spacer.gif";

    public static final String LEAF = "redarrow.gif";

    public static final String LASTLEAF = "normal_last.gif";

    public static final String ZIP = "zip.gif";

    public static final String ZIPFIRST = "zip_first.gif";

    public static final String ZIPLAST = "zip_last.gif";

    public static final String ZIPROOT = "zip_root.gif";

    public static final String UNZIPROOT = "unzip_root.gif";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}
