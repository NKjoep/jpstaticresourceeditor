<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<s:set var="targetNS" value="%{'/do/jpstaticresourceeditor/Resourceeditor'}" />
<h1><s:text name="jpstaticresourceeditor.name" />
<s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" />
</h1>
<div id="main">

	<h2><s:text name="css.new" /></h2>
		<s:form action="new">
			<p>
				<label for="jpresourceeditor_filename">File path</label>&#32;
				<br />resources/static/css
				<wpsf:textfield name="filename" id="jpresourceeditor_filename" />
				<wpsf:submit cssClass="button" value="%{getText('label.create')}" />
			</p>
		
		</s:form>
	<p>
		//TODO.
	</p>
</div>