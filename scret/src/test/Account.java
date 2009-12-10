package test;

public class Account implements  java.io.Serializable{
    public static final String TABLE_NAME="account";
    public static final String VIEW_NAME="account";
    public static final String PRIMARY_KEY="accountId";
    private Long accountId;
    private String accountNo;
    private String password;
    private String userName;
    private java.math.BigDecimal balance;
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
    public Long getAccountId()     { return this.accountId;}
    public void setAccountId(Long accountId)   { this.accountId=accountId;}
    public String getAccountNo()     { return this.accountNo;}
    public void setAccountNo(String accountNo)   { this.accountNo=accountNo;}
    public String getPassword()     { return this.password;}
    public void setPassword(String password)   { this.password=password;}
    public String getUserName()     { return this.userName;}
    public void setUserName(String userName)   { this.userName=userName;}
    public java.math.BigDecimal getBalance()     { return this.balance;}
    public void setBalance(java.math.BigDecimal balance)   { this.balance=balance;}
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
      if(!(o instanceof Account))
          return false;
      Account bean=(Account)o;
      if(accountId.equals(bean.getAccountId()))
          return true;
      else
              return false;
    }
    public int hashCode()
    {
      return accountId.hashCode();
    }
    public String toString(){
      StringBuffer buffer=new StringBuffer();
      buffer.append("[");
      buffer.append("accountId="+accountId+",");
      buffer.append("accountNo="+accountNo+",");
      buffer.append("password="+password+",");
      buffer.append("userName="+userName+",");
      buffer.append("balance="+balance+",");
      buffer.append("buildTime="+buildTime+",");
      buffer.append("updateTime="+updateTime+",");
      buffer.append("creatorId="+creatorId+",");
      buffer.append("useState="+useState);
      buffer.append("]");
      return buffer.toString();
    }
  }
