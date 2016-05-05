<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="/app/templates/includes/taglibs.jsp"%>
<%@ page pageEncoding="UTF-8"%>

<tiles:insertAttribute name="taglibs" />

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	  	<!-- Bootstrap 3.3.6 -->

		<!-- add your meta tags here -->
		<tiles:insertAttribute name="html_head_links_css" />
		<tiles:insertAttribute name="html_head_links_js" />

</head>
<body class="hold-transition login-page">
	<tiles:insertAttribute name="area_col3" />
</body>
</html>