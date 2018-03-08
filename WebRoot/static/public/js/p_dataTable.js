/**
 * Created by starmerk_lzy on 16-4-15.
 */
var aoDataParam = [];
var p_dataTable = function () {
    var table;
    var submitFilter = function () {
            aoDataParam = [];
            table = $('#form_search');
            // get all typeable inputs
            $('textarea.form-filter, select.form-filter, input.form-filter:not([type="radio"],[type="checkbox"])', table).each(function () {
                setAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all checkboxes
            $('input.form-filter[type="checkbox"]:checked', table).each(function () {
                addAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all radio buttons
            $('input.form-filter[type="radio"]:checked', table).each(function () {
                setAjaxParam($(this).attr("name"), $(this).val());
            });

        },
        resetFilter = function () {
            $('textarea.form-filter, select.form-filter, input.form-filter', table).each(function () {
                $(this).val("");
            });
            $('input.form-filter[type="checkbox"]', table).each(function () {
                $(this).attr("checked", false);
            });
            $(".form-filter.select2").val('').trigger("change");
            clearAjaxParams();
        },


        setAjaxParam = function (name, value) {
            aoDataParam.push({'name': name, 'value': value});
        },


        clearAjaxParams = function (name, value) {
            aoDataParam = [];
        },

        tableSearch = function () {
            // 组合框搜索按钮
            $(document).on('click', '#form_search #table_search', function (e) {
                e.preventDefault();
                submitFilter();
                var oTable = $('#form_search').DataTable();
                oTable.ajax.reload(null, false);
            });
            $(document).on('click', '#form_search #table_research', function (e) {
                e.preventDefault();
                resetFilter();
                var oTable = $('#form_search').DataTable();
                oTable.ajax.reload(null, false);
            });
        };

    var columnInvisibleVal = function (v, table) {
        $.each(v, function () {
            var column = table.column(this.toString());
            column.visible(false);
        });
    }, columnVisibleVal = function (v, table) {
        $.each(v, function () {
            var column = table.column(this.toString());
            column.visible(true);
        });
    }, columnVisibleAll = function (table) {
        var l = table.columns()[0].length;
        for (i = 0; i < l; i++) {
            var column = table.column(i);
            column.visible(true);
        };
    }, columnVisibleInit = function (){
        var table = $('#form_search').DataTable();
            columnVisibleAll(table);
            var column_val = $('#column_visible_select').val();
            columnInvisibleVal(column_val, table);
            $('#column_visible_modal').modal('hide');
    };

    var columnVisible = function () {
        $(document).on('click', '#btn_visible', function () {
            $('#column_visible_modal').modal('show');
        });

        $(document).on('click', '.column_visible_submit', function () {
            columnVisibleInit();
        });

    }, multiSelect = function () {
        $("#column_visible_select").multiSelect();

        $('.column_visible-all').click(function () {
            $('#column_visible_select').multiSelect('select_all');
            return false;
        });
        $('.column_visible-null').click(function () {
            $('#column_visible_select').multiSelect('deselect_all');
            return false;
        });
        $('.column_visible-reset').on('click', function () {
            $('#column_visible_select').multiSelect('deselect_all');
            var elem_list = $(this).attr('elem').split(',');
            $('#column_visible_select').multiSelect('select', elem_list);
            return false;
        });
    };

    // 通过checkbox，获取所选择的相关数据。
    var checkBoxVal = function () {
            // 获取被选中checkbox 的 value   value一般就是对应数据行id的值。
            var boxs = $('#form_search .checkboxes');
            var idList = [];
            boxs.each(function () {
                if ($(this).prop('checked')) {
                    idList.push($(this).val());
                }
            });
            return idList;
        },
        checkBoxRow = function () {
            // 获取被选的行数据，返回列表。
            var boxs = $('#form_search .checkboxes');
            var dataRows = [];
            var oTable = $('#form_search').DataTable();
            boxs.each(function () {
                if ($(this).prop('checked')) {
                    var datarow = oTable.row($(this).parents('tr:first')).data();
                    dataRows.push(datarow);
                }
            });
            return dataRows;
        },
        checkBoxGroup = function () {
            // 将全选box更新为 未选
            var boxGroup = $('table thead tr .group-checkable:first');
            boxGroup.prop('checked', !1);
            $.uniform.update(boxGroup);
        };

    var checkBoxTitle80 = function () {
            // 获取被选中checkbox,找出title长度大于80的，主要用于提醒用户修改title，避免上架失败。。
            var boxs = $('#form_search .checkboxes');
            var titleList = [];
            var oTable = $('#form_search').DataTable();
            boxs.each(function () {
                if ($(this).prop('checked')) {
                    var datarow = oTable.row($(this).parents('tr:first')).data();
                    var title = datarow.title;
                    if (title.length > 80){
                        titleList.push(datarow.asin);
                    }
                }
            });
            return titleList;
        };

    var getDataRow = function (t) {
        // 获取datatable中行数据
        var oTable = $('#form_search').DataTable();
        var datarow = oTable.row(t.parents('tr:first')).data();
        return datarow;
    };

    var getTableRow = function (t) {
        // 获取datatable中行数据
        var oTable = $('#form_search').DataTable();
        var tablerow = oTable.row(t);
        return tablerow;
    };

    var url_have = function (arr) {
        // 判断url是否包含能判断菜单的关键字符串
        var h = location.pathname;
        var arr = arr;

        var ret = 0;
        $.each(arr, function () {
            if (h.indexOf(this) >= 0){
                ret = 1;
                return
            }
        });

        return ret
    };

    var menu_url_config = {
        'supplier': [
            "/supplier/showSupplierManage/"
        ],
        'edit': [   //编辑
            "/edit/showEditTaskCategory/",   //编辑类别页
            "/edit/showMyEditTask/"        //编辑任务管理页
        ],
        'develop': [    //开发
            "/kaifa/showDevelopProductCategory/",
            "/kaifa/showKaifaProduct/",
            "/kaifa/showSupplier/",
            "/kaifa/showKaifaNeedEditProduct/",
            "/kaifa/showAlreadyDevelopedProduct/",
            "/kaifa/showPushProduct/",
            "/kaifa/showMyDevelopment/",
            "/kaifa/showAlreadyPushProduct/",
            "/kaifa/showPSLabel/",
            "/kaifa/showPushPro/"
        ],
        'system': [ //系统管理
            "/system/showDepartment/",          //部门管理
            "/system/showDepartmentRole/",      //部门角色管理
            "/system/showSysUser/",             //用户管理
            "/system/showJurisdictionManage/1/",  //角色权限管理
            "/system/showJurisdictionManage/2/"     //用户权限管理
        ],
        'picture': [    //美工
            "/picture/taskCategory/"        //美工分类页
        ],
        'department':[
            "/system/showMyDepartmentUser/",
            "/system/showMyDepartmentJurisdictionManage/"
        ]
    };

    var menuActive = function () {
        // 菜单激活状态
        if (url_have(menu_url_config['supplier'])){
            $('li#nav-1-supplier').addClass('active');
        }else if (url_have(menu_url_config['edit'])){
            $('li#nav-1-edit').addClass('active');
        }else if(url_have(menu_url_config['develop'])){
            $('li#nav-1-develop').addClass('active');
        }else if (url_have(menu_url_config['system'])){
            $('li#nav-1-system').addClass('active');
        }else if (url_have(menu_url_config['picture'])){
            $('li#nav-1-picture').addClass('active');
        }else if (url_have(menu_url_config['department'])){
            $('li#nav-1-department').addClass('active');
        }
        else{
            $('li#nav-1-home').addClass('active');
        }
    };

    return {
        init: function () {
            tableSearch(), columnVisible(), multiSelect(), menuActive()
        },
        tableReload: function () {
            var oTable = $('#form_search').DataTable();
            oTable.ajax.reload(null, false);
        },
        checkBoxVal: checkBoxVal,
        checkBoxRow: checkBoxRow,
        checkBoxGroup: checkBoxGroup,
        getDataRow: getDataRow,
        getTableRow: getTableRow,
        columnVisibleInit: columnVisibleInit,
        checkBoxTitle80: checkBoxTitle80,

    }
}();
jQuery(document).ready(function () {
    p_dataTable.init()
});
