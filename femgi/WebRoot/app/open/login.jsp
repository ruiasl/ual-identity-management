<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="login-box">
	  <div class="login-logo">
	    <a href="<s:url action='login' namespace='/app/open'/>"><s:text name="header.mgi.full" /></a>
	  </div>
	  <!-- /.login-logo -->
	  <div class="login-box-body">
	    <p class="login-box-msg"><s:text name="login.message.text"/></p>
	
	    <s:form action="show" namespace="/app/open/profile" method="post">
	      <div class="form-group has-feedback">
	        <s:textfield type="email" name="userAccountDetail.email" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.email.placeholder')}"/>
	        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
	        <!-- <input type="password" class="form-control" placeholder="Password"> -->
	        <s:textfield type="password" name="userAccountDetail.password" theme="simple" cssClass="form-control" placeholder="%{getText('login.password.placeholder')}"/>
	        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	      </div>
	      <div class="row">
	        <div class="col-xs-8">
	        </div>
	        <!-- /.col -->
	        <div class="col-xs-4">
	          <s:submit theme="simple" cssClass="btn btn-primary btn-block btn-flat" value="%{getText('login.title')}"/>
	        </div>
	        <!-- /.col -->
	      </div>
	    </s:form>
	
	    <a href="<s:url action="forgotPwd" namespace="/app/open"/>"><s:text name="login.forgot.password"/></a><br>
	
	  </div>
	  <!-- /.login-box-body -->
	</div>

<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>