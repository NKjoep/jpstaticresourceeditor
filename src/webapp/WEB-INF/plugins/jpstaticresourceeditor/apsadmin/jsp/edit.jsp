<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main" class="jpstaticresourceeditor-administration">
	<h2 class="margin-bit-bottom">
		<s:text name="title.jpstaticresourceeditor.edit" />
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
			<fieldset>
				<legend><em><s:property value="file"  /></em></legend>
				<p>	
					<label for="cssContent"><s:text name="label.fileContent" /></label>
					<wpsf:textarea id="cssContent" cssClass="cssContent" name="fileContent" cols="80" rows="20"  />
				</p>
			</fieldset>
			<p class="centerText">
				<wpsf:hidden name="file" />
				<s:submit cssClass="button" name="keepOpen" value="%{getText('label.save')}" />
				<s:submit cssClass="button" value="%{getText('label.save.and.close')}" />
			</p>
		</s:form>
	</s:else>
</div>