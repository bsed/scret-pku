package cn.edu.pku.dr.requirement.elicitation.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.lowagie.text.DocumentException;

import cn.edu.pku.dr.requirement.elicitation.data.Project;
import cn.edu.pku.dr.requirement.elicitation.data.UserProjectRelation;
import cn.edu.pku.dr.requirement.elicitation.tools.RtfGenerator;
import easyJ.business.proxy.CompositeDataProxy;
import easyJ.common.EasyJException;
import easyJ.http.servlet.SingleDataAction;
import easyJ.system.data.SysUser;

public class ProjectDetailAction extends SingleDataAction {

	public void getloginProjectsDetails() throws EasyJException {

		CompositeDataProxy cdp = CompositeDataProxy.getInstance();
		UserProjectRelation upr = new UserProjectRelation();
		user = (SysUser) object;
		String resp = "";
		upr.setUserName(user.getUserName());
		Project project = new Project();
		project.setProjectId(upr.getProjectId());
		ArrayList projects = new ArrayList();
		projects = cdp.query(project);
		if (projects.size() > 0) {
			for (int i = 0; i < projects.size(); i++) {
				resp = resp + ((Project) projects.get(i)).getProjectName()
						+ ",";
			}

		}
		returnMessage = resp;
		response.setContentType("text/html;charset=GBK");
		System.out.print(returnMessage);
	}

	public void loginProjectsDetails() throws EasyJException, IOException,
			DocumentException {

		String filename = "";
		String ip = "";
		String times="";
		filename = new String(request.getParameter("filename").getBytes(
				"ISO-8859-1"));
		ip = new String(request.getParameter("ip").getBytes("ISO-8859-1"));
		times= new String(request.getParameter("times").getBytes("ISO-8859-1"));
		RtfGenerator rtf = new RtfGenerator();

		new SendRtf(ip, 10000+new Integer(times), rtf.createRtf(filename)).start();
		

	}

	

}
