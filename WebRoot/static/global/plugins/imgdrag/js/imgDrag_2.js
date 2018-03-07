/**
 * Created by starmerk_lzy on 16-5-18.
 */
 $(function () {

     ImgListMouseDownEvent();//注册鼠标在图片上的事件(按下事件和双击事件)
     SortListMouseDownEvent();//注册鼠标在排序后图片上的事件(按下事件和双击事件)
     WindowMouseMoveAndUp();//注册windows事件（鼠标移动和松开事件）
 });
//$("#attr_img_modal").on("shown.bs.modal",function (e) {
//
//    ImgListMouseDownEvent();//注册鼠标在图片上的事件(按下事件和双击事件)
//    SortListMouseDownEvent();//注册鼠标在排序后图片上的事件(按下事件和双击事件)
//    WindowMouseMoveAndUp();//注册windows事件（鼠标移动和松开事件）
//})

//禁止拖动
document.ondragstart = function () {
    return false
};
//禁用右键
//$(document).ready(function () {
//    $(document).bind("contextmenu", function (e) {
//        return false;
//    });
//});


//注册鼠标在图片上的事件(按下事件和双击事件)
function ImgListMouseDownEvent() {
    $(".imgList > li").unbind("mousedown").unbind("dblclick");//为图片绑定(注册)鼠标按下事件和鼠标双击事件
    $(document).on('mousedown', '.imgList > li[allow]', function (event) {
        $(document.body).data("_isimgdown_", true); //标示是否为鼠标在图片上的按下事件
        $(document.body).data("_eventx_", event.pageX); //鼠标在图片上按下事件时鼠标的x坐标值
        $(document.body).data("_eventy_", event.pageY); //鼠标在图片上按下事件时鼠标的y坐标值
        $(document.body).data("_li_", $(this)); //鼠标在图片上按下事件时当前的li元素

        $(document.body).data("_imgtop_", (event.pageY - $(this).offset().top) * 0.26);
        $(document.body).data("_imgleft_", (event.pageX - $(this).offset().left) * 0.38);
        //var _li_ = $(document.body).data("_li_");
        //alert("x:" + $(document.body).data("_eventx_") + " y:" + $(document.body).data("_eventy_") + " li-img:" + $("img", _li_).attr("src") + " li-div:" + $("div", _li_).html());
    })
}

//注册鼠标在排序后图片上的事件(按下事件和双击事件)
function SortListMouseDownEvent() {
    $(".sortList li").unbind("mousedown").unbind("dblclick").unbind("mouseover").unbind("mouseout");
    $(document).on('mousedown', '.sortList li[allow=true]', function (event) {

        $(document.body).data("_issortdown_", true);
        $(document.body).data("_eventx_", event.pageX);
        $(document.body).data("_eventy_", event.pageY);
        $(document.body).data("_li_", $(this));

        $(document.body).data("_imgtop_", (event.pageY - $(this).offset().top));
        $(document.body).data("_imgleft_", (event.pageX - $(this).offset().left));

    });
}

//#region 添加无图片样式 addNoImgLiCss
function addNoImgLiCss() {


    $('ul.sortList').each(function () {
        var index = 0;
        $('li', $(this)).each(function () {
            if ($("img", this).length == 0) {
                $(this).html("<div class='sortListBg Bg" + index + "'></div>");
            }
            index++;
        });
    });


}
//#endregion

// 获取ul的位置数组
function getUlArrayList() {
    var arraylist = new Array();
    $("ul.sortList").each(function (i) {
        var array = new Array();
        array[0] = $(this).offset().left;
        array[1] = $(this).offset().left + $(this).width();
        array[2] = $(this).offset().top;
        array[3] = $(this).offset().top + $(this).height();
        arraylist[i] = array;
    });
    return arraylist;
}
//获得未排序的图片的ur
function getImgUlArryList() {
    var array = new Array();
    if($("ul.imgList").length<1) return;
    array[0] = $("ul.imgList").offset().left;
    array[1] = $("ul.imgList").offset().left + $("ul.imgList").width();
    array[2] = $("ul.imgList").offset().top;
    array[3] = $("ul.imgList").offset().top + $("ul.imgList").height();
    return array;
}

//#region 获取排序后li的位置数组
function getSortPositionArrayList(position) {
    var arraylist = new Array();
    $("li", $("ul.sortList:eq(" + position + ")")).each(function (i) {
        var array = new Array();
        array[0] = $(this).offset().left;
        array[1] = $(this).offset().left + $(this).width();
        array[2] = $(this).offset().top;
        array[3] = $(this).offset().top + $(this).height();
        arraylist[i] = array;
    });
    return arraylist;
}
//#endregion

function getImgPositionArrayList() {
    var arraylist = new Array();
    $("li", $("ul.imgList")).each(function (i) {
        var array = new Array();
        array[0] = $(this).offset().left;
        array[1] = $(this).offset().left + $(this).width();
        array[2] = $(this).offset().top;
        array[3] = $(this).offset().top + $(this).height();
        arraylist[i] = array;
    });
    return arraylist;
}
//#region 获取拖动位置 getPosition
function getUlPosition(pageX, pageY) {
    var arraylist = getUlArrayList();//获取排序后ul的位置数组
    var array = getImgUlArryList();//获取排序前 ul的位置数组
    for (var i = 0; i < arraylist.length; i++) { //循环排序后ul的位置数组
        if (pageX >= arraylist[i][0] && pageX <= arraylist[i][1] && pageY >= arraylist[i][2] && pageY <= arraylist[i][3]) {
            return i; //如果鼠标的坐标位置处于排序后ul的位置数组之间则返回排序后当前的ul的索引
        }
    }
    if (pageX >= array[0] && pageX <= array[1] && pageY >= array[2] && pageY <= array[3]) {
        return -1; //如果鼠标的坐标位置处于排序前ul的位置数组之间则返回排序后当前的ul的索引
    }
    return -2;
}

//#region 获取拖动位置 getPosition
function getPosition(pageX, pageY, position) {
    if (position == -2) {
        return -2
    }
    if (position == -1){
        var arraylist = getImgPositionArrayList();//获取排序前li的位置数组
    }else {
        var arraylist = getSortPositionArrayList(position);//获取排序后li的位置数组
    }
    for (var i = 0; i < arraylist.length; i++) { //循环排序后li的位置数组
        if (pageX >= arraylist[i][0] && pageX <= arraylist[i][1] && pageY >= arraylist[i][2] && pageY <= arraylist[i][3]) {
            return i; //如果鼠标的坐标位置处于排序后li的位置数组之间则返回排序后当前的li的索引
        }
    }
    return -2;
}
//#endregion

//#region 注册windows事件（鼠标移动和松开事件）
function WindowMouseMoveAndUp() {
    $(document).mousemove(function (event) {
        ImgDragEvent(event);//鼠标在排序前图片上按下后的拖拽事件
        SortDragEvent(event);//鼠标在排序后图片上按下后的拖拽事件
    }).mouseup(function (event) {
        var position_ul = getUlPosition(event.pageX, event.pageY);
        var position_li = getPosition(event.pageX, event.pageY, position_ul);
        if ($(document.body).data("_isimgdown_")) { //鼠标是在排序前图片上按下时
            ImgMouseUpEvent(position_ul, position_li,event.target);//鼠标在图片上的松开事件
        }
        if ($(document.body).data("_issortdown_")) {//鼠标是在排序后图片上按下时
            SortMouseUpEvent(position_ul, position_li);//鼠标在排序后图片上的松开事件
        }
    });
}
//#endregion

//#region 注册鼠标在排序前图片上按下后的拖拽事件
function ImgDragEvent(event) {
    //排序前图片拖拽
    if ($(document.body).data("_isimgdown_")) {
        var _eventx_ = $(document.body).data("_eventx_");
        var _eventy_ = $(document.body).data("_eventy_");
        var _li_ = $(document.body).data("_li_");
        //整体移动 >5 相素时移动
        if (Math.abs(event.pageX - _eventx_) + Math.abs(event.pageY - _eventy_) >= 5) {
            //var _divHtml_ = _li_.html();
            var _divHtml_ = _li_.find('img').prop("outerHTML");
            $("#DropDiv").html(_divHtml_)
                .css({
                    top: event.pageY - $(document.body).data("_imgtop_"),
                    left: event.pageX - $(document.body).data("_imgleft_")
                })
                .height(54)
                .show();
        }
    }
}
//#endregion

//#region 注册鼠标在排序后图片上按下后的拖拽事件
function SortDragEvent(event) {
    //排序后图片拖拽
    if ($(document.body).data("_issortdown_")) {
        var _eventx_ = $(document.body).data("_eventx_");
        var _eventy_ = $(document.body).data("_eventy_");
        var _li_ = $(document.body).data("_li_");
        //整体移动 >5 相素时移动
        if (Math.abs(event.pageX - _eventx_) + Math.abs(event.pageY - _eventy_) >= 5) {
            var imgsrc = $("img", _li_).length > 0 ? $("img", _li_).attr("src") : "/Aspx_Page/images/white.png";
            //var _divHtml_ = _li_.html(); //因为后续li下加了 button 所以现在改为只获取img的html
            var _divHtml_ = _li_.find('img').prop("outerHTML");
            $("#DropDiv").html(_divHtml_)
                .css({
                    top: event.pageY - $(document.body).data("_imgtop_"),
                    left: event.pageX - $(document.body).data("_imgleft_")
                })
                .height(54)
                .show();
        }
    }
}
//#endregion

//#region 注册图片mouseup事件 registerImgMouseUpEvent ele:代表当前点击的jquery对象
function ImgMouseUpEvent(position_ul, position_li,ele) {

    //当前拖拽的图片对象
    var _li_ = $(document.body).data("_li_");

    var _img_ = $("img", _li_);
    if(position_ul == -1 && $(ele).parentsUntil("li").parent()[0] != _li_[0]){
        var liHtml = '<li allow="true" style="height: 140px" >' +
                    '<img  class="edit_img" src="' + _img_.attr("src") + '"/>' +
                    '<button type="button" class="btn btn-xs red btn-outline img_del">' +
                    '<i class="fa fa-times"></i>' +
                    '</button></li>';
        if(position_li == -2){
            $('ul.imgList').append(_li_);
        }else {
            $('ul.imgList').find('li:eq(' + position_li + ')').before(liHtml);
            _li_.remove();
        }
    }
    if (position_ul != -1 && position_ul != -2 && position_li != -2) {//当前拖拽位置
        //拖在已存在图片上
        if ($("img", $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')'))).length > 0) {
            var existsid = $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')')).attr("id");
        }
       if (position_ul == 0){
            //如果从排序前图片拖动到排序后的ul上的第一个ul时候，我们需要对所有的ul的这个位置进行图片复制
            $("li:eq(" + position_li + ")", $('ul.sortList'))
                .empty()
                .html("<img  height=54 src=" + _img_.attr("src") + " />")
                .removeAttr("id")
                .attr("allow", true);
       }else{
             //如果从排序前图片拖动到排序后的ul上的非第一个ul时候，我们需要对当前的ul的这个位置进行图片复制
            $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')'))
                .empty()
                .html("<img  height=54 src=" + _img_.attr("src") + " />")
                .removeAttr("id")
                .attr("allow", true);
        }
        //注册事件
        ImgListMouseDownEvent();//注册鼠标在图片上的事件(按下事件和双击事件)
        SortListMouseDownEvent();//注册鼠标在排序后图片上的事件(按下事件和双击事件)
    }
    //移除img的mousedown
    $(document.body).removeData("_isimgdown_");

    //隐藏拖拽层
    $("#DropDiv").empty().hide();

}
//#endregion

//#region 注册ULmouseup事件 registerUlMouseUpEvent
function SortMouseUpEvent(position_ul, position_li) {
    //debugger;
    //当前拖拽li对象
    var _li_ = $(document.body).data("_li_");
    if(position_ul == -1) {//移动的是排序后的图片，移动到的位置是排序前的位置，不允许这种操作。
        $(document.body).removeData("_issortdown_");
        $("#DropDiv").empty().hide();
        return;
    }
    //当前拖拽位置
    if (position_ul != -2 && position_li != -2) {
        //debugger;
        //拖在已存在图片上
        if ($("img", $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')'))).length > 0) {
            var existsid = $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')')).attr("id");
        }
        //在新位置添加元素
        $("li:eq(" + position_li + ")", $('ul.sortList:eq(' + position_ul + ')'))
            .empty()
            .attr("allow", true)
            .html(_li_.html());
        //移除原位置元素
        $("li:eq(" + _li_.index() + ")", $('ul.sortList:eq(' + position_ul + ')'))
            .removeAttr("id")
            .removeAttr("allow")
            .html('<div class="sortListBg Bg"' + (_li_.index() + 1));
    }
    // else {
    //     $("#" + _li_.attr("id").replace("_s_", ""))
    //         .attr("allow", true);
    //     _li_.empty().removeAttr("allow").removeAttr("id");
    //
    // }

    //注册事件
    ImgListMouseDownEvent();//注册鼠标在图片上的事件(按下事件和双击事件)
    SortListMouseDownEvent();//注册鼠标在排序后图片上的事件(按下事件和双击事件)


    //添加无图片的li样式
    addNoImgLiCss();

    //移除ul的mousedown
    $(document.body).removeData("_issortdown_");

    //隐藏拖拽层
    $("#DropDiv").empty().hide();

}
//#endregion