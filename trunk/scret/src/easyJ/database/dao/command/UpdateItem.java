package easyJ.database.dao.command;

public class UpdateItem {
    private String property;

    private Object value;

    public UpdateItem() {}

    public UpdateItem(String property, Object value) {
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
