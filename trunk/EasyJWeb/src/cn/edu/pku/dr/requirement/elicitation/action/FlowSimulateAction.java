package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.http.servlet.SingleDataAction;
import easyJ.system.data.SystemClass;
import easyJ.system.data.UserPropertyRight;
import easyJ.system.service.HtmlClientComponentService;

public class FlowSimulateAction extends SingleDataAction{
    private UseCaseInteraction interaction;
    private Long useCaseId;
    private String flowNodeCode;
    private String hintMessage; //如果不是显示数据，而是显示提示信息，则提示信息是有点击的按钮传过来的。
    
    public void getInteraction() throws EasyJException{
        //首先得到当前用户需要看到的流程编号，并且得到当前流程编号对应的节点。
        String[] accurateProperties = {"code"};
        flowNodeCode = request.getParameter("flowNodeCode");
        hintMessage = request.getParameter("hintMessage");
        //需要知道是哪个useCase的，useCaseId不能为空
        String id = request.getParameter("useCaseId");
        useCaseId = new Long(id);
        interaction = new UseCaseInteraction();
        interaction.setUseCaseId(useCaseId);
        if (flowNodeCode == null) {
            interaction.setSequence(new Short((short)1));
        } else {
            interaction.setCode(flowNodeCode);
        }
        dp.setAccurateProperties(accurateProperties);
        interaction = (UseCaseInteraction)dp.get(interaction);
        flowNodeCode = interaction.getCode();
    }
    
    /**
     * 模拟用户点击了功能按钮
     * 确定 {
         正确： 跳转到[选择转账]
         错误： 提示"密码错误"   跳转到 [密码错误]
         错误超过三次： 提示用"户卡被吞" 跳转到[]
       }
       
       取消{
       
       }
     * 
     */
    public void simulateFunctionClick() throws EasyJException {
        getInteraction();
        String sysOutput = interaction.getSystemOutput();
        String functionPatternStr = "\\{(.+?)\\}";
        String namePatternStr = "(.*)：";
        String hintPatternStr = "\"(.*)\"";
        String jumpPatternStr = "\\[(.+)\\]";
        String[] functionDeals =  extract(functionPatternStr, sysOutput);
        //如果在输出中没有{}，则认为整个的输出都是一个。
        if (functionDeals == null) {
            functionDeals = new String[1];
            functionDeals[0] = sysOutput;
        }
        ArrayList<String> conditionNames = new ArrayList<String>(); //condition的名字
        ArrayList<String> hints = new ArrayList<String>(); //提示
        ArrayList<String> jumpFlowNodeCodes = new ArrayList<String>(); //跳转到哪个节点
        
        for (String deal : functionDeals) {
            String[] conditions = deal.split("<BR>");
            for (String condition : conditions) {
                condition = condition.replaceAll("\\&nbsp;", "");
                if (GenericValidator.isBlankOrNull(condition))
                    continue;
                Pattern pattern = Pattern.compile(namePatternStr);
                Matcher matcher = pattern.matcher(condition);
                if(matcher.find()) {
                    conditionNames.add(matcher.group(1));
                } else {
                    conditionNames.add("继续");
                }
                pattern = Pattern.compile(hintPatternStr);
                matcher = pattern.matcher(condition);
                if (matcher.find()) {
                    hints.add(matcher.group(1));
                } else {
                    hints.add("");
                }
                pattern = Pattern.compile(jumpPatternStr);
                matcher = pattern.matcher(condition);
                while(matcher.find()) {
                    jumpFlowNodeCodes.add(matcher.group(1));
                }
            }
        }
        
        StringBuffer msg = new StringBuffer();
        msg.append("<table><tr>");
        int i = 0;
        for(String name: conditionNames) {
            String hint = hints.get(i); 
            String jumpFlowNodeCode = jumpFlowNodeCodes.get(i);
            msg.append("<input type='button' value='" + name 
                    + "' onclick=\"UseCase.simulateJump(" + useCaseId + ",'"
                    + hint+"','" + jumpFlowNodeCode + "')\"/>");
            i++;
        }
        returnMessage = msg.toString() + "<message>";
    }
    
    public void simulate() throws EasyJException{
        getInteraction();

        
        
        //得到对当前数据的操作功能。
        String userInput = interaction.getOperatorAction();
        
        String functionPatternStr = "\\[(.+?)\\]";
        String[] functions =  extract(functionPatternStr, userInput);
        
        
        //暂时一个页面只能处理一个数据，将来如果处理多个数据的时候格式会不一样。
        Long classId = interaction.getClassId();
        
        int sequence = interaction.getSequence();
        
        //如果第一个交互不涉及用户操作对系统的操作，则接着往下找。 例如：插入银行卡
        while (functions == null || functions.length == 0) {
            UseCaseInteraction nextInteraction = new UseCaseInteraction();
            nextInteraction.setUseCaseId(useCaseId);
            nextInteraction.setSequence((short)(sequence+1));
            userInput = nextInteraction.getOperatorAction();
            //这里加上问号是进行懒惰匹配用的，为了尽量短的匹配
            functionPatternStr = "\\[(.+?)\\]";
            functions =  extract(functionPatternStr, userInput);
        }
        
        String classType = null;
        StringBuffer returnMsg = new StringBuffer();
        returnMsg.append("<div align='center' style='font:18' color='red'>" + flowNodeCode +"</div>");
        if (hintMessage != null)
            returnMsg.append("<div style='font:18' color='red'>" + hintMessage+"</div>");
        if (classId != null) {
            SystemClass sysClass = new SystemClass();
            sysClass.setClassId(classId);
            sysClass = (SystemClass)dp.get(sysClass);
            classType = sysClass.getClassName();
            String columns = interaction.getColumns();
            ArrayList editProperties = userCache.getEditProperties(classType);
            
            //得到用来显示的字段。
            ArrayList showProperties = new ArrayList();
            for (Object object : editProperties) {
                UserPropertyRight property = (UserPropertyRight)object;
                if (columns != null && 
                        columns.indexOf(","+property.getPropertyName()+",") >= 0) {
                    showProperties.add(property);
                }
            }
            //如果用户没有设置，则默认显示所有字段
            if (showProperties.size() == 0) 
                showProperties = editProperties;
            
            Object obj = BeanUtil.getEmptyObject(classType);
            returnMsg.append(HtmlClientComponentService.getEdit(request, obj, 
                    showProperties, "1", 0, -1, "N", ""));
        }
        
        returnMessage = returnMsg.toString() 
                + getFunctions(functions) + HtmlClientComponentService.getHistory()
                + "<message>";
    }
    
    
    
    
    /**
     * 
     * @param functions
     * @param flowNodeCode 必须知道点击的按钮到底是属于流程的哪一步。
     * @return
     */
    public StringBuffer getFunctions(String[] functions) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table><tr><td>");
        int i = 1;
        //i用来标记当前点击的按钮是第几个按钮，用来对应相应的处理和跳转的
        for(String function : functions) {
            buffer.append("<input type='button' class='button' value='" 
                    + function+"', onclick=\"UseCase.simulateClick(" + useCaseId +",'" 
                    + flowNodeCode + "',"+ i + ")\"/>");
            i++;
        }
        buffer.append("</td></tr></table>");
        return buffer;
    }
    
    
    /**
     * 这个方法用来从action中取得各种数据，使用的是正则表达式的方法，
     * 功能用[**]来表示
     * 数据也用[**]来表示
     * @param action
     * @return
     */
    private static String[] extract(String patternStr, String content) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(content);
        ArrayList<String> result = new ArrayList<String>();
        
        while(matcher.find()) {
            result.add(matcher.group(1));
        }
        if (result.size() == 0)
            return null;
        String[] ret = (String[])result.toArray(new String[result.size()]);
        return ret;
    }
}
