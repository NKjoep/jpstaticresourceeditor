<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1>jpstaticresourceeditor<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">
	
	<s:set var="css" value="cssFiles"/>
	<s:if test="#css!=null&&#css.size()>0">
			<h2>
				resources/static files
			</h2>
			<ul>
				<s:iterator value="#css" var="file">
					<li>
						<s:url action="edit" var="editUrl"><s:param name="fileToEdit" value="#file.path" /></s:url>
						<a href="<s:property value="#editUrl" />">
							<s:property value="#file.path" />
						</a> 
					</li>
				</s:iterator>
			</ul>
	</s:if>
		
	<s:set var="cssPlugins" value='getCssFiles("plugins")' />
	<s:if test="#cssPlugins.size()>0">
	<div class="subsection">
		<h2>
			plugins/ files
		</h2>
		<ul>
			<s:iterator value="#cssPlugins" var="pluginCss">
				<li>
					<s:url var="cssPluginEditUrl" action="edit"><s:param name="fileToEdit" value="#pluginCss.path" /></s:url>
					<a href="<s:property value="#cssPluginEditUrl" />">
						<s:property value="#pluginCss.path"/>
					</a>
				</li>
			</s:iterator>
		</ul>
	</div>
	</s:if>

	<div class="subsection-light">
		<h2>Create New CSS File</h2>
		<s:form action="new">
			<p>
				<label for="jpresourceeditor_filename">filename</label>
				resources/static/
				<wpsf:textfield name="filename" id="jpresourceeditor_filename" />
				<wpsf:submit value="create" />
			</p>
		
		</s:form>
	</div>	

</div>