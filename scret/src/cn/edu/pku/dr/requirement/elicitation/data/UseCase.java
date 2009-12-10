package cn.edu.pku.dr.requirement.elicitation.data;

public class UseCase implements java.io.Serializable {
	public static final String TABLE_NAME = "use_case";

	public static final String VIEW_NAME = "use_case";

	public static final String PRIMARY_KEY = "useCaseId";

	private Long useCaseId;
	private Long scenarioId;
	private String useState;

	private java.sql.Date buildTime;

	private java.sql.Date updateTime;

	private Long creatorId;

	private String useCaseNo;

	private String useCaseName;

	private String useCaseDescription;

	private Integer useCasePriority;

	private String useCaseTrigger;

	private String useCasePreconditions;

	private String useCaseFailedEndCondition;

	private String useCasePostConditions;

	private String useCaseSpecialRequirement;

	private java.util.ArrayList details;

	public static String getSubClass(String property) {
		if ("details".equals(property))
			return "cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction";
		return null;
	}

	public static String[] getSubClassProperties() {
		String[] properties = { "details" };
		return properties;
	}

	public static Boolean isUpdatable(String property) {
		return new Boolean(true);
	}

	public Long getUseCaseId() {
		return this.useCaseId;
	}

	public void setUseCaseId(Long useCaseId) {
		this.useCaseId = useCaseId;
	}
	public Long getScenarioId() {
		return this.scenarioId;
	}

	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
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

	public String getUseCaseNo() {
		return this.useCaseNo;
	}

	public void setUseCaseNo(String useCaseNo) {
		this.useCaseNo = useCaseNo;
	}

	public String getUseCaseName() {
		return this.useCaseName;
	}

	public void setUseCaseName(String useCaseName) {
		this.useCaseName = useCaseName;
	}

	public String getUseCaseDescription() {
		return this.useCaseDescription;
	}

	public void setUseCaseDescription(String useCaseDescription) {
		this.useCaseDescription = useCaseDescription;
	}

	public Integer getUseCasePriority() {
		return this.useCasePriority;
	}

	public void setUseCasePriority(Integer useCasePriority) {
		this.useCasePriority = useCasePriority;
	}

	public String getUseCaseTrigger() {
		return this.useCaseTrigger;
	}

	public void setUseCaseTrigger(String useCaseTrigger) {
		this.useCaseTrigger = useCaseTrigger;
	}

	public String getUseCasePreconditions() {
		return this.useCasePreconditions;
	}

	public void setUseCasePreconditions(String useCasePreconditions) {
		this.useCasePreconditions = useCasePreconditions;
	}

	public String getUseCaseFailedEndCondition() {
		return this.useCaseFailedEndCondition;
	}

	public void setUseCaseFailedEndCondition(String useCaseFailedEndCondition) {
		this.useCaseFailedEndCondition = useCaseFailedEndCondition;
	}

	public String getUseCasePostConditions() {
		return this.useCasePostConditions;
	}

	public void setUseCasePostConditions(String useCasePostConditions) {
		this.useCasePostConditions = useCasePostConditions;
	}

	public String getUseCaseSpecialRequirement() {
		return this.useCaseSpecialRequirement;
	}

	public void setUseCaseSpecialRequirement(String useCaseSpecialRequirement) {
		this.useCaseSpecialRequirement = useCaseSpecialRequirement;
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
		if (!(o instanceof UseCase))
			return false;
		UseCase bean = (UseCase) o;
		if (useCaseId.equals(bean.getUseCaseId()))
			return true;
		else
			return false;
	}

	public int hashCode() {
		return useCaseId.hashCode();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append("useCaseId=" + useCaseId + ",");
		buffer.append("scenarioId=" + scenarioId + ",");
		buffer.append("useState=" + useState + ",");
		buffer.append("buildTime=" + buildTime + ",");
		buffer.append("updateTime=" + updateTime + ",");
		buffer.append("creatorId=" + creatorId + ",");
		buffer.append("useCaseNo=" + useCaseNo + ",");
		buffer.append("useCaseName=" + useCaseName + ",");
		buffer.append("useCaseDescription=" + useCaseDescription + ",");
		buffer.append("useCasePriority=" + useCasePriority + ",");
		buffer.append("useCaseTrigger=" + useCaseTrigger + ",");
		buffer.append("useCasePreconditions=" + useCasePreconditions + ",");
		buffer.append("useCaseFailedEndCondition=" + useCaseFailedEndCondition
				+ ",");
		buffer.append("useCasePostConditions=" + useCasePostConditions + ",");
		buffer.append("useCaseSpecialRequirement=" + useCaseSpecialRequirement);
		buffer.append("]");
		return buffer.toString();
	}

	public java.util.ArrayList getDetails() {
		return details;
	}

	public void setDetails(java.util.ArrayList details) {
		this.details = details;
	}
}
