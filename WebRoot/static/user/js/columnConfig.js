//data datatables取值的字段名，如果不填，则显示空字符串
//table 列所在的表，如果data为id，则必填，否则默认取data为id的table值
//column 搜索时用的字段名，如果不填，则等于data
//showName 列的展示名称，必填
//style 设置列头样式（一般是设置列宽），必填
//align 设置列头显示方式，如果不填，则默认center
//searchType 搜索类型，有：none、text、number、integer、date、time、datetime、option、browse、checkbox，如果不是搜索条件则不填
//searchRowIndex 在搜索栏第几行显示（控制显示顺序），如果不填默认是1
//searchColumnIndex 在搜索栏第几列显示（控制显示顺序），如果不填默认是1
//searchColumnCount 在搜索栏占几列显示，如果searchWhereKey是between，则searchColumnCount在填值时，应该除以2，不然可能显示过宽，如果不填默认是1
//searchWhereKey 搜索条件采用什么sql关键字，有：equal、like、between，如果不是搜索条件则不填
//refTable 外键列关联的表，如果不是外键则不填
//refId 外键列关联的id列，如果不是外键则不填
//refName 外键列关联的显示列，如果不是外键则不填
//refWhere 外键列关联条件，如果不是外键则不填
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
	"addType" : "text",
	"addRowIndex" : "1",
	"addColumnIndex" : "1",
	"addColumnCount" : "6",
	"editType" : "text",
	"editRowIndex" : "1",
	"editColumnIndex" : "1",
	"editColumnCount" : "6"
}, {
	"data" : "email",
	"showName" : "邮箱",
	"style" : "width: 13%;",
	"searchType" : "text",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "text",
	"addRowIndex" : "1",
	"addColumnIndex" : "2",
	"addColumnCount" : "6",
	"editType" : "text",
	"editRowIndex" : "1",
	"editColumnIndex" : "2",
	"editColumnCount" : "6"
}, {
	"data" : "nick_name",
	"showName" : "昵称",
	"style" : "width: 10%;",
	"searchType" : "text",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "like",
	"addType" : "text",
	"addRowIndex" : "2",
	"addColumnIndex" : "1",
	"addColumnCount" : "6",
	"editType" : "text",
	"editRowIndex" : "2",
	"editColumnIndex" : "1",
	"editColumnCount" : "6"
}, {
	"data" : "city",
	"showName" : "所在城市",
	"style" : "width: 10%;",
	"searchType" : "browse",
	"searchRowIndex" : "1",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "equal",
	"addType" : "browse",
	"addRowIndex" : "2",
	"addColumnIndex" : "2",
	"addColumnCount" : "6",
	"editType" : "browse",
	"editRowIndex" : "2",
	"editColumnIndex" : "2",
	"editColumnCount" : "6",
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
	"addType" : "option",
	"addRowIndex" : "3",
	"addColumnIndex" : "1",
	"addColumnCount" : "6",
	"editType" : "option",
	"editRowIndex" : "3",
	"editColumnIndex" : "1",
	"editColumnCount" : "6"
}, {
	"data" : "gold",
	"showName" : "金币",
	"style" : "width: 10%;",
	"searchType" : "number",
	"searchRowIndex" : "2",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "between",
	"addType" : "number",
	"addRowIndex" : "3",
	"addColumnIndex" : "2",
	"addColumnCount" : "6",
	"editType" : "number",
	"editRowIndex" : "3",
	"editColumnIndex" : "2",
	"editColumnCount" : "6"
}, {
	"data" : "recorded_time",
	"showName" : "创建时间",
	"style" : "width: 12%;",
	"searchType" : "date",
	"searchRowIndex" : "2",
	"searchColumnIndex" : "1",
	"searchColumnCount" : "2",
	"searchWhereKey" : "between",
	"addType" : "none",
	"editType" : "none"
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
	"addType" : "option",
	"addRowIndex" : "4",
	"addColumnIndex" : "1",
	"addColumnCount" : "6",
	"editType" : "option",
	"editRowIndex" : "4",
	"editColumnIndex" : "1",
	"editColumnCount" : "6"
}, {
	"showName" : "操作",
	"style" : "width: 10%;",
	"align" : "center"
} ];