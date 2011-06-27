<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<s:include value="/WEB-INF/apsadmin/jsp/common/template/defaultExtraResources.jsp" />
<link rel="stylesheet" type="text/css" href="<wp:resourceURL />plugins/jpstaticresourceeditor/administration/common/css/jpstaticresourceeditor-administration.css" />

<s:if test="#myClient == 'advanced'">
	<script src="<wp:resourceURL />administration/mint/js/codemirror/codemirror-compressed.js"></script>
	<link rel="stylesheet" href="<wp:resourceURL />administration/mint/js/codemirror/codemirror.css">
	<link rel="stylesheet" href="<wp:resourceURL />administration/mint/js/codemirror/mode/css/css.css">
	<%-- 
	--%>
	
	<script type="text/javascript">
		window.addEvent("load", function(){
				var myCodeMirror = CodeMirror.fromTextArea(document.id("cssContent"),{
						mode: "css",
						lineNumbers: true,
						gutter: true
				});
			myCodeMirror.refresh();
		});
		
		
	</script>
</s:if>