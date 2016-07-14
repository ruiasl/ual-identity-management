<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="row">
	<!-- left column -->
	<div class="col-md-8">

		<!-- general form elements disabled -->
		<div class="box box-warning">
			<div class="box-header with-border">
				<h3 class="box-title"><s:text name="menu.security.profile.consult.title" /></h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<s:form action="updateSecProfile" namespace="/app/restricted/profile" method="post">
					
					<%@ include file="/app/templates/includes/messages.jsp"%>
					
					<!-- text input -->
					<div class="form-group">
						<label><s:text name="user.profile.id"/></label> 
						<s:textfield name="userAccountDetail.id" theme="simple" cssClass="form-control" readonly="true"/>
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.old.password"/></label>
						<s:textfield type="password" name="oldPassword" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.old.password.placeholder')}"/>
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.new.password"/></label>
						<s:textfield type="password" name="newPassword" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.new.password.placeholder')}"/>
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.confirm.password"/></label>
						<s:textfield type="password" name="confirmPassword" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.confirm.password.placeholder')}"/>
					</div>
					
					<s:submit theme="simple" cssClass="btn btn-primary" value="%{getText('btn.change')}" />
					
				</s:form>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
	<!--/.col (right) -->
	<div class="col-md-4">
		
		<div class="box box-solid">
			<div class="box-header with-border">
				<h3 class="box-title">
					<s:text name="side.bar.help" />
				</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="box-group" id="accordion">
					<!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne"> <s:text
										name="side.bar.sec.profile.help.password.title"></s:text>
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="box-body">
								<s:text name="side.bar.sec.profile.help.password.text"></s:text>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
</div>
</div>