package cn.edu.pku.dr.requirement.elicitation.data;

public class ScenarioDataRelation implements  java.io.Serializable{
    public static final String TABLE_NAME="scenario_data_relation";
    public static final String VIEW_NAME="v_scenario_data_relation";
    public static final String PRIMARY_KEY="scenarioDataId";
    private Long scenarioDataId;
    private Long dataId;
    private Long scenarioId;
    private Integer sequence;
    private String scenarioName;
    private String dataName;
    private Long projectId;
    private String projectName;
    public static Boolean isUpdatable(String property){
        if("scenarioName".equals(property))    return new Boolean(false);
        if("dataName".equals(property))    return new Boolean(false);
        if("projectId".equals(property))    return new Boolean(false);
        if("projectName".equals(property))    return new Boolean(false);
      return new Boolean(true);
    }
    public static String getSubClass(String property){
      return null;
    }
    public Long getScenarioDataId()     { return this.scenarioDataId;}
    public void setScenarioDataId(Long scenarioDataId)   { this.scenarioDataId=scenarioDataId;}
    public Long getDataId()     { return this.dataId;}
    public void setDataId(Long dataId)   { this.dataId=dataId;}
    public Long getScenarioId()     { return this.scenarioId;}
    public void setScenarioId(Long scenarioId)   { this.scenarioId=scenarioId;}
    public Integer getSequence()     { return this.sequence;}
    public void setSequence(Integer sequence)   { this.sequence=sequence;}
    public String getScenarioName()     { return this.scenarioName;}
    public void setScenarioName(String scenarioName)   { this.scenarioName=scenarioName;}
    public String getDataName()     { return this.dataName;}
    public void setDataName(String dataName)   { this.dataName=dataName;}
    public Long getProjectId()     { return this.projectId;}
    public void setProjectId(Long projectId)   { this.projectId=projectId;}
    public String getProjectName()     { return this.projectName;}
    public void setProjectName(String projectName)   { this.projectName=projectName;}
    public Object clone(){
      Object object=null;
      try{object = easyJ.common.BeanUtil.cloneObject(this);}catch (easyJ.common.EasyJException ee){return null;}
      return object;
    }
    public boolean equals(Object o){
      if(!(o instanceof ScenarioDataRelation))
          return false;
      ScenarioDataRelation bean=(ScenarioDataRelation)o;
      if(scenarioDataId.equals(bean.getScenarioDataId()))
          return true;
      else
              return false;
    }
    public int hashCode()
    {
      return scenarioDataId.hashCode();
    }
    public String toString(){
      StringBuffer buffer=new StringBuffer();
      buffer.append("[");
      buffer.append("scenarioDataId="+scenarioDataId+",");
      buffer.append("dataId="+dataId+",");
      buffer.append("scenarioId="+scenarioId+",");
      buffer.append("sequence="+sequence+",");
      buffer.append("scenarioName="+scenarioName+",");
      buffer.append("dataName="+dataName+",");
      buffer.append("projectId="+projectId+",");
      buffer.append("projectName="+projectName);
      buffer.append("]");
      return buffer.toString();
    }
  }

