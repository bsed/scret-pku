package cn.edu.pku.dr.requirement.elicitation.data;

public class Domain implements java.io.Serializable {
    public static final String TABLE_NAME = "domain";

    public static final String VIEW_NAME = "domain";

    public static final String PRIMARY_KEY = "domainId";

    private Long domainId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String domainName;

    private String domainDiscription;

    public static final String ID_PROPERTY = "domainId";

    public static final String DISPLAY_PROPERTY = "domainName";

    public static Boolean isUpdatable(String property) {
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getDomainId() {
        return this.domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
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

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainDiscription() {
        return this.domainDiscription;
    }

    public void setDomainDiscription(String domainDiscription) {
        this.domainDiscription = domainDiscription;
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
        if (!(o instanceof Domain))
            return false;
        Domain bean = (Domain) o;
        if (domainId.equals(bean.getDomainId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return domainId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("domainId=" + domainId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("domainName=" + domainName + ",");
        buffer.append("domainDiscription=" + domainDiscription);
        buffer.append("]");
        return buffer.toString();
    }
}
