<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="row">
	<!-- left column -->
	<div class="col-md-8">

		<!-- general form elements disabled -->
		<div class="box box-warning">
			<div class="box-header with-border">
				<h3 class="box-title"><s:text name="menu.init.title" /></h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<form role="form">
					<%@ include file="/app/templates/includes/messages.jsp"%>
					
					<article>                      
                    	<a href="http://autonoma.pt/pt/?det=14826&amp;section=A_UAL&amp;title=A-UAL&amp;id=2914&amp;mid=&amp;mid=18"><img src="http://autonoma.pt/resources/images/universidade_autonoma/ual_home_banner.jpg" alt="Autónoma, a universidade mais antiga do país" style="margin-left:25px"></a>
                    </article>
					
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
				<h3 class="box-title">
					<s:text name="side.bar.home.help.title" />
				</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="box-group" id="accordion">
					<!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"> 
									<s:text name="side.bar.home.help.ual.title"></s:text>
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="box-body">
								<s:text name="side.bar.home.help.ual.text"></s:text>
								<p><a href="<s:text name='side.bar.home.help.ual.url'/>" target="_blank"><s:text name="side.bar.home.help.click.here"/></a></p>
							</div>
						</div>
					</div>
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"> 
									<s:text name="side.bar.home.help.elearning.title"></s:text>
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="box-body">
								<s:text name="side.bar.home.help.elearning.text"></s:text>
								<p><a href="<s:text name='side.bar.home.help.elearning.url'/>" target="_blank"><s:text name="side.bar.home.help.click.here"/></a></p>
							</div>
						</div>
					</div>
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"> 
									<s:text name="side.bar.home.help.secvirtual.title"></s:text>
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="box-body">
								<s:text name="side.bar.home.help.secvirtual.text"></s:text>
								<p><a href="<s:text name='side.bar.home.help.secvirtual.url'/>" target="_blank">
										<s:text name="side.bar.home.help.click.here"/></a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		
	</div>
</div>