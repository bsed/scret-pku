package test;

public class WarehouseWarrantDetail implements java.io.Serializable {
    public static final String TABLE_NAME = "warehouse_warrant_detail";

    public static final String VIEW_NAME = "v_warehouse_warrant_detail";

    public static final String PRIMARY_KEY = "warehouseWarrantDetailId";

    private Long warehouseWarrantDetailId;

    private java.math.BigDecimal materialId;

    private java.math.BigDecimal amount;

    private String unit;

    private Long warehouseWarrantId;

    private String useState;

    private Short sequence;

    private String materialName;

    public static Boolean isUpdatable(String property) {
        if ("materialName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getWarehouseWarrantDetailId() {
        return this.warehouseWarrantDetailId;
    }

    public void setWarehouseWarrantDetailId(Long warehouseWarrantDetailId) {
        this.warehouseWarrantDetailId = warehouseWarrantDetailId;
    }

    public java.math.BigDecimal getMaterialId() {
        return this.materialId;
    }

    public void setMaterialId(java.math.BigDecimal materialId) {
        this.materialId = materialId;
    }

    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getWarehouseWarrantId() {
        return this.warehouseWarrantId;
    }

    public void setWarehouseWarrantId(Long warehouseWarrantId) {
        this.warehouseWarrantId = warehouseWarrantId;
    }

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public Short getSequence() {
        return this.sequence;
    }

    public void setSequence(Short sequence) {
        this.sequence = sequence;
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
        if (!(o instanceof WarehouseWarrantDetail))
            return false;
        WarehouseWarrantDetail bean = (WarehouseWarrantDetail) o;
        if (warehouseWarrantDetailId.equals(bean.getWarehouseWarrantDetailId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return warehouseWarrantDetailId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("warehouseWarrantDetailId=" + warehouseWarrantDetailId
                + ",");
        buffer.append("materialId=" + materialId + ",");
        buffer.append("amount=" + amount + ",");
        buffer.append("unit=" + unit + ",");
        buffer.append("warehouseWarrantId=" + warehouseWarrantId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("sequence=" + sequence + ",");
        buffer.append("materialName=" + materialName);
        buffer.append("]");
        return buffer.toString();
    }
}
