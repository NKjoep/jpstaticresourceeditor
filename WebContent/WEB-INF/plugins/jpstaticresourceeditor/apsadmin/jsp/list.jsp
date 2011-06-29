<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main">
	<p>
		<s:text name="info.resources.list" />
	</p>
	
	<s:set var="css" value="cssFiles"/>
	<s:if test="#css!=null&&#css.size()>0">
			<table class="generic">
				<caption><span><em>resources/static/css</em> files</span></caption>
				<tr>
					<th><s:text name="label.file.name" /></th>
					<th><s:text name="label.file.path" /></th>
					<th class="icon">&ndash;</th>
				</tr>
				<s:iterator value="#css" var="file">
					<s:url action="edit" var="editUrl"><s:param name="fileToEdit" value="#file.path" /></s:url>
					<tr>
						<td>
							<a href="<s:property value="#editUrl" />" title="<s:text name="label.edit" />&#32;<s:property value="#file.path" />">
							<s:property value="#file.name" />
							</a>
						</td>
						<td class="monospace">
								<s:property value="#file.path" />
						</td>
						<td class="icon">
							<a href="#todo/delete/css" title="<s:text name="label.remove" />&#32;<s:property value="#file.path" />">
								<img src="<wp:resourceURL />administration/common/img/icons/delete.png" alt="<s:text name="label.edit" />"  />
							</a>							
						</td>
					</tr>
				</s:iterator>
			</table>
	</s:if>
		
	<s:set var="cssPlugins" value='getCssFiles("plugins")' />
	<s:if test="#cssPlugins.size()>0">
		<div class="subsection">
			<table class="generic">
				<caption><span><em>plugins/</em> files</span></caption>
				<tr>
					<th><s:text name="label.file.name" /></th>
					<th><s:text name="label.file.path" /></th>
					<th class="icon">&ndash;</th>
				</tr>
				<s:iterator value="#cssPlugins" var="file">
					<s:url action="edit" var="editUrl"><s:param name="fileToEdit" value="#file.path" /></s:url>
					<tr>
						<td>
							<a href="<s:property value="#editUrl" />" title="<s:text name="label.edit" />&#32;<s:property value="#file.path" />">
							<s:property value="#file.name" />
							</a>
						</td>
						<td class="monospace">
								<s:property value="#file.path" />
						</td>
						<td class="icon">
							<a href="#todo/delete/css" title="<s:text name="label.remove" />&#32;<s:property value="#file.path" />">
								<img src="<wp:resourceURL />administration/common/img/icons/delete.png" alt="<s:text name="label.edit" />"  />
							</a>							
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</s:if>
</div>