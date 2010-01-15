package cn.edu.pku.dr.requirement.elicitation.data;

public class UseCaseInteraction implements java.io.Serializable {
    public static final String TABLE_NAME = "use_case_interaction";

    public static final String VIEW_NAME = "v_use_case_interaction";

    public static final String PRIMARY_KEY = "useCaseInteractionId";

    private Long useCaseInteractionId;

    private Long useCaseId;

    private String useState;

    private java.sql.Date buildTime;

    private java.sql.Date updateTime;

    private Long creatorId;

    private String code;

    private String operatorAction;

    private String systemAction;

    private String systemOutput;

    private Long classId;

    private Short sequence;

    private String columns;

    private String className;

    private String classChiName;

    public static Boolean isUpdatable(String property) {
        if ("className".equals(property))
            return new Boolean(false);
        if ("classChiName".equals(property))
            return new Boolean(false);
        return new Boolean(true);
    }

    public static String getSubClass(String property) {
        return null;
    }

    public Long getUseCaseInteractionId() {
        return this.useCaseInteractionId;
    }

    public void setUseCaseInteractionId(Long useCaseInteractionId) {
        this.useCaseInteractionId = useCaseInteractionId;
    }

    public Long getUseCaseId() {
        return this.useCaseId;
    }

    public void setUseCaseId(Long useCaseId) {
        this.useCaseId = useCaseId;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperatorAction() {
        return this.operatorAction;
    }

    public void setOperatorAction(String operatorAction) {
        this.operatorAction = operatorAction;
    }

    public String getSystemAction() {
        return this.systemAction;
    }

    public void setSystemAction(String systemAction) {
        this.systemAction = systemAction;
    }

    public String getSystemOutput() {
        return this.systemOutput;
    }

    public void setSystemOutput(String systemOutput) {
        this.systemOutput = systemOutput;
    }

    public Long getClassId() {
        return this.classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Short getSequence() {
        return this.sequence;
    }

    public void setSequence(Short sequence) {
        this.sequence = sequence;
    }

    public String getColumns() {
        return this.columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassChiName() {
        return this.classChiName;
    }

    public void setClassChiName(String classChiName) {
        this.classChiName = classChiName;
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
        if (!(o instanceof UseCaseInteraction))
            return false;
        UseCaseInteraction bean = (UseCaseInteraction) o;
        if (useCaseInteractionId.equals(bean.getUseCaseInteractionId()))
            return true;
        else
            return false;
    }

    public int hashCode() {
        return useCaseInteractionId.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append("useCaseInteractionId=" + useCaseInteractionId + ",");
        buffer.append("useCaseId=" + useCaseId + ",");
        buffer.append("useState=" + useState + ",");
        buffer.append("buildTime=" + buildTime + ",");
        buffer.append("updateTime=" + updateTime + ",");
        buffer.append("creatorId=" + creatorId + ",");
        buffer.append("code=" + code + ",");
        buffer.append("operatorAction=" + operatorAction + ",");
        buffer.append("systemAction=" + systemAction + ",");
        buffer.append("systemOutput=" + systemOutput + ",");
        buffer.append("classId=" + classId + ",");
        buffer.append("sequence=" + sequence + ",");
        buffer.append("columns=" + columns + ",");
        buffer.append("className=" + className + ",");
        buffer.append("classChiName=" + classChiName);
        buffer.append("]");
        return buffer.toString();
    }
}
