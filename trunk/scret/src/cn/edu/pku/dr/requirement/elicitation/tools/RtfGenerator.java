package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.edu.pku.dr.requirement.elicitation.data.Project;
import cn.edu.pku.dr.requirement.elicitation.data.Scenario;
import cn.edu.pku.dr.requirement.elicitation.data.UseCase;
import cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfTableOfContents;
import com.lowagie.text.rtf.style.RtfFont;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;

import easyJ.business.proxy.CompositeDataProxy;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;
import easyJ.http.servlet.CompositeDataAction;

public class RtfGenerator extends CompositeDataAction {

	public File createRtf(String projectName) throws EasyJException,
			IOException, DocumentException {

		ArrayList scenarios = new ArrayList();
		// 创建空白rtf文档
		// String filePath = "/EasyJ/" + projectName + ".rtf";
		
		File dir = new File ("./RTFs");
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath ="./RTFs/"+projectName + ".rtf";
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		} else {
			myFilePath.delete();
			myFilePath.createNewFile();
		}

		// 打开rtf文档
		Document document = new Document();
		FileOutputStream myFile = new FileOutputStream(filePath);
		RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document, myFile);
		document.open();
		// TODO 加文档标题，即项目名 有待居中
		RtfFont projectNameFont = new RtfFont("Arial", 35, RtfFont.STYLE_BOLD);
		Paragraph paragraph = new Paragraph();
		// Use the RtfFonts when creating the text.
		paragraph.add(new Chunk(projectName, projectNameFont));
		Paragraph paragraph2 = new Paragraph();
		// Use the RtfFonts when creating the text.
		paragraph2.add(new Chunk("需求文档", projectNameFont));

		paragraph.setAlignment(1);
		paragraph2.setAlignment(1);
		document.add(paragraph);
		document.add(paragraph2);
		// 加Table of Content
		RtfParagraphStyle tocLevel1Style = new RtfParagraphStyle("toc 1",
				"Times New Roman", 11, Font.NORMAL, Color.BLACK);
		RtfParagraphStyle tocLevel2Style = new RtfParagraphStyle("toc 2",
				"Times New Roman", 10, Font.NORMAL, Color.BLACK);
		tocLevel2Style.setIndentLeft(10);

		// Register the paragraph stylesheets with the RtfWriter2
		rtfWriter2.getDocumentSettings().registerParagraphStyle(tocLevel1Style);
		rtfWriter2.getDocumentSettings().registerParagraphStyle(tocLevel2Style);

		// Create a Paragraph and add the table of contents to it
		Paragraph par = new Paragraph();

		par.add(new RtfTableOfContents("如想查看目录，单击右键选择\"更新域\""));
		par.setAlignment(1);
		document.add(par);

		CompositeDataProxy cdp = CompositeDataProxy.getInstance();
		SingleDataProxy sdp = SingleDataProxy.getInstance();
		Project p = new Project();
		p.setProjectName(projectName);
		p = (Project) sdp.get(p);
		// 得到该项目的所有的场景
		Scenario s = new Scenario();
		s.setProjectId(p.getProjectId());
		scenarios = cdp.query(s);
		if (scenarios.size() > 0) {
			for (int i = 0; i < scenarios.size(); i++) {
				s = (Scenario) scenarios.get(i);
				document.add(new Paragraph(""));
				// 加场景名
				document.add(new Paragraph("场景" + (i + 1) + ": "
						+ s.getScenarioName().toString(),
						RtfParagraphStyle.STYLE_HEADING_1));
				// TODO 加用况
				UseCase uc = new UseCase();
				uc.setScenarioId(s.getScenarioId());
				ArrayList ucs = cdp.query(uc);
				if (ucs.size() > 0) {
					for (int j = 0; j < ucs.size(); j++) {

						uc = (UseCase) ucs.get(j);
						document.add(new Paragraph(""));
						document.add(new Paragraph("用况" + (i + 1) + "."
								+ (j + 1) + ": "
								+ uc.getUseCaseName().toString(),
								RtfParagraphStyle.STYLE_HEADING_2));

						RtfBorderGroup normal = new RtfBorderGroup(
								Rectangle.BOX, RtfBorder.BORDER_SINGLE, 1,
								new Color(0, 0, 0));

						Paragraph title = new Paragraph();
						title.add(new Chunk(uc.getUseCaseName().trim()));
						title.setAlignment(1);
						document.add(title);

						RtfCell useCaseID = new RtfCell("用况编号:");
						useCaseID.setBorders(normal);

						RtfCell useCaseIDC = new RtfCell(uc.getUseCaseId()
								.toString());
						useCaseIDC.setBorders(normal);

						RtfCell useCaseName = new RtfCell("用况名称:");
						useCaseName.setBorders(normal);

						RtfCell useCaseNameC = new RtfCell(uc.getUseCaseName());
						useCaseNameC.setBorders(normal);

						RtfCell useCaseDescription = new RtfCell("用况描述:");
						useCaseDescription.setBorders(normal);

						RtfCell useCaseDescriptionC = new RtfCell(uc
								.getUseCaseDescription());
						useCaseDescriptionC.setBorders(normal);

						RtfCell useCasePriority = new RtfCell("用况优先级:");
						useCasePriority.setBorders(normal);

						RtfCell useCasePriorityC = null;

						if (uc.getUseCasePriority() != null) {
							useCasePriorityC = new RtfCell(uc
									.getUseCasePriority().toString());
						} else {
							useCasePriorityC = new RtfCell("");
						}

						useCasePriorityC.setBorders(normal);

						RtfCell specialRequirement = new RtfCell("特殊要求:");
						specialRequirement.setBorders(normal);

						RtfCell specialRequirementC = new RtfCell(uc
								.getUseCaseSpecialRequirement());
						specialRequirementC.setColspan(3);
						specialRequirementC.setBorders(normal);

						RtfCell preCondition = new RtfCell("前置条件:");
						preCondition.setBorders(normal);

						RtfCell preConditionC = new RtfCell(uc
								.getUseCasePreconditions());
						preConditionC.setColspan(3);
						preConditionC.setBorders(normal);

						RtfCell fireCondition = new RtfCell("触发条件:");
						fireCondition.setBorders(normal);

						RtfCell fireConditionC = new RtfCell(uc
								.getUseCaseTrigger());
						fireConditionC.setColspan(3);
						fireConditionC.setBorders(normal);

						RtfCell postCondition = new RtfCell("后置条件:");
						postCondition.setBorders(normal);

						RtfCell postConditionC = new RtfCell(uc
								.getUseCasePostConditions());
						postConditionC.setColspan(3);
						postConditionC.setBorders(normal);

						RtfCell failedEndCondition = new RtfCell("失败情况:");
						failedEndCondition.setBorders(normal);

						RtfCell failedEndConditionC = new RtfCell(uc
								.getUseCaseFailedEndCondition());
						failedEndConditionC.setColspan(3);
						failedEndConditionC.setBorders(normal);

						Table table = new Table(4);
						table.addCell(useCaseID);
						table.addCell(useCaseIDC);
						table.addCell(useCaseName);
						table.addCell(useCaseNameC);
						table.addCell(useCaseDescription);
						table.addCell(useCaseDescriptionC);
						table.addCell(useCasePriority);
						table.addCell(useCasePriorityC);
						
						table.addCell(fireCondition);
						table.addCell(fireConditionC);
						table.addCell(preCondition);
						table.addCell(preConditionC);
						table.addCell(failedEndCondition);
						table.addCell(failedEndConditionC);
						table.addCell(postCondition);
						table.addCell(postConditionC);					
						table.addCell(specialRequirement);
						table.addCell(specialRequirementC);

						// 生成Use Case具体描述的标题

						RtfCell userOperation = new RtfCell("用户操作");
						userOperation.setBorders(normal);

						RtfCell systemOperation = new RtfCell("系统操作");
						systemOperation.setBorders(normal);

						RtfCell systemOutput = new RtfCell("系统输出");
						systemOutput.setBorders(normal);

						Table table2 = new Table(3);
						table2.addCell(userOperation);
						table2.addCell(systemOperation);
						table2.addCell(systemOutput);

						// 生成Use Case具体描述的内容
						ArrayList ucis = new ArrayList();

						UseCaseInteraction uci = new UseCaseInteraction();
						uci.setUseCaseId(uc.getUseCaseId());
						OrderRule orderule = new OrderRule();
						orderule.setOrderColumn("sequence");
						orderule.setOrderDirection(OrderDirection.ASC);
						OrderRule[] orderRules = { orderule };
						String temp;
						ucis = cdp.query(uci, orderRules);
						if (ucis.size() > 0) {
							for (int k = 0; k < ucis.size(); k++) {
								temp = ((UseCaseInteraction) ucis.get(k))
										.getOperatorAction();
								temp = removeHtml(temp);
								RtfCell userOperationC = new RtfCell(temp);
								userOperationC.setBorders(normal);

								temp = ((UseCaseInteraction) ucis.get(k))
										.getSystemAction();
								temp = removeHtml(temp);
								RtfCell systemOperationC = new RtfCell(temp);
								systemOperationC.setBorders(normal);

								temp = ((UseCaseInteraction) ucis.get(k))
										.getSystemOutput();
								temp = removeHtml(temp);
								RtfCell systemOutputC = new RtfCell(temp);
								systemOutputC.setBorders(normal);
								table2.addCell(userOperationC);
								table2.addCell(systemOperationC);
								table2.addCell(systemOutputC);
							}

						} else {

						}
						//

						document.add(table);
						document.add(table2);

					}

				} else {
					// TODO 暂无用况的时候
					document.add(new Paragraph(""));
					Paragraph noUseCase = new Paragraph();
					noUseCase.add(new Chunk("暂无用况"));
					document.add(noUseCase);
				}
			}

		} else {
			// TODO 加场景名 暂无场景的时候
		}

		document.close();
		myFile.close();
		rtfWriter2.close();

		return myFilePath;
	}

	public String removeHtml(String page) {
		if (page == null || page.trim().equals("")) {
			return "";
		}
		int length = page.length();
		// 去掉所有html元素,
		String str = page.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;

	}

	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws EasyJException
	 */
	public static void main(String[] args) throws EasyJException, IOException,
			DocumentException {
		// TODO Auto-generated method stub
		new RtfGenerator().createRtf("北化ERP");

	}

}
