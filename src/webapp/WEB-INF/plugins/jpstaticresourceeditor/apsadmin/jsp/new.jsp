<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main">
	<h2 class="margin-bit-bottom"><s:text name="title.css.new" /></h2>
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
	<s:form action="saveNew">
		<fieldset>
			<legend><s:text name="label.info" /></legend>
			<p>
				<label for="jpstaticresourceeditor_filefolder" class="basic-mint-label"><s:text name="label.folder" /></label>
				<wpsf:select list="cssFoldersMap" name="folder" id="jpstaticresourceeditor_filefolder" cssClass="text" />
			</p>
			<p>
				<label for="jpresourceeditor_filename" class="basic-mint-label"><s:text name="label.file.name" /></label>
				<wpsf:textfield name="file" id="jpresourceeditor_filename" cssClass="text" />
			</p>
			<p>
				<label for="cssContent" class=""><s:text name="label.fileContent" /></label>
				<wpsf:textarea id="cssContent" name="fileContent" cssClass="text" cols="80" rows="25" />
			</p>
		</fieldset>
		<p class="centerText">
			<wpsf:submit cssClass="button" value="%{getText('label.create')}" />
		</p>
	</s:form>
</div>