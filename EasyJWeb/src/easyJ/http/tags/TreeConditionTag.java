package easyJ.http.tags;

/**
 * 这个类用来为TreeTag提供条件用的，TreeTag在生成树的时候用到的条件
 * 
 * @author liufeng
 */
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class TreeConditionTag extends TagSupport {
    private String property = "";

    private String value = "";

    public void setValue(String value) {
        this.value = value;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int doEndTag() throws JspTagException {
        Tag t = findAncestorWithClass(this, TreeTag.class);
        TreeTag parent = (TreeTag) t;
        Condition condition = new Condition();
        condition.setProperty(property);
        condition.setValue(value);
        parent.addCondisiton(condition);
        return EVAL_PAGE;
    }

    public static class Condition {
        String property;

        String value;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
