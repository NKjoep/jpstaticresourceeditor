<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa"uri="/apsadmin-core" %>
<wp:ifauthorized permission="superuser">
	<li><a href="<s:url action="intro" namespace="/do/jpstaticresourceeditor/Resourceeditor" />" id="menu_jpstaticresourceeditor" tabindex="<wpsa:counter />"><s:text name="jpstaticresourceeditor.admin.menu" /></a></li>
</wp:ifauthorized>
