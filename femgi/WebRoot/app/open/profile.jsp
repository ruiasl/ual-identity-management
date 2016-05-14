<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="row">
	<!-- left column -->
	<div class="col-md-8">

		<!-- general form elements disabled -->
		<div class="box box-warning">
			<div class="box-header with-border">
				<h3 class="box-title"><s:text name="menu.profile.title" /></h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<form role="form">
					<!-- text input -->
					<div class="form-group">
						<label><s:text name="user.profile.id"/></label> 
						<s:textfield name="userAccountDetail.id" theme="simple" cssClass="form-control" disabled="true"/>
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.name"/></label> 
						<s:textfield name="userAccountDetail.name" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.name.placeholder')}"/>
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.email"/></label>
						<!-- <div class="input-group"> -->
							<%-- <span class="input-group-addon">@</span> --%> 
							<s:textfield name="userAccountDetail.email" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.email.placeholder')}"/>
						<!-- </div> -->
					</div>
					<div class="form-group">
						<label><s:text name="user.profile.phone"/></label> 
						<s:textfield name="userAccountDetail.phone" theme="simple" cssClass="form-control" placeholder="%{getText('user.profile.phone.placeholder')}"/>
						
					</div>
			
				</form>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
	<!--/.col (right) -->
	<div class="col-md-4">
		
		<div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title"><s:text name="side.bar.help" /></h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <div class="box-group" id="accordion">
                <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
                <div class="panel box box-primary">
                  <div class="box-header with-border">
                    <h4 class="box-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                        <s:text name="side.bar.profile.help.name.title"></s:text>
                      </a>
                    </h4>
                  </div>
                  <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="box-body">
                      <s:text name="side.bar.profile.help.name.text"></s:text>
                    </div>
                  </div>
                </div>
                <div class="panel box box-primary">
                  <div class="box-header with-border">
                    <h4 class="box-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                        <s:text name="side.bar.profile.help.email.title"></s:text>
                      </a>
                    </h4>
                  </div>
                  <div id="collapseTwo" class="panel-collapse collapse">
                    <div class="box-body">
                      <s:text name="side.bar.profile.help.email.text"></s:text>
                    </div>
                  </div>
                </div>
                <div class="panel box box-primary">
                  <div class="box-header with-border">
                    <h4 class="box-title">
                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                        <s:text name="side.bar.profile.help.phone.title"></s:text>
                      </a>
                    </h4>
                  </div>
                  <div id="collapseThree" class="panel-collapse collapse">
                    <div class="box-body">
                      <s:text name="side.bar.profile.help.phone.text"></s:text>
                    </div>
                  </div>
                </div>
              </div>
            </div>
	
	</div>
</div>
</div>