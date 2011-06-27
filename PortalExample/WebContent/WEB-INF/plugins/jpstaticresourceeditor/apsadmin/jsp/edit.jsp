<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1>jpstaticresourceeditor<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>
<div id="main" class="jpstaticresourceeditor-administration">

	<h2>
		Edit FILE <s:property value="fileToEdit"  />
	</h2>
	
	<s:if test="hasActionErrors()">
		<div class="message message_error">
		<h3><s:text name="message.title.ActionErrors" /></h3>	
			<ul>
			<s:iterator value="actionErrors">
				<li><s:property escape="false" /></li>
			</s:iterator>
			</ul>
		</div>
	</s:if>
	<s:else>
		<s:form action="save" method="post">
			<p>	
				<wpsf:textarea id="cssContent" cssClass="cssContent" name="fileContent" cols="80" rows="20"  />
			</p>
			<p>
				<wpsf:hidden name="fileToEdit" />
				<s:submit cssClass="button" name="keepOpen" value="Save" />
				<s:submit cssClass="button" value="Save and Close" />
			</p>
		</s:form>
	</s:else>
</div>