
function getDatatablesInitInfo(){
    return {
        "sSearch": "",
        "sProcessing": "正在加载中......",
        "sLengthMenu": "_MENU_",
        "sZeroRecords": "对不起，查询不到相关数据！",
        "sInfo": "显示第 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
        "sInfoEmpty": "显示第 0 到 0 条，共 0 条记录",
        "sInfoFiltered": "数据表中共 _MAX_ 条记录",
        "sInfoPostFix": "",
        "sUrl": "",
        "sEmptyTable": "表中无数据存在！",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上一页",
            "sNext": "下一页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 升序",
            "sSortDescending": ": 降序"
        }
    }
}

function selectedAllRow(ck){
	if($(ck).parent().attr("class")!="checked"){
		//将其他复选框全部取消选中
		$("."+row_checkbox).attr("checked","checked");
		var selected_ids="";
		$("."+row_checkbox).each(function(){
			selected_ids+=(this.value+",");
		    $(this).parent().attr("class","checked");
		});
		var lastIndexOf=selected_ids.lastIndexOf(",");
		if (lastIndexOf>0) {
			selected_ids=selected_ids.substring(0,lastIndexOf);
		}
		document.getElementById(selected_row_ids).value=selected_ids;
	}else{
		$("."+row_checkbox).removeAttr("checked");
		$("."+row_checkbox).each(function(){
		    $(this).parent().attr("class","");
		});
		document.getElementById(selected_row_ids).value="";
	}
}
function selectedRow(ck){
	if($(ck).parent().attr("class")!="checked"){
		//将其他复选框全部取消选中
		$("."+row_checkbox).removeAttr("checked");
		$("."+row_checkbox).each(function(){
		    $(this).parent().attr("class","");
		});
		$(ck).attr("checked","checked");
		$(ck).parent().attr("class","checked");
		document.getElementById(selected_row_ids).value=ck.value;
	}else{
		$("."+row_checkbox).removeAttr("checked");
		$("."+row_checkbox).each(function(){
		    $(this).parent().attr("class","");
		});
		document.getElementById(selected_row_ids).value="";
	}
	if($("#"+all_selected_checkbox).parent().attr("class")=="checked"){
		$("#"+all_selected_checkbox).parent().attr("class","");
		$("#"+all_selected_checkbox).removeAttr("checked");
	}
}
function getSelectedRowId(){
	return document.getElementById(selected_row_ids).value;
}

function showModal(m){
    $('#'+m).modal('show');
}
function hideModal(m){
	$('#'+m).modal('hide');
}
function showAddModal(){
	showModal(add_modal);
}
function showAddDialog(){
	window.open(add_url, '_blank');
}
function showUpdateModal(id){
	PublicModule.idQuery(id,function(data){
		var obj=data.data;
		alert(JSON.stringify(obj));
		for(var pro in obj){
			var value=obj[pro];
			$("#"+update_form+" #"+pro).val(value);
		}
		showModal(update_modal);
	});
}
function showUpdateDialog(id){
	window.open(update_url+"?id="+id, '_blank');
}
function showDetailModal(id){
	PublicModule.idQuery(id,function(data){
		var obj=data.data;
		alert(JSON.stringify(obj));
		for(var pro in obj){
			var value=obj[pro];
			$("#"+detail_modal+" #"+pro).val(value);
		}
		showModal(detail_modal);
	});
}
function showDetailDialog(id){
	window.open(detail_url+"?id="+id, '_blank');
}

function getRules(formid){
	var validateRules = {};
	$.each($("#"+formid+" .form-control"), function () {
		var attrs={};
		attrs["required"]=!$(this).attr("required")||$(this).attr("required")==undefined?false:true;
		attrs["number"]=!$(this).attr("number")||$(this).attr("number")==undefined?false:true;
		attrs["maxlength"]=$(this).attr("maxlength");
		attrs["dateISO"]=$(this).attr("date");
		validateRules[$(this).attr("name")]=attrs;
	});
	return validateRules;
}

function searchValidate(){
	var validateRules = getRules(search_form);
	$("#"+search_form).validate({
        rules: validateRules,
        debug:true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            return false;
        },
    });
}

add_op="add";
delete_op="delete";
update_op="update";
list_url=rootpath+"/"+module+"/list.do";
listQuery_url=rootpath+"/"+module+"/listQuery.do";
detail_url=rootpath+"/"+module+"/detail.do";
idQuery_url=rootpath+"/"+module+"/idQuery.do";
add_url=rootpath+"/"+module+"/add.do";
addAction_url=rootpath+"/"+module+"/addAction.do";
deleteAction_url=rootpath+"/"+module+"/deleteAction.do";
update_url=rootpath+"/"+module+"/update.do";
updateAction_url=rootpath+"/"+module+"/updateAction.do";
import_url=rootpath+"/"+module+"/import.do";
importAction_url=rootpath+"/"+module+"/importAction.do";
exportExcel_url=rootpath+"/"+module+"/exportExcel.do";
row_checkbox="row_checkbox";
all_selected_checkbox="all_selected_checkbox";
selected_row_ids="selected_row_ids";
//search_form="search_form";
search_form="form_search";
//data_table="data_table";
data_table="sample_table";
add_form="add_form";
add_modal="add_modal";
update_form="update_form";
update_modal="update_modal";
detail_modal="detail_modal";

function initModule(){
	
	if(isEmpty(add_op)){
		add_op="add";
	}
	if(isEmpty(delete_op)){
		delete_op="delete";
	}
	if(isEmpty(update_op)){
		update_op="update";
	}
	
	if(isEmpty(list_url)){
		list_url=rootpath+"/"+module+"/list.do";
	}
	if(isEmpty(listQuery_url)){
		listQuery_url=rootpath+"/"+module+"/listQuery.do";
	}
	if(isEmpty(detail_url)){
		detail_url=rootpath+"/"+module+"/detail.do";
	}
	if(isEmpty(idQuery_url)){
		idQuery_url=rootpath+"/"+module+"/idQuery.do";
	}
	if(isEmpty(add_url)){
		add_url=rootpath+"/"+module+"/add.do";
	}
	if(isEmpty(addAction_url)){
		addAction_url=rootpath+"/"+module+"/addAction.do";
	}
	if(isEmpty(deleteAction_url)){
		deleteAction_url=rootpath+"/"+module+"/deleteAction.do";
	}
	if(isEmpty(update_url)){
		update_url=rootpath+"/"+module+"/update.do";
	}
	if(isEmpty(updateAction_url)){
		updateAction_url=rootpath+"/"+module+"/updateAction.do";
	}
	if(isEmpty(import_url)){
		import_url=rootpath+"/"+module+"/import.do";
	}
	if(isEmpty(importAction_url)){
		importAction_url=rootpath+"/"+module+"/importAction.do";
	}
	if(isEmpty(exportExcel_url)){
		exportExcel_url=rootpath+"/"+module+"/exportExcel.do";
	}
	
	if(isEmpty(row_checkbox)){
		row_checkbox="row_checkbox";
	}
	if(isEmpty(all_selected_checkbox)){
		all_selected_checkbox="all_selected_checkbox";
	}
	if(isEmpty(selected_row_ids)){
		selected_row_ids="selected_row_ids";
	}
	if(isEmpty(search_form)){
		search_form="search_form";
	}
	if(isEmpty(data_table)){
		data_table="data_table";
	}
	if(isEmpty(add_form)){
		add_form="add_form";
	}
	if(isEmpty(add_modal)){
		add_modal="add_modal";
	}
	if(isEmpty(update_form)){
		update_form="update_form";
	}
	if(isEmpty(update_modal)){
		update_modal="update_modal";
	}
	if(isEmpty(detail_modal)){
		detail_modal="detail_modal";
	}
}

var PublicModule=(function () {
	
	var clear=function (){
		$('textarea.form-filter, select.form-filter, input.form-filter', $('#'+search_form)).each(function () {
			$(this).val("");
		});
		$('input.form-filter[type="checkbox"]', $('#'+search_form)).each(function () {
			$(this).attr("checked", false);
		});
		$(".form-filter.select2").val('').trigger("change");
	}
	
	var search=function (){//listQuery.do
		var datatable = $('#'+data_table).DataTable();
		datatable.ajax.reload(null, false);
	}
	
	var idQuery=function (id,callback) {
	    var params = {};
	    params['id'] = id;
	    $.ajax({
	        "url": idQuery_url,
	        "data": params,
	        "type": 'POST',
	        "dataType": 'json',
	        "beforeSubmit": function () {
	        },
	        "success": function (data) {
	        	callback(data);
	        },
	        "error": function (xhr, status, error) {
	            alert("查询失败：" + error);
	        }
	    });
	}
	
	var addAction=function () {
		var params = {};
		$.ajax({
			"url": addAction_url,
			"data": params,
			"type": 'POST',
			"dataType": 'json',
			"beforeSubmit": function () {
			},
			"success": function (data) {
				alert("添加成功");
				search();
			},
			"error": function (xhr, status, error) {
				alert("添加失败：" + error);
			}
		});
	}
	
	var addActionByForm=function () {
		var params = {};
		$("#"+add_form).ajaxSubmit({
        	"url": addAction_url,
            "data": params,
            "type": 'POST',
            "dataType": 'json',
            "beforeSubmit": function () {
            },
            "success": function (data) {
                if (data.code == "200") {
                    alert("添加成功");
                    hideModal(add_modal);
                    search();
                } else {
                    alert(data.msg);
                }

            },
            "error": function (xhr, status, error) {
                alert("添加失败：" + error);
            }
        });
	}
	
	var addValidateListener = function () {
        var validateRules = getRules(add_form);
        $("#"+add_form).validate({
            rules: validateRules,
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            },
            submitHandler: function (form) {
            	addActionByForm();
                return false;//取消表单的提交，使用ajax自己提交
            }
        });
    };
	
	var deleteAction=function (id,is_confirm) {
		if(!isEmpty(is_confirm)&&is_confirm){
			if (!confirm("确认要删除所选记录吗？")) {
				return false;
			}
		}
		var params = {};
		params['id'] = id;
		$.ajax({
			"url": deleteAction_url,
			"data": params,
			"type": 'POST',
			"dataType": 'json',
			"beforeSubmit": function () {
			},
			"success": function (data) {
				alert("删除成功");
				search();
			},
			"error": function (xhr, status, error) {
				alert("删除失败：" + error);
			}
		});
	}
	
	var updateAction=function (params) {
		$.ajax({
			"url": updateAction_url,
			"data": params,
			"type": 'POST',
			"dataType": 'json',
			"beforeSubmit": function () {
			},
			"success": function (data) {
				alert("更新成功");
				search();
			},
			"error": function (xhr, status, error) {
				alert("更新失败：" + error);
			}
		});
	}
	
	var updateActionByForm=function () {
		var params = {};
        $("#"+update_form).ajaxSubmit({
        	"url": updateAction_url,
            "data": params,
            "type": 'POST',
            "dataType": 'json',
            "beforeSubmit": function () {
            },
            "success": function (data) {
                if (data.code == "200") {
                    alert("修改成功");
                    hideModal(update_modal);
                    search();
                } else {
                    alert(data.msg);
                }

            },
            "error": function (xhr, status, error) {
                alert("修改失败：" + error);
            }
        });
	}
	
	var updateValidateListener = function () {
        var validateRules = getRules(update_form);
        $("#"+update_form).validate({
            rules: validateRules,
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            },
            submitHandler: function (form) {
            	updateActionByForm();
                return false;//取消表单的提交，使用ajax自己提交
            }
        });
    };
	
    return {//公开以下方法，其他没有列出的都是私有方法
        init:function(){
        	initModule();
        	addValidateListener();
        	updateValidateListener();
        },
        clear:clear,
        search:search,
        idQuery:idQuery,
        addAction:addAction,
        addActionByForm:addActionByForm,
        deleteAction:deleteAction,
        updateAction:updateAction,
        updateActionByForm:updateActionByForm,
    }
})();
$(document).ready(function () {
	PublicModule.init();
});