var columnConfig = [ {
	"data" : "id",
	"table" : "user",
	"showName" : "<input id=\"all_selected_checkbox\" type=\"checkbox\" onclick=\"selectedRow(this);\" />",
	"style" : "width: 5%;",
	"align" : "center"
}, {
	"data" : "phone_number",
	"showName" : "手机号",
	"style" : "width: 12%;",
	"searchType" : "text",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "email",
	"showName" : "邮箱",
	"style" : "width: 13%;",
	"searchType" : "text",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "nick_name",
	"showName" : "昵称",
	"style" : "width: 10%;",
	"searchType" : "text",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "like",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "city",
	"showName" : "所在城市",
	"style" : "width: 10%;",
	"searchType" : "browse",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2",
	"refTable" : "area",
	"refId" : "id",
	"refName" : "name",
	"refWhere" : "parent_id is null"
}, {
	"data" : "sex_name",
	"column" : "sex",
	"showName" : "性别",
	"style" : "width: 10%;",
	"searchType" : "option",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "gold",
	"showName" : "金币",
	"style" : "width: 10%;",
	"searchType" : "number",
	"searchRowIndex" : "2",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "between",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "recorded_time",
	"showName" : "创建时间",
	"style" : "width: 12%;",
	"searchType" : "date",
	"searchRowIndex" : "2",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "between",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"data" : "valid_name",
	"column" : "valid",
	"showName" : "状态",
	"style" : "width: 8%;",
	"searchType" : "option",
	"searchRowIndex" : "2",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "readonly",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "2",
	"editType" : "readonly",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "2"
}, {
	"showName" : "操作",
	"style" : "width: 10%;",
	"align" : "center"
} ];
var handleColumn=[
{
      "targets": [0],// 目标列位置，下标从0开始
      "orderable": false,//禁用排序
      "render":function(data, type, row){
      	var selectedId=getSelectedId();
      	var c="";
      	if(selectedId.indexOf(",")>0){
      		var sIds=selectedId.split(",");
      		for(var i=0;i<sIds.length;i++){
      			if(sIds[i]==data){
      				c="checked";
      				break;
      			}
      		}
      	}else{
      		if(data==selectedId){
      			c="checked";
      		}
      	}
      	return '<div class="checker"><span class="'+c+'"><input class="row_checkbox" type="checkbox" value="'+data+'" onclick="selectedRow(this);"/></span></div>';
      }
  },
{
      "targets": [9],// 目标列位置，下标从0开始
      "orderable": false,//禁用排序
      "render":function(data, type, row){
          return '<button class="btn btn-sm blue btn-outline" onclick="showUserDetail('+row.id+');return false;"><i class="fa fa-info"></i></button>'
          +'<button class="btn btn-sm blue btn-outline" onclick="showUpdateDialog('+row.id+');return false;"><i class="fa fa-edit"></i></button>'
          +'<button class="btn btn-sm red btn-outline" onclick="deleteUser('+row.id+');return false;"><i class="fa fa-times"></i></button>';
          }
      }
  ];

var TableDaDatatables = function () {
    var e = function () {
        var e = $("#sample_table");
        e.dataTable({
                "processing": true,
                "serverSide": true,
                'bFilter': true,
                scrollY: 400,
                lengthMenu: [[10,20, 50, 100, 500], [10,20, 50, 100, 500]],
                ordering:true,
                orderCellsTop:true,
                order: [[6, "desc"]],
                //'bStateSave':true,
                "fnServerParams": function (aoData) {
                	
                	searchValidate();
                	
                    var params = $("#form_search").formToArray();
                    //params.push({"name":"partId","value":$("#partId").val()+""})
                    //params.push({"name":"psState","value":$("#psState").val()+""})
                    //alert(params.toString());
                    //alert(JSON.stringify(params));
                    //alert($("#createTimeStart").val());
                    $.merge(aoData,params);
                },
                "sAjaxSource": rootpath+"/user/listQuery.do",
                "sServerMethod": "POST",
                "iDisplayStart": 0,
                "columns": columnConfig,
                "columnDefs": handleColumn,

//{#            分页类型#}
                "pagingType": "bootstrap_full_number",
                "bAutoWidth": true,
                "bSort": true,
                //"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": getDatatablesInitInfo(),
            }
        );
        
        //增加选择行功能
        $(document).on('click', 'td:nth-child(0)', function () {
            $(this).parent().toggleClass('selected');
        });
    };
    return {
        init: function () {
            jQuery().dataTable && (e());
        }
    };
}();
jQuery(document).ready(function () {
    TableDaDatatables.init();
});
