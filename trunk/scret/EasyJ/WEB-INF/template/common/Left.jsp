<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<div id="columnLeft">
  <easyJ:TreeTag className="easyJ.system.data.Module" selectMode="2">
  	<easyJ:TreeConditionTag property="moduleType" value=",scenario,"/>
  </easyJ:TreeTag>
</div>
