package cn.edu.pku.dr.requirement.elicitation.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import cn.edu.pku.dr.requirement.elicitation.data.Description;
import cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion;
import cn.edu.pku.dr.requirement.elicitation.data.Message;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemVersion;
import cn.edu.pku.dr.requirement.elicitation.data.Remark;
import cn.edu.pku.dr.requirement.elicitation.data.Scenario;
import cn.edu.pku.dr.requirement.elicitation.data.ScenarioDataRelation;
import cn.edu.pku.dr.requirement.elicitation.data.ScenarioRoleRelation;
import cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion;
import cn.edu.pku.dr.requirement.elicitation.data.UserProjectRelation;
import cn.edu.pku.dr.requirement.elicitation.data.UserScenarioRelation;
import cn.edu.pku.dr.requirement.elicitation.system.DictionaryConstant;
import cn.edu.pku.dr.requirement.elicitation.tools.AppletSize;
import cn.edu.pku.dr.requirement.elicitation.tools.HtmlTransformer;
import cn.edu.pku.dr.requirement.elicitation.tools.Myparser;
import easyJ.business.proxy.CompositeDataProxy;
import easyJ.business.proxy.DictionaryProxy;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.command.SelectCommand;
import easyJ.http.Globals;
import easyJ.system.service.HtmlClientComponentService;

public class ScenarioAction extends easyJ.http.servlet.SingleDataAction {
    private static CompositeDataProxy cdp = CompositeDataProxy.getInstance();

    public ScenarioAction() {}

    public void saveDescription() throws EasyJException {
        Description description = (Description) object;
        if (description.getDescriptionId() == null) {
            dp.create(description);
            returnMessage = "descriptionId=" + description.getDescriptionId()
                    + "<message>保存成功";
        } else {
            description.setUseState("Y");
            dp.update(description);
            returnMessage = "保存成功";
        }
    }
    
    /*当用户需要选择多个场景的时候，显示选择图形*/
    public void multiSelect() throws EasyJException {
        ArrayList sourceList=new ArrayList();
        
        
        //得到用户参与的场景，如果是owner，则应该得到所有此项目的场景，否则
        //找出所有此用户拥有权限的场景，包括两部分，一部分是他作为某个角色的拥有者参与了场景的讨论，另一种是他作为场景的查看者
        //可以查看场景，这个数据是从UserScenarioRelation中得到。 将这两部分数据统一转换为Scenario类型的
        if(context.getProjectRole()==DictionaryConstant.OWNER) {
            Scenario scenario=new Scenario();
            sourceList=dp.query(scenario);
        }else {
            String roles=user.getRoleIds();
            if(!GenericValidator.isBlankOrNull(roles)) {
                roles=roles.substring(1,roles.length()-1);
                
                SelectCommand scmd = DAOFactory.getSelectCommand(ScenarioRoleRelation.class);
                
                String[] rolesArr=roles.split(",");
                Long[] primaryKeysLong = new Long[rolesArr.length];
                for (int i = 0; i < rolesArr.length; i++)
                    primaryKeysLong[i] = new Long(rolesArr[i]);
                Filter filter=DAOFactory.getFilter("roleId",SQLOperator.IN,primaryKeysLong);
                Filter topFilter=DAOFactory.getFilter();
                topFilter.addFilter(filter);
                scmd.setFilter(topFilter);
                ArrayList relationList=dp.query(scmd);
                
                for(int i=0;i<relationList.size();i++) {
                    ScenarioRoleRelation relation=(ScenarioRoleRelation)relationList.get(i);
                    Scenario scenario=new Scenario();
                    scenario.setScenarioId(relation.getScenarioId());
                    scenario.setScenarioName(relation.getScenarioName());
                    sourceList.add(scenario);
                }
            }
            //得到用户可以查看的场景
            
            UserScenarioRelation relation=new UserScenarioRelation();
            relation.setUserId(userId);
            ArrayList relationList=dp.query(relation);
            for(int i=0;i<relationList.size();i++) {
                relation=(UserScenarioRelation)relationList.get(i);
                Scenario scenario=new Scenario();
                scenario.setScenarioId(relation.getScenarioId());
                scenario.setScenarioName(relation.getScenarioName());
                sourceList.add(scenario);
            }
        }
        
        StringBuffer buffer= HtmlClientComponentService.getMultiSelect(sourceList, new ArrayList(), "scenarioName",Scenario.class);
        buffer.append("<table><tr><td><div id=\"sdrRelation\"></div></td></tr></table>");
        
        returnMessage=buffer.toString();
    }
    
    public void showRelationMatrix() throws EasyJException {
        
        //按照场景的编号，得到场景和数据之间的关系
        
        //得到用户所选的场景
        SelectCommand scmd = DAOFactory.getSelectCommand(ScenarioRoleRelation.class);
        String scenarios=request.getParameter("scenarios");
        
        //根据所得到的场景选择场景和角色的关系
        scenarios=scenarios.substring(0,scenarios.length()-1);
        String[] scnarioArr=scenarios.split(",");
        Long[] primaryKeysLong = new Long[scnarioArr.length];
        for (int i = 0; i < scnarioArr.length; i++)
            primaryKeysLong[i] = new Long(scnarioArr[i]);
        Filter filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        Filter topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioRoleList=dp.query(scmd);
        
        //选择场景
        
        scmd = DAOFactory.getSelectCommand(Scenario.class);
        filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioList=dp.query(scmd);
        
        
        //选择场景和数据的关系
        scmd = DAOFactory.getSelectCommand(ScenarioDataRelation.class);
        filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioDataList=dp.query(scmd);
        //下面用来得到需要的文件。
        
        //得到所有的场景，数据，角色，以及其间的关系
        StringBuffer sbuffer=new StringBuffer();
        StringBuffer dbuffer=new StringBuffer();
        StringBuffer rbuffer=new StringBuffer();
        StringBuffer srbuffer=new StringBuffer();  //场景和角色之间的
        StringBuffer sdbuffer=new StringBuffer(); //场景和数据之间的
        StringBuffer rdbuffer=new StringBuffer(); //角色和数据之间的

        int size=scenarioList.size();
        for(int i=0;i<size;i++) {
            Scenario scenario=(Scenario)scenarioList.get(i);
            String scenarioName=scenario.getScenarioName();
            if(sbuffer.indexOf(scenarioName)<0) {
                sbuffer.append(scenarioName);
                sbuffer.append("\n");
            }
        }
        
        size=scenarioRoleList.size();
        for(int i=0;i<size;i++) {
            ScenarioRoleRelation relation=(ScenarioRoleRelation)scenarioRoleList.get(i);
            String scenarioName=relation.getScenarioName();
            if(sbuffer.indexOf(scenarioName)<0) {
                sbuffer.append(scenarioName);
                sbuffer.append("\n");
            }
            
            String roleName=relation.getRoleName();
            if(rbuffer.indexOf(roleName) <0) {
                rbuffer.append(roleName);
                rbuffer.append("\n");
            }
            
            String roleScenario=roleName+","+scenarioName;
            
            if(srbuffer.indexOf(roleScenario)<0) {
                srbuffer.append(relation.getScenarioName()).append(",");
                srbuffer.append(relation.getRoleName());
                srbuffer.append(",");
                srbuffer.append("solid");
                srbuffer.append("\n");
            }
        }
        
        size=scenarioDataList.size();
        HashMap<String,String> dataScenario=new HashMap<String,String>();
        
        
        
        //找场景之间的数据关系，因为还没有数据传递关系的抽取，所以这里把所有的关系输出，将来需要修改
        for(int i=0;i<size;i++) {
            ScenarioDataRelation relation=(ScenarioDataRelation)scenarioDataList.get(i);
            String data=relation.getDataName();
            if (dbuffer.indexOf(data) < 0)
                dbuffer.append(data).append("\n");
            String scenarioName = relation.getScenarioName();
            sdbuffer.append(scenarioName).append(",").append(data).append(",").append("solid\n");
        }
        

        
        String fileName = userId+"matrix.txt";

        sbuffer.append("!end of scenario\n").append(rbuffer).append("!end of actor\n").append(dbuffer).append("!end of data\n");
        sbuffer.append(srbuffer).append("!end of saRelation\n");
        sbuffer.append("!end of adRelation\n");
        sbuffer.append(sdbuffer).append("!end of sdRelation");
        String result="";
//        try {
//            result = new String(sbuffer.toString().getBytes("GBK"),
//                    "ISO-8859-1");
            result=sbuffer.toString();
            String absolutFileName=application.getRealPath(fileName);
            File file=new File(absolutFileName);
            FileOutputStream os=null;
            try {
                os=new FileOutputStream(file);
                os.write(result.getBytes());
                os.flush();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        
        
        returnMessage = "fileName=" + fileName + "<message>";
    }
    
    
    
    public void showRelationDiagram() throws EasyJException {
        
        //按照场景的编号，得到场景和数据之间的关系
        
        //得到用户所选的场景
        SelectCommand scmd = DAOFactory.getSelectCommand(ScenarioRoleRelation.class);
        String scenarios=request.getParameter("scenarios");
        
        //根据所得到的场景选择场景和角色的关系
        scenarios=scenarios.substring(0,scenarios.length()-1);
        String[] scnarioArr=scenarios.split(",");
        Long[] primaryKeysLong = new Long[scnarioArr.length];
        for (int i = 0; i < scnarioArr.length; i++)
            primaryKeysLong[i] = new Long(scnarioArr[i]);
        Filter filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        Filter topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioRoleList=dp.query(scmd);
        
        //选择场景
        
        scmd = DAOFactory.getSelectCommand(Scenario.class);
        filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioList=dp.query(scmd);
        
        
        //选择场景和数据的关系
        scmd = DAOFactory.getSelectCommand(ScenarioDataRelation.class);
        filter=DAOFactory.getFilter("scenarioId",SQLOperator.IN,primaryKeysLong);
        topFilter=DAOFactory.getFilter();
        topFilter.addFilter(filter);
        scmd.setFilter(topFilter);
        ArrayList scenarioDataList=dp.query(scmd);
        //下面用来得到需要的文件。
        
        //得到所有的场景，数据，角色，以及其间的关系
        StringBuffer sbuffer=new StringBuffer();
        StringBuffer dbuffer=new StringBuffer();
        StringBuffer rbuffer=new StringBuffer();
        StringBuffer srbuffer=new StringBuffer();  //场景和角色之间的
        StringBuffer ssdbuffer=new StringBuffer(); //场景之间共有的数据

        int size=scenarioList.size();
        for(int i=0;i<size;i++) {
            Scenario scenario=(Scenario)scenarioList.get(i);
            String scenarioName=scenario.getScenarioName();
            if(sbuffer.indexOf(scenarioName)<0) {
                sbuffer.append(scenarioName);
                sbuffer.append("\n");
            }
        }
        
        size=scenarioRoleList.size();
        for(int i=0;i<size;i++) {
            ScenarioRoleRelation relation=(ScenarioRoleRelation)scenarioRoleList.get(i);
            String scenarioName=relation.getScenarioName();
            if(sbuffer.indexOf(scenarioName)<0) {
                sbuffer.append(scenarioName);
                sbuffer.append("\n");
            }
            
            String roleName=relation.getRoleName();
            if(rbuffer.indexOf(roleName) <0) {
                rbuffer.append(roleName);
                rbuffer.append("\n");
            }
            
            String roleScenario=roleName+","+scenarioName;
            
            if(srbuffer.indexOf(roleScenario)<0) {
                srbuffer.append(relation.getRoleName());
                srbuffer.append(",");
                srbuffer.append(relation.getScenarioName());
                srbuffer.append("\n");
            }
        }
        
        size=scenarioDataList.size();
        HashMap<String,String> dataScenario=new HashMap<String,String>();
        
        //找场景之间的数据关系，因为还没有数据传递关系的抽取，所以这里把所有的关系输出，将来需要修改
        for(int i=0;i<size;i++) {
            ScenarioDataRelation relation=(ScenarioDataRelation)scenarioDataList.get(i);
            String data=relation.getDataName();
            String scenarioRelations=(String)dataScenario.get(data);
            if(scenarioRelations==null) {
                dataScenario.put(data, relation.getScenarioName());
            } else {
                dataScenario.put(data, scenarioRelations+","+relation.getScenarioName());
            }
        }
        
        for(Entry<String, String> entry: dataScenario.entrySet()) {
            String scenarioRelations=entry.getValue();
            String[] scenariosArr = scenarioRelations.split(",");
            //只有一个场景
            if(scenariosArr.length<3)
                continue;
            scenarioRelations = scenariosArr[0]+","+scenariosArr[1]+",";
            ssdbuffer.append(scenarioRelations);
            ssdbuffer.append(",");
            ssdbuffer.append("Data：");
            ssdbuffer.append(entry.getKey());
            ssdbuffer.append("\n");
        }
        
        String fileName = userId+"interaction.txt";

        sbuffer.append("!end of scenario\n").append(rbuffer).append("!end of actor\n").append(srbuffer).append("!end of actorNscenarioInteraction\n");
        sbuffer.append(ssdbuffer).append("!end of scenarioRelation");
        String result="";
//        try {
//            result = new String(sbuffer.toString().getBytes("GBK"),
//                    "ISO-8859-1");
            result=sbuffer.toString();
            String absolutFileName=application.getRealPath(fileName);
            File file=new File(absolutFileName);
            FileOutputStream os=null;
            try {
                os=new FileOutputStream(file);
                os.write(result.getBytes());
                os.flush();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        
        
        returnMessage = "fileName=" + fileName + "<message>";
    }
    
    public void addRemark() throws EasyJException {
        Remark remark = (Remark) object;
        remark.setCreatorId(userId);
        remark.setBuildTime(new java.sql.Date(System.currentTimeMillis()));
        remark.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
        /* todo:将来需要改成用户对应的角色 */

        remark.setRoleId(new Long(1));
        if (remark.getRemarkId() == null)
            remark = (Remark) dp.create(remark);
        else {
            remark.setUseState("Y");
            dp.update(remark);
        }
        remark = (Remark) dp.get(remark);
        StringBuffer buffer = BeanUtil.serializeObjectToClient(remark);
        buffer.append("<message>保存成功");
        returnMessage = buffer.toString();
    }

    
    private void updateApplyState(int state) throws EasyJException {
        dp.setContext(null);
        UserScenarioRelation relation = (UserScenarioRelation) object;
        relation = (UserScenarioRelation) dp.get(relation);
        Long userScenarioState = DictionaryProxy.getIdByRelatedValue(
                "PROJECT_APPLY_STATE", new Long(state));
        relation.setApplyState(userScenarioState);
        dp.update(relation);
    }

    public void confirmApply() throws EasyJException {
        updateApplyState(DictionaryConstant.ACCEPTED);
    }

    public void rejectApply() throws EasyJException {
        updateApplyState(DictionaryConstant.REJECTED);
    }

    public void cancel() throws EasyJException {
        updateApplyState(DictionaryConstant.CANCELED);
    }
    
    // public void addRole() throws EasyJException
    // {
    // Scenario scenario=(Scenario)object;
    // //得到用户想要添加什么类型的角色，是参与者还是其他。
    // String type=request.getParameter("type");
    // ScenarioRoleRelation sr=new ScenarioRoleRelation();
    // String strRoleId=request.getParameter("roleId");
    // if(GenericValidator.isBlankOrNull(strRoleId)){
    // returnMessage="请选择角色";
    // return;
    // }
    // Long roleId= new Long(strRoleId);
    //	  
    // //看看选择的角色是否已经在此场景中存在
    // ScenarioRoleRelation srRelation=new ScenarioRoleRelation();
    // srRelation.setRoleId(roleId);
    // srRelation.setScenarioId(scenario.getScenarioId());
    // try {
    // dp.get(srRelation);
    // returnMessage="角色已存在";
    // return;
    // } catch(Exception e) {
    // //说明数据不存在，可以继续执行。
    // }
    //	  
    // sr.setRoleId(roleId);
    // sr.setScenarioId(scenario.getScenarioId());
    // sr.setRoleType(type);
    // dp.create(sr);
    // returnMessage="添加成功";
    // }
    //
    // public void addData() throws EasyJException
    // {
    // Scenario scenario=(Scenario)object;
    // ScenarioDataRelation sd=new ScenarioDataRelation();
    // String strDataId=request.getParameter("dataId");
    // if(GenericValidator.isBlankOrNull(strDataId)){
    // returnMessage="请选择数据";
    // return;
    // }
    // Long dataId= new Long(strDataId);
    //	  
    // //看看选择的角色是否已经在此场景中存在
    // ScenarioDataRelation sdRelation=new ScenarioDataRelation();
    // sdRelation.setDataId(dataId);
    // sdRelation.setScenarioId(scenario.getScenarioId());
    // try {
    // dp.get(sdRelation);
    // returnMessage="数据已存在";
    // return;
    // }catch(Exception e) {
    // //说明数据不存在，可以继续执行。
    // }
    //	  
    // sd.setDataId(dataId);
    // sd.setScenarioId(scenario.getScenarioId());
    // dp.create(sd);
    // returnMessage="添加成功";
    // }
    //  
    public void saveScenarioName() throws EasyJException {
        Scenario scenario = (Scenario) object;
        Scenario temp = new Scenario();
        temp.setScenarioId(scenario.getScenarioId());
        temp = (Scenario) dp.get(temp);
        temp.setScenarioName(scenario.getScenarioName());
        dp.update(temp);
        returnMessage = "保存成功";
    }

    public void addProblem() throws EasyJException {
        Problem problem = (Problem) object;
        problem.setCreatorId(userId);
        problem.setBuildTime(new java.sql.Date(System.currentTimeMillis()));
        problem.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
        /* todo:将来需要改成用户对应的角色 */
        problem.setRoleId(new Long(1));
        problem.setStatusId(new Integer(0));
        problem.setProblemAward(new Short((short) 0));
        problem.setVotingNum(new Long(0));
        if (problem.getProblemId() == null)
            problem = (Problem) dp.create(problem);
        else {
            problem.setUseState("Y");
            dp.update(problem);
        }

        String problemContent = request.getParameter("problemContent");
        // 增加完problem之后，需要增加对应的problem_version
        ProblemVersion version = new ProblemVersion();
        version.setCreatorId(userId);
        version.setProblemContent(problemContent);
        version.setProblemId(problem.getProblemId());
        version.setProblemVersionId(new Long(1));
        dp.update(version);
        returnMessage = "problemId=" + problem.getProblemId() + "<message>保存成功";
    }

    /**
     * 用来通知提出remark的人来取消remark。
     * 
     * @throws EasyJException
     */
    public void resolve() throws EasyJException {
        Message message = (Message) object;
        message.setCreatorId(userId);
        message.setIsRead("N");
        dp.create(message);
    }

    public void revokeRemark() throws EasyJException {
        Remark remark = (Remark) object;
        dp.delete(remark);
    }

    public void saveDescriptionVersion() throws EasyJException {
        Description description = (Description) object;
        description.setRoleId(userCache.getUser().getUserId());
        Scenario scenario = new Scenario();
        scenario.setScenarioId(description.getScenarioId());
        String datas = "", participants = "", observers = "", communicators = "", descriptions = "";
        scenario = (Scenario) cdp.get(scenario);
        ArrayList dataList = scenario.getDatas();
        ArrayList roleList = scenario.getRoles();
        ArrayList descriptionList = scenario.getDescriptions();
        for (int i = 0; i < dataList.size(); i++) {
            ScenarioDataRelation data = (ScenarioDataRelation) dataList.get(i);
            if (i != dataList.size() - 1)
                datas += data.getDataId() + ",";
            else
                datas += data.getDataId();
        }
        for (int i = 0; i < descriptionList.size(); i++) {
            Description descriptionVersion = (Description) descriptionList
                    .get(i);
            if (i != dataList.size() - 1)
                descriptions += descriptionVersion.getDescriptionId() + ",";
            else
                descriptions += descriptionVersion.getDescriptionId();
        }

        for (int i = 0; i < roleList.size(); i++) {
            ScenarioRoleRelation role = (ScenarioRoleRelation) roleList.get(i);
            if ("participant".equals(role.getRoleType()))
                participants += role.getRoleId() + ",";
            if ("observer".equals(role.getRoleType()))
                observers += role.getRoleId() + ",";
            if ("communicator".equals(role.getRoleType()))
                communicators += role.getRoleId() + ",";
        }

        ScenarioVersion version = new ScenarioVersion();
        version.setScenarioId(description.getScenarioId());
        version.setCommunicators(communicators);
        version.setParticipants(participants);
        version.setObservers(observers);
        version.setDatas(datas);
        version.setCreatorId(userId);
        version.setDescriptions(descriptions);
        version.setBuildTime(new java.sql.Date(System.currentTimeMillis()));
        version.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
        version.setModifyMark("description");

        dp.create(version);

        String changeContent = description.getDescriptionContent();
        String oriDescriptionContent = request.getParameter("cleanContent");

        description.setDescriptionContent(oriDescriptionContent);
        if (description.getDescriptionId() == null) {
            dp.create(description);
            returnMessage = "descriptionId=" + description.getDescriptionId()
                    + "<message>保存成功";
        } else {
            dp.update(description);
            returnMessage = "保存成功";
        }
        DescriptionVersion descriptionVersion = new DescriptionVersion();
        descriptionVersion.setBuildTime(new java.sql.Date(System
                .currentTimeMillis()));
        descriptionVersion.setUpdateTime(new java.sql.Date(System
                .currentTimeMillis()));
        descriptionVersion.setCreatorId(userId);
        descriptionVersion.setScenarioVersionId(version.getScenarioVersionId());
        descriptionVersion.setDescriptionId(description.getDescriptionId());
        descriptionVersion.setDescriptionVersionContent(changeContent);
        dp.create(descriptionVersion);
    }

    public void edit() throws EasyJException {
        Object primaryKey = BeanUtil.getPrimaryKeyValue(object);
        /* primaryKey为空则代表新增，否则代表编辑 */
        System.out.println("primaryKey is:" + primaryKey);
        if (primaryKey != null) {
            object = cdp.get(object);
            this.returnPath = "/WEB-INF/AjaxScenario.jsp";
        } else
            this.returnPath = Globals.SINGLE_DATA_EDIT_RETURN_PARTH;
        request.setAttribute(Globals.OBJECT, object);
    }

    public void apply() throws EasyJException {
        Scenario scenario = (Scenario) object;
        UserScenarioRelation relation = new UserScenarioRelation();
        relation.setScenarioId((scenario.getScenarioId()));
        relation.setUserId(userId);
        Long applyState = DictionaryProxy.getIdByRelatedValue(
                "PROJECT_APPLY_STATE", new Long(DictionaryConstant.APPLYING));
        relation.setApplyState(applyState);
        dp.create(relation);
    }

    public void paint() {
        try {
            Scenario scenario = (Scenario) cdp.get(object);
            // 在这里得到description，需要注意的是，当对参与场景的用户进行调整的时候，并没有把删除掉的角色的描述删掉，
            // 所以，在这里还是会碰到虽然没有参与此场景的描述，但依然会有描述的情况。 所以要把这些过滤掉。
            ArrayList descriptions = scenario.getDescriptions();
            StringBuffer buffer = new StringBuffer();
            ArrayList roles = scenario.getRoles();
            int roleSize = scenario.getRoles().size();
            // 找出所有的参与者
            String participants = ",";
            for (int j = 0; j < roleSize; j++) {
                ScenarioRoleRelation role = (ScenarioRoleRelation) roles.get(j);
                if ("participant".equals(role.getRoleType())) {
                    participants = participants + role.getRoleId() + ",";
                }
            }
            for (int i = 0; i < descriptions.size(); i++) {
                Description description = (Description) descriptions.get(i);
                if (participants.indexOf("," + description.getRoleId() + ",") >= 0) {
                    String content = HtmlTransformer
                            .getGraphSourceString(description
                                    .getDescriptionContent());
                    content = content.replaceAll("\u00a0", " ");
                    buffer.append(description.getRoleName() + "\n" + content
                            + "\n");
                    buffer.append(HtmlTransformer.SWIMLANE_SEP + "\n");
                }
            }
            System.out.println(buffer.toString());
            String fileName = ".txt";
            String nodeFileName = userId + "node" + scenario.getScenarioId()
                    + fileName;
            String edgeFileName = userId + "edge" + scenario.getScenarioId()
                    + fileName;

            String test = new String(buffer.toString().getBytes("GBK"),
                    "ISO-8859-1");
            Myparser.parseString(test, application.getRealPath(nodeFileName),
                    application.getRealPath(edgeFileName));

            AppletSize appletSize = new AppletSize(application
                    .getRealPath(nodeFileName), application
                    .getRealPath(edgeFileName));

            int height = appletSize.getAppletHeight();

            returnMessage = "nodeFile=" + nodeFileName + "&edgeFile="
                    + edgeFileName + "&height=" + height + "<message>";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newObject() throws EasyJException {
        Scenario scenario = (Scenario) object;
        scenario.setCstId(user.getCstId());
        scenario.setCreatorId(userId);
        super.newObject();
    }

    public void getGraphText() throws EasyJException {
        Scenario scenario = (Scenario) object;

    }

    public void update() throws EasyJException {

    }

    public void query() throws EasyJException {
        super.query();
    }

}
