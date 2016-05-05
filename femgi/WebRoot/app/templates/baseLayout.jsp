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
<body class="skin-blue">
	<!-- Site wrapper -->
	<div class="wrapper">

		<header class="main-header"> <tiles:insertAttribute
			name="area_header" /> </header>
		<!-- =============================================== -->

		<!-- Left side column. contains the sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- Sidebar user panel -->
		<div class="user-panel">
			<tiles:insertAttribute name="area_nav" />
		</div>
		
		<!-- search form -->
		<!-- <form action="#" method="get" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control"
					placeholder="Search..."> <span class="input-group-btn">
					<button type="submit" name="search" id="search-btn"
						class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form> -->

		<!-- sidebar menu: : style can be found in sidebar.less -->
		<hr>
		<ul class="sidebar-menu">
			<tiles:insertAttribute name="menu" flush="false" />
		</ul>
		</section> </aside>
		<!-- =============================================== -->

		<!-- Right side column. Contains the navbar and content of the page -->
		<div class="content-wrapper">
			<section class="content-header">
			<h1>
				<s:property value="pageTitle" />
			</h1>
			</section>

			<!-- Main content -->
			<section class="content"> <!-- Default box -->
				<tiles:insertAttribute name="area_col3" />
			</section>
		</div>

		<footer class="main-footer"> <tiles:insertAttribute name="area_footer" /> </footer>
	</div>
</body>
</html>