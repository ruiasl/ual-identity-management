<%@ include file="/app/templates/includes/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<li class="header"><s:text name="menu.title" /></li>
<li class="treeview">
	<a href="<s:url action='home' namespace='/app/restricted'/>"> <i
		class="fa fa-dashboard"></i> <span><s:text name="menu.init.title" /></span>
	</a>
	<a href="#"> <i
		class="fa fa-user"></i> <span><s:text name="menu.profile.title" /></span> <i
		class="fa fa-angle-left pull-right"></i>
	</a>
	<ul class="treeview-menu">
		<li>>
			<a href="<s:url action='show' namespace='/app/restricted/profile'/>">
				<i class="fa fa-circle-o"></i><s:text name="menu.profile.consult.title" /></a>
		</li>
		<li>
			<a href="<s:url action='showSecProfile' namespace='/app/restricted/profile'/>">
				<i class="fa fa-circle-o"></i><s:text name="menu.security.profile.consult.title" /></a>
		</li>
	</ul>
</li>
