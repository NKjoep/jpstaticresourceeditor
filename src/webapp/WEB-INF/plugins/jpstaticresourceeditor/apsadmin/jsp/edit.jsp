<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main" class="jpstaticresourceeditor-administration">
	<h2 class="margin-bit-bottom">
		<s:if test="strutsAction==1">
			<s:text name="title.css.new" />
		</s:if>
		<s:elseif test="strutsAction==2">
			<s:text name="title.jpstaticresourceeditor.edit" />
		</s:elseif>
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
			<s:if test="strutsAction==1">
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
						<label for="cssContent" class="basic-mint-label"><s:text name="label.fileContent" /></label>
						<wpsf:textarea id="cssContent" name="fileContent" cssClass="text" cols="80" rows="25" />
					</p> 
				</fieldset>
			</s:if>
			<s:elseif test="strutsAction==2">
				<fieldset>
					<legend><em><s:property value="file"  /></em></legend>
					<p>	
						<wpsf:hidden name="file" />
						<label for="cssContent"><s:text name="label.fileContent" /></label>
						<wpsf:textarea id="cssContent" cssClass="cssContent" name="fileContent" cols="80" rows="20"  />
					</p>
				</fieldset>
			</s:elseif>
			<p class="centerText">
				<wpsf:hidden name="strutsAction" />
				<s:submit cssClass="button" name="keepOpen" value="%{getText('label.save')}" />
				<s:submit cssClass="button" name="saveAndClose" value="%{getText('label.save.and.close')}" />
			</p>
		</s:form>
	</s:else>
</div>