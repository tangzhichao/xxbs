/**
 * Created by starmerk_lzy on 16-11-3.
 */

var categorySearch = function () {

    var ajaxPhCategory = function (category_id_path) {
        var params = {};
        params['category_id_path'] = category_id_path;
        var ret = '';
        $.ajax({
            "url": "/kaifa/api/queryPhCategoryChild/",
            "data": params,
            "type": 'POST',
            "dataType": 'json',
            "async": false,
            "beforeSubmit": function () {
            },
            "success": function (data) {
                ret = data.data
            },
            "error": function (xhr, status, error) {
                alert("获取子类失败：" + error);
            }
        });
        return ret;
    }, addChildCategory = function (data, level) {
        var new_level = (parseInt(level) + 1).toString();
        var $select = $('<div class="col-md-2">' +
            '<div class="form-group">' +
            '<label>&nbsp;</label>' +
            '<div class="input-group">' +
            '<select  class="bs-select form-control puhuo_category_search" style="width: 100%;" level="' + new_level + '"' +
            'data-live-search="true">' +
                '<option value="" ></option>'+
            '</select>' +
            '</div>' +
            '</div>' +
            '</div>');
        $.each(data, function () {
            var category_id_path = this.category_id_path + '>' + this.id;
            var opt_html = '<option value="' + category_id_path + '" >' + this.category_name + '</option>';
            $select.find('select').append(opt_html);
        });
        $select.find('select').val('');
        $('#form_search #puhuo_category_row').append($select);
    }, removeChildCategory = function (level) {
        var level = level;
        var $select = $('#form_search #puhuo_category_row .puhuo_category_search');
        $.each($select, function () {
            if ($(this).attr('level') > level) {
                $(this).parents('div.col-md-2:first').remove();
            }
        });
    }, setCategoryValue = function (v) {
        $('#form_search #category_id_path').val(v);
        //var category_name_path = [];
        //var $categorys = $('#form_search .puhuo_category_search');
        //$categorys.each(function () {
        //    category_name_path.push($(this).find('option:selected').text());
        //});
        //var category_name_path_str = category_name_path.join('>');
        //$('#form_search #category_name_path').val(category_name_path_str)
    };

    var phCategory = function () {
        // puhuo分类选择
        $(document).on('change', '.puhuo_category_search', function (e) {
            e.preventDefault();
            var level = $(this).attr('level');
            var category_id_path = '';
            var thsi_val = $(this).val();

            removeChildCategory(level);     //删除后面的类选框

            if (thsi_val){  //选择的是有效值的时候
                // 设置category的选值
                category_id_path = thsi_val;

                var data = ajaxPhCategory(thsi_val);
                if (data.length > 0) {
                    addChildCategory(data, level);
                }
            }else{  //选择的是无效值（空值）的时候。
                var $prev_select = $(this).parents('div.col-md-2:first').prev().find('select');
                category_id_path = $prev_select.val();
            }
            setCategoryValue(category_id_path);
        });
    };

    return {
        init: function () {
            phCategory();
        }
    }
}();
jQuery(document).ready(function () {
    categorySearch.init();

});
