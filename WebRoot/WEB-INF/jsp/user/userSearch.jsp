<%@page import="com.xxbs.v1.util.web.AutoGenerater"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
        <div id="search_l" style="display:inline;">
            <div class="portlet portlet_search">
                <div class="portlet-title">
                    <div class="caption tools" style="float: left;">
                        <button type="button"
                                style="margin-left: 5px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue dropdown-toggle collapse"><!-- expand/collapse -->
                            <i class="fa"></i> 收起/展开搜索项
                        </button>
                    </div>
                    <div class="tools" style="padding: 0px">
                        <button type="button" id="btn_add_user" onclick="showAddUserDialog()"
                                style="margin-left: 10px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue">
                            <i class="fa"></i> 添加用户
                        </button>
                        <button type="button" id="btn_download_templet" onclick="downloadTemplet()"
                                style="margin-left: 10px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue">
                            <i class="fa"></i> 模板下载
                        </button>
                        <button type="button" id="btn_import_user" onclick="showImportUserDialog()"
                                style="margin-left: 10px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue">
                            <i class="fa"></i> 导入用户
                        </button>
                        <button type="button" id="btn_assign_many" onclick="exportQR()"
                                style="margin-left: 10px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue">
                            <i class="fa"></i> 生成二维码
                        </button>
                        <button type="button" id="btn_assign_many" onclick="batchExportQR()"
                                style="margin-left: 10px;padding: 4px 10px;font-size: 14px;display: inline-block;"
                                class="btn blue">
                            <i class="fa"></i> 生成该用户所有下级用户二维码
                        </button>
                	</div>
                </div>
                <div class="portlet-body" style="display: block;">
                    <form id="search_form" method="post" onkeydown="if(event.keyCode==13)return false;" onsubmit="return false;">
                        <div class="modal-body">
                            <%=AutoGenerater.getSearchConditionHtml(application,"user")%>
                        </div>
                        <div class="modal-footer">
                            <button id="clear_btn" class="btn btn-default blue" data-dismiss="modal" onclick="clearSearch();return false;" ><i class="fa fa-reply"></i> 清空</button>
                            <button id="search_btn" class="btn btn-default blue" onclick="PublicModule.search();return false"><i class="fa fa-search"></i> 搜索</button>
                        </div>
                    </form>
                </div>
            </div>            
        </div>