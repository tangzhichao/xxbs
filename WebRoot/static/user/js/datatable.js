
var Datatables = function () {
	alert(listQuery_url);
    var e = function () {
        var e = $(data_table);
        e.dataTable({
                "processing": true,
                "serverSide": true,
                'bFilter': true,
                scrollY: 400,
                lengthMenu: [[10,20, 50, 100, 500], [10,20, 50, 100, 500]],
                ordering:true,
                orderCellsTop:true,
                order: [[1, "desc"]],
                //'bStateSave':true,
                "fnServerParams": function (aoData) {
                	
                	searchValidate();
                	
                    var params = $(search_form).formToArray();
                    //params.push({"name":"partId","value":$("#partId").val()+""})
                    //params.push({"name":"psState","value":$("#psState").val()+""})
                    alert(params.toString());
                    alert(JSON.stringify(params));
                    $.merge(aoData,params);
                },
                "sAjaxSource": listQuery_url,
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
            alert("init");
        }
    };
}();
jQuery(document).ready(function () {
    Datatables.init();
});
