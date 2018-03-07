
jQuery.validator.addMethod("lrunlv", function (value, element) {
    return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);
}, "小数位不能超过两位");

jQuery.validator.addMethod("twoDecimal", function (value, element) {
    return this.optional(element) || /^(-)\d+(\.\d{1,2})?$/.test(value);
}, "小数位不能超过两位");

jQuery.validator.addMethod("nfiveDecimal", function (value, element) {
    return this.optional(element) || /^\d{0,3}(\.\d{1,2})?$/.test(value);
}, "整数位不能超过三位且小数位不能超过两位");


jQuery.validator.addMethod("fourDecimal", function (value, element) {
    return this.optional(element) || /^\d+(\.\d{1,4})?$/.test(value);
}, "小数位不能超过四位");

jQuery.validator.addMethod("threeDecimal", function (value, element) {
    return this.optional(element) || /^\d+(\.\d{1,3})?$/.test(value);
}, "小数位不能超过三位");

jQuery.validator.addMethod("maxTo", function (value, element, params) {
    return this.optional(element) || parseFloat(value)<=parseFloat($(params).val());
}, "超数了");

jQuery.validator.addMethod("supplierUrl", function (value, element) {
    return this.optional(element) || /^http[s]?:\/\/(.*).1688.com\/$/.test(value);
}, "供应商主页不对");

jQuery.validator.addMethod("taskedit",function(value,element,param){
if($(param).val()=='1' && !value){
   return false;
}else{
    return true;
}
}, "难度不能为空");