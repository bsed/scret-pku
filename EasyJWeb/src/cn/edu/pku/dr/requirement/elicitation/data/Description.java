package cn.edu.pku.dr.requirement.elicitation.data;

import java.util.ArrayList;

public class Description implements java.io.Serializable {
    public static final String TABLE_NAME = "description";

    public static final String VIEW_NAME = "v_description";

    public static final String PRIMARY_KEY = "descriptionId";

    private Long descriptionId;

    private Long scenarioId;

    private Long roleId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private java.math.BigDecimal creatorId;

    private String descriptionContent;

    private String roleName;

    private ArrayList remarks;

    public static Boolean isUpdatable(String property) {
        if ("roleName".equals(property)) {
            return new Boolean(false);
        }
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        if ("remarks".equals(property)) {
            return "cn.edu.pku.dr.requirement.elicitation.data.Remark";
        }
        return null;
    }

    public static String[] getSubClassProperties() {
        String[] subClassProperties = {
            "remarks"
        };
        return subClassProperties;
    }

    public Long getDescriptionId() {
        return this.descriptionId;
    }

    public void setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Long getScenarioId() {
        return this.scenarioId;
    }

    public void setScenarioId(Long scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUseState() {
        return this.useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public java.sql.Date getBuildTime() {
        return this.buildTime;
    }

    public void setBuildTime(java.sql.Date buildTime) {
        this.buildTime = buildTime;
    }

    public java.sql.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(java.sql.Date updateTime) {
        this.updateTime = updateTime;
    }

    public java.math.BigDecimal getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(java.math.BigDecimal creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescriptionContent() {
        return this.descriptionContent;
    }

    public void setDescriptionContent(String descriptionContent) {
        this.descriptionContent = descriptionContent;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
        if (!(o instanceof Description)) {
            return false;
        }
        Description bean = (Description) o;
        if (descriptionId.equals(bean.getDescriptionId())) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return descriptionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("descriptionId=" + descriptionId + ",");
        buffer.append("scenarioId=" + scenarioId + ",");
        buffer.append("roleId=" + roleId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("descriptionContent=" + descriptionContent + ",");
        buffer.append("roleName=" + roleName);
        buffer.append("]");
        return buffer.toString();
    }

    public void setRemarks(ArrayList remarks) {
        this.remarks = remarks;
    }

    public ArrayList getRemarks() {
        return remarks;
    }

}
