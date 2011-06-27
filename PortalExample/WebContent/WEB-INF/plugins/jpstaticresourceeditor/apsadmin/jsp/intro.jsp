<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1>jpstaticresourceeditor<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">
	<p>
		This is intro.jsp of jpstaticresourceeditor.
	</p>
	
	<p>
	<a href="<s:url action="list" />">go</a>
	</p>

</div>

