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