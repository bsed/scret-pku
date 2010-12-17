/**
 * 
 */
package elicitation.model.project;

public class RoleMap{
	/**
	 * 
	 */
	private final Scenario RoleMap;
	public Role role;
	public String description;
	public RoleMap(Scenario scenario){
		RoleMap = scenario;}		
	public Role getRole(){
		return role;
	}
	public String getDescription(){
		return description;
	}
}