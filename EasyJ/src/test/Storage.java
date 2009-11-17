package test;

public class Storage implements java.io.Serializable {
    public static final String TABLE_NAME = "storage";

    public static final String VIEW_NAME = "v_storage";

    public static final String PRIMARY_KEY = "stockId";

    private Long stockId;

    private Long materialId;

    private String unit;

    private java.math.BigDecimal amount;

    private String materialName;

    public static Boolean isUpdatable(String property) {
        if ("materialName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getMaterialId() {
        return this.materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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
        if (!(o instanceof Storage))
            return false;
        Storage bean = (Storage) o;
        if (stockId.equals(bean.getStockId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return stockId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("stockId=" + stockId + ",");
        buffer.append("materialId=" + materialId + ",");
        buffer.append("unit=" + unit + ",");
        buffer.append("amount=" + amount + ",");
        buffer.append("materialName=" + materialName);
        buffer.append("]");
        return buffer.toString();
    }
}
