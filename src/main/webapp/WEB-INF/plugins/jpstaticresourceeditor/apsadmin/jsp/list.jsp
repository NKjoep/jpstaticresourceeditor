<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main" class="jpstaticresourceeditor-administration">
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
	<s:set var="cssFoldersMap" value="cssFoldersMap" />
	<s:form action="list">
		<p>
			<label for="filterFilename" class="basic-mint-label label-search">
				<s:text name="label.search.by"/>&#32;Filename:
			</label>
			<wpsf:textfield useTabindexAutoIncrement="true" cssClass="text" name="filterFilename" id="filterFilename"/>
		</p>
		<fieldset>
			<legend class="accordion_toggler"><s:text name="title.searchFilters" /></legend>
			<div class="accordion_element">
			<p>
				<label for="filterFolder" class="basic-mint-label">
					Folder:
				</label>
				<wpsf:select useTabindexAutoIncrement="true" id="filterFolder" name="filterFolder" list="%{#cssFoldersMap}" headerKey="" headerValue="%{getText('label.all')}" />
			</p>
			<p class="noscreen">
				Subfolders:
			</p>
			<ul class="radiocheck noBullet">
				<li>
					<wpsf:radio useTabindexAutoIncrement="true" name="filterSubfolderType" value="none" id="filter-sub-1" checked="%{filterSubfolderType=='none'||filterSubfolderType==null}" />
					<label for="filter-sub-1">Hide files in subfolders //todo</label>
				</li>
				<li>
					<wpsf:radio useTabindexAutoIncrement="true" name="filterSubfolderType" value="group" id="filter-sub-2" checked="%{filterSubfolderType=='group'}" />
					<label for="filter-sub-2">Show files //todo</label>
				</li>
				<li>
					<wpsf:radio useTabindexAutoIncrement="true" name="filterSubfolderType" value="divide" id="filter-sub-3" checked="%{filterSubfolderType=='divide'}" />
					<label for="filter-sub-3">Show subfolder and files //todo</label>
				</li>
			</ul>


			</div>
		</fieldset>
		<p>
			<wpsf:submit useTabindexAutoIncrement="true" cssClass="button" value="%{getText('label.search')}" />
		</p>
	</s:form>

	<div class="subsection-light">

		<s:set var="cssList" value="cssList" />
		<s:set var="separator" value='fileSeparator' />
		<s:iterator value="#cssList" var="folder">
			<s:set var="list" value="value" />
			<s:set var="folder" value="key" />
			<s:if test="%{#list.size()>0}">
				<table class="generic">
					<caption><span>
						<%--
						<s:property value="#folder.path"/>
						--%>
						<s:set var="split" value="#folder.path.split(#separator)" />
						<s:set var="current" value="%{''}" />
						<s:iterator var="splitFold" value="#split" status="status">
							<s:set var="subPath" value="#current+#splitFold" />
							<a title="<s:text name="label.quickfilter" />&#32;<s:property value="#subPath" />" href="<s:url action="list"><s:param name="filterFolder" value="#subPath" /></s:url>"><s:property value="#splitFold" /></a><s:if test="!#status.last">&#32;/&#32;</s:if>
							<s:set var="current" value="#subPath+#separator" />
						</s:iterator>
					</span></caption>

					<tr>
						<th><s:text name="label.file.name" /></th>
						<th class="icon"><abbr title="<s:text name="label.permalink.full" />"><s:text name="label.permalink.short" /></abbr></th>
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
								<a href="<wp:resourceURL /><s:property value="#file.path" />" title="<s:text name="view.online.version" />&#32;<s:property value="#file.path" />">
									<img src="<wp:resourceURL />administration/common/img/icons/22x22/link.png" alt="<s:text name="label.permalink.full" />" />
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
			</s:if>
		</s:iterator>
	</div>

</div>