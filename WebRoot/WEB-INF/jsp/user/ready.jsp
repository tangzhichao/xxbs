<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<script>
jQuery(document).ready(function () {
    /*
    var search_l = $('div#search_l').html();
    $('div.dataTables_wrapper div#data_table_length').parent()
        .removeClass('col-md-6')
        .removeClass('col-sm-6')
        .addClass('col-md-12')
        .addClass('col-sm-12');
    $('div.dataTables_wrapper div#data_table_filter').parent().remove();
    $('div.dataTables_wrapper div#data_table_length').parent().append(search_l);
    $('div#search_l').remove();
    
    $.fn.select2.defaults.set("theme", "bootstrap");
    $(".select2, .select2-multiple").select2({});*/
});
</script>
<script src="<%=path %>/static/public/js/date_init.js" type="text/javascript"></script>