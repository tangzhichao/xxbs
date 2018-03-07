<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style>
td.lab {
	text-align: right;
}

select.form-control {
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}
</style>

<div class="modal fade" id="add_user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="width: 800px;">
			<form id="addUserForm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close" req>
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">添加用户</h4>
			</div>
			<div class="modal-body">
				<table class="table">
					<tr>
						<td class="lab">角色：</td>
						<td><select name="roleName" class="form-control" required="required">
								<option value="">请选择</option>
								<c:forEach items="${LvMap}" var="lv">
									<option value="${lv.key}">${lv.value}</option>
								</c:forEach>
						</select></td>
						<td class="lab">姓名：</td>
						<td><input name="realName" class="form-control"  required="required"/></td>
					</tr>
					<tr>

						<td class="lab">营业厅、代理商名称：</td>
						<td><input name="sealName" class="form-control" /></td>
						<td class="lab">手机号：</td>
						<td><input name="phone" class="form-control" maxlength="11" required="required"/></td>
					</tr>
					<tr>
						<td class="lab">证件号码：</td>
						<td><input name="card" class="form-control" maxlength="18" required="required"/></td>
						<td class="lab">地市：</td>
						<td>
							<select id="city" name="city" class="form-control" required="required"  onchange="cityChange(this);">
								<option value="">请选择</option>
								<option value="全省">全省</option>
								<c:forEach items="${city}" var="city">
									<option value="${city.value}">${city.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="lab">工号：</td>
						<td><input name="workNumber" class="form-control"/></td>
						<td class="lab">销售员编码：</td>
						<td><input name="salecode" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">渠道：</td>
						<td>
							<select name="channel_Id" class="form-control" required="required">
								<option value="">请选择</option>
								<c:forEach items="${channel}" var="channel">
									<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lab">cpsId：</td>
						<td><input name="cpsId" class="form-control" required="required"/></td>
					</tr>
					<tr>
						<td class="lab">结对营业厅：</td>
						<td>
							<select id="upId" name="upId" class="form-control">
								<option value="">请选择</option>
							</select>
						</td>
						<td class="lab">上级账号：</td>
						<td>
							<select id="parentId" name="parentId" class="form-control">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="lab">网点编码：</td>
						<td><input name="branchCode" class="form-control"/></td>
						<td class="lab">市县：</td>
						<td><input name="county" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">工号姓名：</td>
						<td><input name="workName" class="form-control"/></td>
						<td class="lab">工号部门：</td>
						<td><input name="workSection" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">翼支付账号：</td>
						<td><input name="yzfAccount" class="form-control"/></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary">保存</button>
			</div>
		</form>
		</div>
	</div>
</div>


<div class="modal fade" id="update_user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="width: 800px;">
			<form id="addUserForm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close" req>
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">添加用户</h4>
			</div>
			<div class="modal-body">
				<table class="table">
					<tr>
						<td class="lab">角色：</td>
						<td><select name="roleName" class="form-control" required="required">
								<option value="">请选择</option>
								<c:forEach items="${LvMap}" var="lv">
									<option value="${lv.key}">${lv.value}</option>
								</c:forEach>
						</select></td>
						<td class="lab">姓名：</td>
						<td><input name="realName" class="form-control"  required="required"/></td>
					</tr>
					<tr>

						<td class="lab">营业厅、代理商名称：</td>
						<td><input name="sealName" class="form-control" /></td>
						<td class="lab">手机号：</td>
						<td><input name="phone" class="form-control" maxlength="11" required="required"/></td>
					</tr>
					<tr>
						<td class="lab">证件号码：</td>
						<td><input name="card" class="form-control" maxlength="18" required="required"/></td>
						<td class="lab">地市：</td>
						<td>
							<select id="city" name="city" class="form-control" required="required"  onchange="cityChange(this);">
								<option value="">请选择</option>
								<option value="全省">全省</option>
								<c:forEach items="${city}" var="city">
									<option value="${city.value}">${city.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="lab">工号：</td>
						<td><input name="workNumber" class="form-control"/></td>
						<td class="lab">销售员编码：</td>
						<td><input name="salecode" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">渠道：</td>
						<td>
							<select name="channel_Id" class="form-control" required="required">
								<option value="">请选择</option>
								<c:forEach items="${channel}" var="channel">
									<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>
						</td>
						<td class="lab">cpsId：</td>
						<td><input name="cpsId" class="form-control" required="required"/></td>
					</tr>
					<tr>
						<td class="lab">结对营业厅：</td>
						<td>
							<select id="upId" name="upId" class="form-control">
								<option value="">请选择</option>
							</select>
						</td>
						<td class="lab">上级账号：</td>
						<td>
							<select id="parentId" name="parentId" class="form-control">
								<option value="">请选择</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="lab">网点编码：</td>
						<td><input name="branchCode" class="form-control"/></td>
						<td class="lab">市县：</td>
						<td><input name="county" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">工号姓名：</td>
						<td><input name="workName" class="form-control"/></td>
						<td class="lab">工号部门：</td>
						<td><input name="workSection" class="form-control"/></td>
					</tr>
					<tr>
						<td class="lab">翼支付账号：</td>
						<td><input name="yzfAccount" class="form-control"/></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary">保存</button>
			</div>
		</form>
		</div>
	</div>
</div>


<div class="modal fade" id="import_user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">导入用户</h4>
			</div>
			<form id="import_user_form" action="<%=path%>/upload/importUser.do?currentPageName=yyt" method="post" enctype="multipart/form-data" onkeydown="if(event.keyCode==13)return false;">
				<div class="modal-body">
					<div class="fileinput fileinput-new" data-provides="fileinput">
						<br /> <label>只接收xls格式，第一行为列头不作为数据。 </label> <br />
						<br /> <label>导入用户之前请一定下载模板，并仔细阅读模板里的文字，严格遵守模板要求。 </label> <br /> <br /><br />
						<!--<span class="fileinput-filename"> </span>-->
						<input id="importUserFile" name="file" type="file" value="导入"><br />
						<br />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">提交</button>
					<!-- <label type="button" class="btn btn-default" onclick="upload()">提交</label> -->
				</div>
			</form>
		</div>
	</div>
</div>
<div class="modal fade" id="exportDateSelectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">选择指定日期的用户进行导出</h4>
			</div>
			<form id="import_user_form" action="<%=path%>/upload/importUser.do?currentPageName=yyt" method="post" enctype="multipart/form-data" onkeydown="if(event.keyCode==13)return false;">
				<div class="modal-body">
					<table>
						<tr>
							<td>创建时间：</td>
							<td><input id="exportFilterStartTime" class="form-control form_date" /></td>
							<td>至：</td>
							<td><input id="exportFilterEndTime" class="form-control form_date" /></td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary" onclick="submitBatchExportQR();return false;">确定</button>
					<!-- <label type="button" class="btn btn-default" onclick="upload()">提交</label> -->
				</div>
			</form>
		</div>
	</div>
</div>