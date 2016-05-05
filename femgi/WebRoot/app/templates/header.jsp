<%@ taglib prefix="s" uri="/struts-tags" %>
<a href="<s:url value='/app/open/index.jspx'/>" class="logo">
  <!-- mini logo for sidebar mini 50x50 pixels -->
  <span class="logo-mini"><s:text name="header.mgi.small" /></span>
  <!-- logo for regular state and mobile devices -->
  <span class="logo-lg"><s:text name="header.mgi.full" /></span>
</a>
<!-- Header Navbar: style can be found in header.less -->
<nav class="navbar navbar-static-top">
  <!-- Sidebar toggle button-->
  <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
    <span class="sr-only"><s:text name="header.mgi.openclose"/></span>
  </a>
  <div class="navbar-custom-menu">
    <ul class="nav navbar-nav">
      <!-- User Account: style can be found in dropdown.less -->
      <li class="dropdown user user-menu">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <img src="<s:url value='/app/img/user_default.png'/>" class="user-image" alt="">
          <span class="hidden-xs">User Name</span>
        </a>
        <ul class="dropdown-menu">
          <!-- User image -->
          <li class="user-header">
            <img src="<s:url value='/app/img/user_default.png'/>" class="img-circle" alt="User Image">

            <p>
              User Name
              <small>Mebro desde 2012</small>
            </p>
          </li>
          <!-- Menu Footer-->
          <li class="user-footer">
            <div class="pull-left">
              <a href="<s:url action='index' namespace='/app/open'/>" class="btn btn-default btn-flat"><s:text name="menu.profile.title"/></a>
            </div>
            
            <div class="pull-right">
              <a href="#" class="btn btn-default btn-flat"><s:text name="menu.signout"/></a>
            </div>
          </li>
        </ul>
      </li>
      <!-- Control Sidebar Toggle Button -->
      <li>
        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
      </li>
    </ul>
  </div>
</nav>