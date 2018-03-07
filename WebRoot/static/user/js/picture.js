/*function downloadFile(options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
};*/

/*function getFileName(fileName){
    indexOf = fileName.lastIndexOf("img=");
    indexOf2 = fileName.lastIndexOf(".");
    if (indexOf != -1 && indexOf2!=-1){
        return fileName.substring(indexOf+4,indexOf2);
    }else{
        return fileName
    }
}
function getFilePostfix(fileName){
    indexOf = fileName.lastIndexOf(".");
    if (indexOf != -1){
        return fileName.substring(indexOf);
    }else{
        return ".png"
    }
}*/

function getLastStr(str,c){
    if(typeof(str) == "string" && str!=""){
        var ss=str.split(c);
        var lastStr=ss[ss.length-1];
        if(lastStr=="" && ss.length>1){
            lastStr=ss[ss.length-2];
        }
        return lastStr;
    }
    return str;
}

function getFirstStr(str,c){
    if(typeof(str) == "string" && str!=""){
        var ss=str.split(c);
        return ss[0];
    }
    return str;
}
function getSecondStr(str,c){
    if(typeof(str) == "string" && str!=""){
        var ss=str.split(c);
        var secondStr="";
        if(ss.length>1){
            secondStr=ss[1];
        }
        if(secondStr==""){
            secondStr=ss[0];
        }
        return secondStr;
    }
    return str;
}

function showField(obj){    
    var names="";       
    for(var name in obj){       
        names+=name+": "+obj[name]+", ";  
    }  
    alert(names);  
}

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

function getLvText(data){
	if (data=="1") {
		return "省管理员";
	}else if(data=="2") {
		return "市管理员";
	}else if(data=="3") {
		return "营业厅、代理商";
	}else if(data=="4") {
		return "一线、自有人员、分销经理";
	}else if(data=="5") {
		return "分销员";
	}
	return data;
}

function allSelected(ck){
	if($(ck).parent().attr("class")!="checked"){
		//将其他复选框全部取消选中
		$(".ck_input").attr("checked","checked");
		var selectedUserId="";
		$(".ck_input").each(function(){
		    selectedUserId+=(this.value+",");
		    $(this).parent().attr("class","checked");
		});
		var lastIndexOf=selectedUserId.lastIndexOf(",");
		if (lastIndexOf>0) {
			selectedUserId=selectedUserId.substring(0,lastIndexOf);
		}
		document.getElementById("selectedUserId").value=selectedUserId;
	}else{
		$(".ck_input").removeAttr("checked");
		$(".ck_input").each(function(){
		    $(this).parent().attr("class","");
		});
		document.getElementById("selectedUserId").value="";
	}
}
function selectedRow(ck){
	
	if($(ck).parent().attr("class")!="checked"){
		//将其他复选框全部取消选中
		$(".ck_input").removeAttr("checked");
		$(".ck_input").each(function(){
		    $(this).parent().attr("class","");
		});
		
		$(ck).attr("checked","checked");
		$(ck).parent().attr("class","checked");
		document.getElementById("selectedUserId").value=ck.value;
	}else{
		$(".ck_input").removeAttr("checked");
		$(".ck_input").each(function(){
		    $(this).parent().attr("class","");
		});
		document.getElementById("selectedUserId").value="";
	}
	if($("#allSelectedCheckbox").parent().attr("class")=="checked"){
		$("#allSelectedCheckbox").parent().attr("class","");
		$("#allSelectedCheckbox").removeAttr("checked");
	}
}
function getSelectedId(){
	return document.getElementById("selectedUserId").value;
}

function exportQR(){
	var userId=getSelectedId();
	if(userId==undefined||userId==""){
		alert("你还没有选择一个用户");
	}else{
	    window.open(rootpath+"/user/exportQR.do?id="+getSelectedId(), "_blank");
	}
}

function showExportDateSelectDialog() {
	$('#exportDateSelectModal').modal('show');
}
function submitBatchExportQR(){
	var startTime=$("#exportFilterStartTime").val();
	var endTime=$("#exportFilterEndTime").val();
    window.open(rootpath+"/user/batchExportQR.do?id="+getSelectedId()+"&startTime="+startTime+"&endTime="+endTime, "_blank");
}
function batchExportQR(){
	var userId=getSelectedId();
	if(userId==undefined||userId==""){
		alert("你还没有选择一个用户");
	}else{
		showExportDateSelectDialog();
	}
}


function showAddUserDialog(){
    $('#add_user_modal').modal('show');
}
function showUpdateUserDialog(){
	$('#update_user_modal').modal('show');
}
function showImportUserDialog(){
	$('#import_user_modal').modal('show');
}
function downloadTemplet(){
    window.open(location.protocol+"//"+location.host+rootpath+"/ALLUSERIMPMODEL.xls", "_blank");
}

function importUser(){
	 var form = document.getElementById("userImportForm");
    form.setAttribute('method','post');
    form.setAttribute('enctype','multipart/form-data');
    form.setAttribute('action',rootpath+'/upload/importUser.do?currentPageName=yyt');
    form.submit();
    
    var params = {};
    $("#import_user_form").ajaxSubmit({
        "url": rootpath+'/upload/importUser.do?currentPageName=yyt',
        "data": params,
        "type": 'POST',
        "dataType": 'json',
        "beforeSubmit": function () {
        },
        "success": function (data) {
            if (data.status == "200") {
                alert("上传成功");
                search();
            } else {
                alert('上传失败：'+data.data);
            }
            $('#file_modal').modal('hide');                     
        },
        "error": function (xhr, status, error) {
            alert("无法连接服务器或服务器内部错误：" + error);
            $('#file_modal').modal('hide');
        }
    });
}

function deleteUser(id) {
    if (!confirm("确认要删除所选记录吗？")) {
        return false;
    }
    var params = {};
    params['id'] = id;
    $.ajax({
        "url": rootpath+"/user/deleteUser.do",
        "data": params,
        "type": 'POST',
        "dataType": 'json',
        "beforeSubmit": function () {
        },
        "success": function (data) {
            alert("删除成功");
            var oTable = $('#sample_table').DataTable();
            oTable.ajax.reload(null, false);
        },
        "error": function (xhr, status, error) {
            alert("删除失败：" + error);
        }
    });
}
function detailUser(id) {
	var params = {};
	params['id'] = id;
	$.ajax({
		"url": rootpath+"/user/detailUser.do",
		"data": params,
		"type": 'POST',
		"dataType": 'json',
		"beforeSubmit": function () {
		},
		"success": function (data) {
			alert("删除成功");
			var oTable = $('#sample_table').DataTable();
			oTable.ajax.reload(null, false);
		},
		"error": function (xhr, status, error) {
			alert("删除失败：" + error);
		}
	});
}
function updateUser(id) {
	var params = {};
	params['id'] = id;
	$.ajax({
		"url": rootpath+"/user/updateUser.do",
		"data": params,
		"type": 'POST',
		"dataType": 'json',
		"beforeSubmit": function () {
		},
		"success": function (data) {
			alert("删除成功");
			var oTable = $('#sample_table').DataTable();
			oTable.ajax.reload(null, false);
		},
		"error": function (xhr, status, error) {
			alert("删除失败：" + error);
		}
	});
}

function batchTaskAssign(){
	var oTable = $('#sample_table').DataTable();
	var selectedRows = oTable.rows('.selected').data();
	if (selectedRows.length == 0) {
		alert('请点击标题选中至少一行！');
		return;
	}
	var idarr = [];
	$.each(selectedRows, function () {
		idarr.push(this.id);
	});
	var idstr = idarr.join(',');
	
	$("#taskId_assign").val(idstr);
	$('#sample_modal_assign').modal('show');
}
function batchTaskExamine(){
    var oTable = $('#sample_table').DataTable();
    var selectedRows = oTable.rows('.selected').data();
    if (selectedRows.length == 0) {
        alert('请点击标题选中至少一行！');
        return;
    }
    var idarr = [];
    $.each(selectedRows, function () {
        idarr.push(this.id);
    });
    var idstr = idarr.join(',');
    
    $("#taskId_examine").val(idstr);
    $('#sample_modal_examine').modal('show');
}
function taskAssign(taskId){
    $("#taskId_assign").val(taskId);
    $('#sample_modal_assign').modal('show');
}
function taskExamine(taskId){
    $("#taskId_examine").val(taskId);
    $('#sample_modal_examine').modal('show');
}

function downloadZip(taskId,partId){
    //var rootpath=location.protocol+"//"+location.host;
    //window.open(rootpath+"/picture/downloadZip/?taskId="+taskId+"&partId="+partId, "_blank");
    
    var rootpath=location.protocol+"//123.207.171.79:8080";
    window.open(rootpath+"/downloadTaskImages/?taskId="+taskId+"&partId="+partId, "_blank");
}

function uploadZip(taskId,partId){
    $("#taskId").val(taskId)
    $("#partId").val(partId)
    $('#file_modal').modal('show');
}

function upload(){
    var rootpath=location.protocol+"//"+location.host;
    var params = {};
    
    $("#form_file_input").ajaxSubmit({
        "url": "http://123.207.171.79:8080/uploadTaskImages/",
        "data": params,
        "type": 'POST',
        "dataType": 'json',
        "beforeSubmit": function () {
        },
        "success": function (data) {
            if (data.status == 1) {
                alert("上传成功");
                search();
            } else {
                alert('上传失败：'+data.data);
            }
            $('#file_modal').modal('hide');                     
        },
        "error": function (xhr, status, error) {
            alert("无法连接服务器或服务器内部错误：" + error);
            $('#file_modal').modal('hide');
        }
    });
    
    
}

function showUserDetail(id){
    var d=top.dialog({title:"用户详情", url:rootpath+"/user/detail.do?id="+id, width:800, height:600});
    d.show();
}

function showFailReason(taskId,partId){
    var rootpath=location.protocol+"//"+location.host;
    $.ajax({   
        url:rootpath+"/picture/taskFailReason/?taskId="+taskId+"&partId="+partId,   
        type:'get',   
        data:'',
        dataType:'json',
        async : false, //默认为true 异步   
        success:function(data){
            if(data.status==1){
                $("#textarea_reason").html(data.data);
            }else{
                $("#textarea_reason").html("查询失败");
            }
        },
        error:function(){   
              $("#textarea_reason").html("无法连接服务器或服务器内部错误");
        }
    });
    $('#sample_modal').modal('show');
}

function receiveTask(taskId,partId){
    var rootpath=location.protocol+"//"+location.host;
    $.ajax({   
        url:rootpath+"/picture/receiveTask/?taskId="+taskId+"&partId="+partId,   
        type:'get',   
        data:'',
        dataType:'json',
        async : false, //默认为true 异步   
        success:function(data){
            if(data.status==0){
                alert("领取失败!\r\n"+data.data);
            }else{
                downloadZip(taskId,partId);
                search();
            }
        },
        error:function(){   
            alert("无法连接服务器或服务器内部错误");
        }
    });
}

function submitTaskAssign(){
        
    document.getElementById("taskId_assign").setAttribute("name","taskId");
    document.getElementById("taskId_examine").removeAttribute("name");
    
    var rootpath=location.protocol+"//"+location.host;
    var params = {};
    params['partId']=$("#partId").val();
    $("#formvalidation_assign").ajaxSubmit({
        "url": rootpath+"/picture/submitTaskAssign/",
        "data": params,
        "type": 'POST',
        "dataType": 'json',
        "success": function (data) {
            if(data.status==0){
                alert("保存失败!\r\n"+data.data);
            }else{
                alert("保存成功!");
                search();
            }
            $('#sample_modal_assign').modal('hide');
        },
        "error": function (xhr, status, error) {
            alert("保存失败：" + error);
            $('#sample_modal_assign').modal('hide');
        }
    });
}
function submitTaskExamine(){
    
    document.getElementById("taskId_assign").removeAttribute("name");
    document.getElementById("taskId_examine").setAttribute("name","taskId");
    
    var rootpath=location.protocol+"//"+location.host;
    var params = {};
    params['partId']=$("#partId").val();
    params['taskId']=$("#taskId_examine").val();
    params['reason']=$("#reason").val();
    params['examine']=$('input[name="examine"]:checked').val();
    
    //目前不清楚这个地方使用ajaxSubmit时，ajaxSubmit没有去提交，但是ajax可以用，以后研究
    $.ajax({
        "url": rootpath+"/picture/submitTaskExamine/",
        "data": params,
        "type": 'POST',
        "dataType": 'json',
        "success": function (data) {
            if(data.status==0){
                alert("保存失败!\r\n"+data.data);
            }else{
                alert("保存成功!");
                search();
            }
            $('#sample_modal_examine').modal('hide');
        },
        "error":function (xhr,error) {
            alert("保存失败:"+error);
            $('#sample_modal_examine').modal('hide');
        }
    })
    
}

function clearSearch(){
//    $("#createTimeStart").val("");
//    $("#createTimeEnd").val("");
    
    $('textarea.form-filter, select.form-filter, input.form-filter', $('#form_search')).each(function () {
        $(this).val("");
     });
    $('input.form-filter[type="checkbox"]', $('#form_search')).each(function () {
        $(this).attr("checked", false);
    });
    $(".form-filter.select2").val('').trigger("change");
    
}

function search(){
    var datatable = $('#sample_table').DataTable();
    datatable.ajax.reload(null, false);
}

function resetItem(o){
    o.options.length=0;
    o.options[0]= new Option("", "");
    o.value="";
}

function categoryItemChange(s){
    var rootpath=location.protocol+"//"+location.host;
    var child=document.getElementById("child_category_id");
    if(s.value==""||s.value==undefined||s.value==null){
        resetItem(child);
        return;
    }
    $.ajax({
        "url": rootpath+"/picture/getChildCategorys/",
        "data": {"category_id":s.value},
        "type": 'POST',
        "dataType": 'json',
        "success": function (data) {
            resetItem(child);
            if(data.status==1){
                var c_data=data.data;
                for(var i=0;i<c_data.length;i++){
                    child.options[i+1] = new Option(c_data[i].category_name, c_data[i].id);  
                }
            }
        },
        "error":function (xhr,error) {
            resetItem(child);
        }
    })
}

function cityChange(s) {
	var city = $(s).val();
	//加载结对营业厅信息
	loadYyt(city);
	//加载上级账号（营业厅、代理商、一线、自有人员账号）
	loadParent(city);
}
function loadYyt(city) {
	var child=$("#addUserForm #upId")[0];
	if(city==""||city==undefined||city==null){
        resetItem(child);
        return;
    }
	$.ajax({
		type:'post',
		url:rootpath+'/user/loadYyt.do',
		data:{'city':city},
		dataType:'json',
		success:function(data){
			resetItem(child);
			child.options[0] = new Option("不结对营业厅", "");
			for(var i=0;i<data.data.length;i++){
				child.options[child.options.length] = new Option(data.data[i].sealName, data.data[i].id);
			}
		},error:function (xhr,error) {
            resetItem(child);
        }
	});
}
//加载上级账号
function loadParent(city) {
	var child=$("#addUserForm #parentId")[0];
	//alert(child);
	//alert(JSON.stringify(child));
	if(city==""||city==undefined||city==null){
        resetItem(child);
        return;
    }
	$.ajax({
		type:'post',
		url:rootpath+'/user/loadParent.do',
		data:{'city':city},
		dataType:'json',
		success:function(data){
			resetItem(child);
			child.options[0] = new Option("无上级账号", "");
			for(var i=0;i<data.data.length;i++){
				child.options[child.options.length] = new Option(data.data[i].realName, data.data[i].id);
			}
		},error:function (xhr,error) {
            resetItem(child);
        }
	});
}

function searchValidate(){
	var searchValidateRules = {};
	$.each($("#form_search .form-control"), function () {
		var attrs={};
		attrs["required"]=!$(this).attr("required")||$(this).attr("required")==undefined?false:true;
		attrs["maxlength"]=$(this).attr("maxlength");
		attrs["dateISO"]=$(this).attr("date");
		searchValidateRules[$(this).attr("name")]=attrs;
	});
	
	$("#form_search").validate({
        rules: searchValidateRules,
        debug:true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            return false;
        },
    });
}

var User = function () {
    var add = function () {
        //保存
        //表单验证
        var addUserValidateRules = {};
        $.each($("#addUserForm .form-control"), function () {
        	var attrs={};
        	attrs["required"]=!$(this).attr("required")||$(this).attr("required")==undefined?false:true;
        	attrs["maxlength"]=$(this).attr("maxlength");
        	addUserValidateRules[$(this).attr("name")]=attrs;
        });
        $("#addUserForm").validate({
            rules: addUserValidateRules,
            errorPlacement: function (error, element) {
                error.appendTo(element.parent());
            },
            submitHandler: function (form) {
                //    表单提交
                var params = {};
                $("#addUserForm").ajaxSubmit({
                    "url": rootpath+"/user/addUser.do",
                    "data": params,
                    "type": 'POST',
                    "dataType": 'json',
                    "beforeSubmit": function () {
                    },
                    "success": function (data) {
                        if (data.code == "200") {
                            alert("保存成功");
                            $('#add_user_modal').modal('hide');
                            var oTable = $('#sample_table').DataTable();
                            oTable.ajax.reload(null, false);
                        } else {
                            alert(data.msg);
                        }

                    },
                    "error": function (xhr, status, error) {
                        alert("保存失败：" + error);
                    }
                });
                return false;
            }
        });
    };
    return {
        init: function () {
        	add();
        }
    };
}();
jQuery(document).ready(function () {
    User.init();
    //cityChange($("#addUserForm #city")[0]);
});