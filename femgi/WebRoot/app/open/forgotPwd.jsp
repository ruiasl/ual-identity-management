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
	    <p class="login-box-msg"><s:text name="login.forgot.title"/></p>
	
	    <s:form action="resetPassword" namespace="/app/open" method="post">
	      <div class="form-group has-feedback">
	        <s:textfield type="email" name="userAccountDetail.username" readonly="true" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.username.placeholder')}"/>
	        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
	        <s:textfield type="email" name="userAccountDetail.email" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.email.placeholder')}"/>
	        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
	        <s:textfield type="text" name="userAccountDetail.hintQuestion" readonly="true" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.hintQ.placeholder')}"/>
	        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
	        <s:textfield type="text" name="userAccountDetail.hintAnswer" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.hintA.placeholder')}"/>
	        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	      </div>
			<div class="row">
				<div class="col-xs-4">
					<a href="<s:url action='login' namespace='/app/open'/>" class="btn btn-default btn-block btn-flat">
						<s:text name="btn.back"/>
					</a>
				</div>
				<!-- /.col -->
				<div class="col-xs-8">

					<s:submit theme="simple"
						cssClass="btn btn-primary btn-flat pull-right"
						value="%{getText('btn.recover.pwd')}" />

				</div>
				<!-- /.col -->
			</div>
		</s:form>
	
	  </div>
	  <!-- /.login-box-body -->
	</div>