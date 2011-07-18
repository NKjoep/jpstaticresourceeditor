<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main">
	<s:if test="hasActionErrors()">
		<div class="message message_error">
		<h2><s:text name="message.title.ActionErrors" /></h2>	
			<ul>
			<s:iterator value="actionErrors">
				<li><s:property escape="false" /></li>
			</s:iterator>
			</ul>
		</div>
	</s:if>
	<s:set var="cssFoldersMap" value="%{getCssFoldersMap()}" />
	<form>
		<p>
			<label for="filterFilename" class="basic-mint-label label-search">
				<s:text name="label.search.by"/>&#32;Filename:
			</label>
			<wpsf:textfield cssClass="text" name="filterFilename" id="filterFilename"/>
		</p>
		<fieldset>
			<legend class="accordion_toggler"><s:text name="title.searchFilters" /></legend>
			<div class="accordion_element">
			<p>
				<label for="filterFolder" class="basic-mint-label">
					Folder:
				</label>
				<wpsf:select id="filterFolder" name="filterFolder" list="%{#cssFoldersMap}" />
			</p>
			<p class="noscreen">
				Subfolders:
			</p>
			<ul class="radiocheck noBullet">
				<li>
					<wpsf:radio name="filterSubfolderType" value="none" id="filter-sub-1" />
					<label for="filter-sub-1">Hide files in subfolders</label>
				</li>
				<li>
					<wpsf:radio name="filterSubfolderType" value="group" id="filter-sub-2" />
					<label for="filter-sub-2">Show files</label>
				</li>
				<li>
					<wpsf:radio name="filterSubfolderType" value="divide" id="filter-sub-3" />
					<label for="filter-sub-3">Show subfolder and files</label>
				</li>
			</ul>
				
				
			</div>
		</fieldset>
		
	</form>
	
	<div class="subsection-light">
		<p>
			<s:text name="info.resources.list" />
		</p>
		<s:iterator value="#cssFoldersMap" var="folder">
			<s:set var="list" value='%{getCssFiles(#folder)}' />
			<table class="generic">
				<caption><span><s:property value="#folder"/></span></caption>
				<tr>
					<th><s:text name="label.file.name" /></th>
					<th class="icon">&ndash;</th> 
				</tr>
				<s:iterator value="#list" var="file">
					<s:url action="edit" var="editUrl"><s:param name="file" value="#file.path" /></s:url>
					<s:url action="trash" var="trashUrl"><s:param name="file" value="#file.path" /></s:url>
					<tr>
						<td>
							<a href="<s:property value="#editUrl" />" title="<s:text name="label.edit" />&#32;<s:property value="#file.path" />">
							<s:property value="#file.name" />
							</a>
						</td>
						<td class="icon">
							<a href="<s:property value="#trashUrl" />" title="<s:text name="label.delete" />&#32;<s:property value="#file.path" />">
								<img src="<wp:resourceURL />administration/common/img/icons/delete.png" alt="<s:text name="label.delete" />"  />
							</a>							
						</td> 
					</tr>
				</s:iterator>
			</table>
		</s:iterator>
	</div>

</div>