package cn.edu.pku.dr.requirement.elicitation.system;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 这个类用来描述用户选择某个项目之后的上下文环境
 * 
 * @author liufeng
 */
public class Context {
    private Long projectId;

    private String projectName;

    private int projectRole; // 用来指示当前用户在project环境中的角色

    private int scenarioRole; // 用来指示用户在当前scenario中的角色，此属性暂时没用上
    
    private HashMap<String,String> roles = new HashMap<String,String>(); //用来存储此project所拥有的所有角色，用来进行角色过滤用

    public HashMap<String,String> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<String,String> roles) {
        this.roles = roles;
    }

    public int getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(int projectRole) {
        this.projectRole = projectRole;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public static String getSubClass(String propertyName) {
        return null;
    }

    public int getScenarioRole() {
        return scenarioRole;
    }

    public void setScenarioRole(int scenarioRole) {
        this.scenarioRole = scenarioRole;
    }
}
