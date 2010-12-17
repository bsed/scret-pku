package elicitation.action.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

import elicitation.model.domain.Domain;
import elicitation.model.domain.DomainService;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;

public class DomainListAction extends ActionSupport{
	/**
	 * static 保证后续使用高效；
	 * 不然的话，得经常刷新页面.
	 */
	private  List<Domain> domains = new ArrayList<Domain>();	
	private  List<Project> projects = null ;
	private int domainId = -100;
	private String domainName = null;
	public List<Domain> getDomains() throws SQLException{
		domains = DomainService.selectDomainList();
		for(Domain domain : domains) {
			DomainService.getProjects(domain);
		}
		
		return domains;
	}
	public String getDomainName(){
		return domainName;
	}
	public String getDomainDescription(){
		if(domains == null || domains.size() == 0) return "";
		for(Domain domain:domains){
			if(domain.getDomainId() == domainId)
				return domain.getDomainDes();
		}
		return domains.get(0).getDomainDes();
	}
	public List<Project> getProjects(){
		if(domains.size()>0 ){
			if(domainId == -100) domainId = domains.get(0).getDomainId() ;
			Domain dom = getDomain(domainId);
			if(dom !=null){
				return dom.getProjects();
			}
		}
		return null;
	}
	public void setDomainId(int did){
		this.domainId = did;		
	}
	public String getName(){
		return "haha";
	}
	public void setName(String name){
		return;
	}
	public String getProjectsOfDomain() throws Exception{
		if(domains == null || domains.size() == 0) return ActionSupport.SUCCESS; 
		//if(domainId == -1 )domainId = domains.get(0).getDomainId();
		for(Domain domain:domains){
			if(domain.getDomainId() == domainId){
				projects= domain.getProjects();
				domainName = domain.getDomainName();
				
				System.out.println("name="+domainName);
				return ActionSupport.SUCCESS;
			}
		}
		return ActionSupport.SUCCESS;
	}
	public Domain getDomain(int did){
		for(Domain domain:domains){
			if(domain.getDomainId() == did){
				return domain;
			}
		}
		return null;
	}
	@Override
	public String execute() throws SQLException{
		domains = DomainService.selectDomainList();
		for(Domain domain : domains) {
			DomainService.getProjects(domain);
			System.out.println("DomainID="+domain.getDomainId()+" projectNum="+domain.getProjects().size());
		}
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache,must-revalidate"); //保证首页及时刷新.		
		return ActionSupport.SUCCESS;
	}	
}
