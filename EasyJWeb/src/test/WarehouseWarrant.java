package test;

public class WarehouseWarrant implements java.io.Serializable {
    public static final String TABLE_NAME = "warehouse_warrant";

    public static final String VIEW_NAME = "warehouse_warrant";

    public static final String PRIMARY_KEY = "warehouseWarrantId";

    private Long warehouseWarrantId;

    private String warehouseWarrantNo;

    private java.sql.Date inhouseDate;

    private java.util.ArrayList details;

    private String useState;

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        if ("details".equals(property))
            return "test.WarehouseWarrantDetail";
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] properties = {
            "details"
        };
        return properties;
    }

    public Long getWarehouseWarrantId() {
        return this.warehouseWarrantId;
    }

    public void setWarehouseWarrantId(Long warehouseWarrantId) {
        this.warehouseWarrantId = warehouseWarrantId;
    }

    public String getWarehouseWarrantNo() {
        return this.warehouseWarrantNo;
    }

    public void setWarehouseWarrantNo(String warehouseWarrantNo) {
        this.warehouseWarrantNo = warehouseWarrantNo;
    }

    public java.sql.Date getInhouseDate() {
        return this.inhouseDate;
    }

    public void setInhouseDate(java.sql.Date inhouseDate) {
        this.inhouseDate = inhouseDate;
    }

    public Object clone() {
        Object object = null;
        try {
            object = easyJ.common.BeanUtil.cloneObject(this);
        } catch (easyJ.common.EasyJException ee) {
            return null;
        }
        return object;
    }

    public boolean equals(Object o) {
        if (!(o instanceof WarehouseWarrant))
            return false;
        WarehouseWarrant bean = (WarehouseWarrant) o;
        if (warehouseWarrantId.equals(bean.getWarehouseWarrantId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return warehouseWarrantId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("warehouseWarrantId=" + warehouseWarrantId + ",");
        buffer.append("warehouseWarrantNo=" + warehouseWarrantNo + ",");
        buffer.append("inhouseDate=" + inhouseDate);
        buffer.append("]");
        return buffer.toString();
    }

    public java.util.ArrayList getDetails() {
        return details;
    }

    public void setDetails(java.util.ArrayList details) {
        this.details = details;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }
}
