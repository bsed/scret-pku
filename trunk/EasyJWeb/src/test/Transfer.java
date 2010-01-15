package test;

public class Transfer implements  java.io.Serializable{
    public static final String TABLE_NAME="transfer";
    public static final String VIEW_NAME="transfer";
    public static final String PRIMARY_KEY="transferId";
    private Long transferId;
    private String fromAccountNo;
    private Integer toAccountNo;
    private java.math.BigDecimal amount;
    private java.sql.Date buildTime;
    private java.sql.Date updateTime;
    private Long creatorId;
    private String useState;
    public static Boolean isUpdatable(String property){
      return new Boolean(true);
    }
    public static String getSubClass(String property){
      return null;
    }
    public Long getTransferId()     { return this.transferId;}
    public void setTransferId(Long transferId)   { this.transferId=transferId;}
    public String getFromAccountNo()     { return this.fromAccountNo;}
    public void setFromAccountNo(String fromAccountNo)   { this.fromAccountNo=fromAccountNo;}
    public Integer getToAccountNo()     { return this.toAccountNo;}
    public void setToAccountNo(Integer toAccountNo)   { this.toAccountNo=toAccountNo;}
    public java.math.BigDecimal getAmount()     { return this.amount;}
    public void setAmount(java.math.BigDecimal amount)   { this.amount=amount;}
    public java.sql.Date getBuildTime()     { return this.buildTime;}
    public void setBuildTime(java.sql.Date buildTime)   { this.buildTime=buildTime;}
    public java.sql.Date getUpdateTime()     { return this.updateTime;}
    public void setUpdateTime(java.sql.Date updateTime)   { this.updateTime=updateTime;}
    public Long getCreatorId()     { return this.creatorId;}
    public void setCreatorId(Long creatorId)   { this.creatorId=creatorId;}
    public String getUseState()     { return this.useState;}
    public void setUseState(String useState)   { this.useState=useState;}
    public Object clone(){
      Object object=null;
      try{object = easyJ.common.BeanUtil.cloneObject(this);}catch (easyJ.common.EasyJException ee){return null;}
      return object;
    }
    public boolean equals(Object o){
      if(!(o instanceof Transfer))
          return false;
      Transfer bean=(Transfer)o;
      if(transferId.equals(bean.getTransferId()))
          return true;
      else
              return false;
    }
    public int hashCode()
    {
      return transferId.hashCode();
    }
    public String toString(){
      StringBuffer buffer=new StringBuffer();
      buffer.append("[");
      buffer.append("transferId="+transferId+",");
      buffer.append("fromAccountNo="+fromAccountNo+",");
      buffer.append("toAccountNo="+toAccountNo+",");
      buffer.append("amount="+amount+",");
      buffer.append("buildTime="+buildTime+",");
      buffer.append("updateTime="+updateTime+",");
      buffer.append("creatorId="+creatorId+",");
      buffer.append("useState="+useState);
      buffer.append("]");
      return buffer.toString();
    }
  }
