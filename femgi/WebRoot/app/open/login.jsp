<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="login-box">
	<div class="login-logo">
        
        <a href="<s:url action='login' namespace='/app/open'/>">
        	<img width="75px;" src="<s:url value='/app/img/logo2015_300.jpg'/>" 
        		alt="<s:text name='login.logo.alt'/>" title="<s:text name='login.logo.alt'/>"/>
        </a>
		<a title="<s:text name='login.logo.alt'/>" href="<s:url action='login' namespace='/app/open'/>"><s:text
				name="header.mgi.full" /></a>
	</div>
	<!-- /.login-logo -->
	<div class="login-box-body">
		<p class="login-box-msg">
			<s:text name="login.message.text" />
		</p>

		<s:form action="j_spring_security_check.jspx" method="post">
			
			<%@ include file="/app/templates/includes/messages.jsp"%>

			<div class="form-group has-feedback">
				<s:textfield name="j_username" type="email"
					theme="simple" cssClass="form-control"
					placeholder="%{getText('user.profile.username.placeholder')}" />
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<!-- <input type="password" class="form-control" placeholder="Password"> -->
				<s:textfield type="password" name="j_password"
					theme="simple" cssClass="form-control"
					placeholder="%{getText('login.password.placeholder')}" />
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="row">
				<div class="col-xs-8"></div>
				<!-- /.col -->
				<div class="col-xs-4">
					<s:submit theme="simple"
						cssClass="btn btn-primary btn-block btn-flat"
						value="%{getText('login.title')}" />
				</div>
				<!-- /.col -->
			</div>
		</s:form>

		<a href="#forgotPasswordModal" data-toggle="modal" data-target="#forgotPasswordModal">
			<s:text name="login.forgot.password" /></a><br>
		
		<s:form action="recoverPassword" namespace="/app/open" method="post">
		
			<!-- Modal -->
			<div id="forgotPasswordModal" class="modal fade" role="dialog">
				<div class="modal-dialog modal-sm">
	
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title"><s:text name="login.forgot.title" /></h4>
						</div>
						<div class="modal-body">
							<div class="form-group has-feedback">
								<s:textfield name="userAccountDetail.username" theme="simple"
									cssClass="form-control" type="email"
									placeholder="%{getText('user.profile.username.placeholder')}" />
								<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal"><s:text name="btn.close" /></button>
							<s:submit theme="simple" cssClass="btn btn-primary" value="%{getText('btn.continue')}" />
						</div>
					</div>
	
				</div>
			</div>
		</s:form>
	</div>
	<!-- /.login-box-body -->
</div>