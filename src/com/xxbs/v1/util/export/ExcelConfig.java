package com.xxbs.v1.util.export;

/**
 * 导出Excel时样式配置类
 * 
 * @注 ExcelConfig大致有三种情况：
 * 
 * @注 如果为空,即:exportTool.setExcelConfig(null)，宽高由poi自身处理(poi会自动调整行高，但是列宽固定)
 * 
 * @注 如果设置自动宽高,即excelConfig.isAutoWidth or Height=true,行高或列宽 会根据内容自动适应,此设置比较消耗内存, 因为牵涉很多遍历和比较计算,如果数据量较大,切记勿将isAutoWidth or Height设为true
 * 
 * @注 另外特别注明：如果isAutoHeight=false，则行高就会使用excelConfig.height， 如果isAutoWidth=false，则列宽会使用excelConfig.width
 * 
 * 
 * @注 如果不自动宽高,即excelConfig.isAutoWidth and Height=false,宽高会被设置成固定的值,即excelConfig. width和excelConfig.height,(excelConfig.isAutoWidth and
 *    Height=false是ExportTool默认的情况)
 * 
 * @注 poi3.9支持的Excel最大行数65536，最大列数256,默认行高255，默认列宽2048(10字符)
 * 
 * @注 ExportTool默认有一个ExcelConfig对象，如果你不需要ExcelConfig对象，可以调用ExportTool. setExcelConfig(null),或者在构造方法传入null
 * 
 * @author tang
 * 
 */
public class ExcelConfig {

	// 切记，以下这些静态变量的数值依赖于字体为10
	public static final int MinWidth = 2048;
	public static final int ChineseCharWidth = (int) (MinWidth / 4);
	public static final int UpperCharWidth = MinWidth / 6;
	public static final int NumberAndLowerCharWidth = MinWidth / 8;
	public static final int MinHeight = 255;
	public static final int MaxWidth = 127 * ChineseCharWidth;

	public final short fontSize = 10;// 此变量不要轻易改动，一旦改动上面那些变量的值将全部错误

	public String fontName = "宋体";

	/**
	 * 是否设置边框
	 */
	public boolean isBorder = true;

	/**
	 * 是否自动列宽
	 */
	public boolean isAutoWidth;
	/**
	 * 是否自动行高
	 */
	public boolean isAutoHeight;

	/*-------------如果isAutoWidthHeight 和 isAutoHeight都设置为false，那么以下属性不用设置，因为用不到了--------------*/

	/**
	 * 最小行高
	 */
	public int minHeight = 255;// 350
	/**
	 * isAutoHeight为true时控制最大行高
	 */
	public int maxHeight = 20000;
	/**
	 * 最小列宽
	 */
	public int minWidth = 2048;// 2000
	/**
	 * isAutoWidth为true时控制最大列宽
	 */
	public int maxWidth = 20000;

	/*-------------*****************************************************--------------*/

	/*-------------如果isAutoWidth 和 isAutoHeight都设置为true，那么以下属性不用设置，因为用不到了--------------*/
	/**
	 * isAutoHeight为false时控制行高
	 */
	public int height = 255;
	/**
	 * isAutoWidth为false时控制列宽，此宽度单位比较特殊，为字符个数
	 */
	public int widthByCharCount = 10;

	/*-------------*****************************************************--------------*/
	public ExcelConfig() {
		this(true, true);
	}

	public ExcelConfig(boolean isAutoWidth, boolean isAutoHeight) {

		this.isAutoWidth = isAutoWidth;
		this.isAutoHeight = isAutoHeight;
	}
}
