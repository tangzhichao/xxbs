package com.xxbs.v1.util.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.str.ValidUtil;

/**
 * 图形工具类
 * 
 * @author tang
 * @date 2015/3/25
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ChartUtil {

	/**
	 * 图形类型
	 */
	public static enum ChartType {
		Bar, Line, Stacked, Pie
	}

	/*------------------------------分割线开始： 图形和图形面板--------------------------------*/

	protected StandardChartTheme chartDefaultStyle;// 统一的默认样式
	private final String chartNumberFormatString = "#,##0.###" + ";" + "-#,##0.###";// '#'表示此位置可有可无，'0'表示此位置如果没有就用'0'填充
	protected DecimalFormat chartNumberFormat = new DecimalFormat(chartNumberFormatString);// 格式化曲线中显示的数字
	private final String chartNumberPercentString = "0%" + ";" + "-0%";
	protected DecimalFormat chartPercentFormat = new DecimalFormat(chartNumberPercentString);// 格式化曲线中显示的百分比
	protected final int chartMinimumDrawHeight = 30;// 如果图形实际显示的高度小于这个高度则图形开始变形
	protected final int chartMinimumDrawWidth = 30;// 如果图形实际显示的宽度小于这个宽度则图形开始变形
	private final Font defaultFont = new Font("宋体", Font.PLAIN, 12);

	protected boolean isCreateLegend = false;

	/*------------------------------分割线开始：图基--------------------------------*/

	protected Paint plotBackground = Color.WHITE;// 图基背景色
	protected RectangleInsets plotInsets = new RectangleInsets(4, 8, 4, 8);// 图基四周间隙
	protected boolean plotBorderVisible = true;// 图基是否显示边框
	protected Stroke plotBorderStroke = new BasicStroke(1);// 图基边框笔触（笔触就是线的粗细等属性）
	protected Paint plotBorderColor = Color.decode("#D8D8D8");// 图基边框颜色
	protected String plotNoneDataMessage = "暂无数据!";// 没有数据时图基显示的文字

	// 系列图图基（线图，柱状图等）
	protected boolean plotDomainGridlineVisible = true;// X轴网格线是否显示
	protected Paint plotDomainGridlineColor = Color.decode("#DDDDDD");// X轴网格线颜色
	protected Stroke plotDomainGridlineStroke = new BasicStroke(1);// X轴网格线笔触（笔触就是线的粗细等属性）

	protected boolean plotRangeGridlineVisible = true;// Y轴网格线是否显示
	protected Paint plotRangeGridlineColor = Color.decode("#DDDDDD");// Y轴网格线颜色
	protected Stroke plotRangeGridlineStroke = new BasicStroke(1);// Y轴网格线笔触（笔触就是线的粗细等属性）

	protected RectangleInsets plotAxisOffset = new RectangleInsets(0, 0, 0, 0);// 图基和轴之间的间隙

	// 饼图图基
	protected RectangleInsets plotPieInsets = plotInsets;// 饼图图基四周间隙
	protected boolean plotPieIgnoreNullValue = false;// 饼图是否忽略空数据标签
	protected boolean plotPieIgnoreZeroValue = false;// 饼图是否忽略0数据标签
	protected boolean plotPieSectionBorderVisible = false;// 饼图每一部分的边框是否显示
	protected Paint plotPieSectionBorderColor = new Color(255, 255, 240);// 饼图数据标签背景色
	protected boolean plotPieLabelLinksVisible = true;// 饼图标签与图基的连线是否显示
	protected double plotPieLabelLinkMargin = 0;// 饼图数据标签与图基的连接线边距，相当于间接的设置饼图的大小，数值越大图形越小（0.0~1.0），当数值为1.0时图形最小（一个点）
	protected double plotPieLabelGap = 0.025;// 饼图数据标签与图基的连接线边距，相当于间接的设置饼图的大小，数值越大图形越小（0.0~1.0），当数值为1.0时图形最小（一个点）
	protected Paint plotPieLabelBackground = new Color(255, 255, 240);// 饼图数据标签背景色
	protected Font plotPieLabelFont = defaultFont;// 饼图数据标签字体
	protected Paint plotPieLabelColor = Color.BLACK;// 饼图数据标签文字颜色
	protected Paint plotPieLabelBorderColor = Color.BLACK;// 饼图数据标签边框颜色
	protected Paint plotPieLabelShadowColor = null;// 饼图数据标签阴影颜色
	protected Paint plotPieShadowColor = null;// 饼图阴影颜色
	protected double plotPieShadowXOffset = 4.0;// 饼图阴影与图基水平方向相距多远
	protected double plotPieShadowYOffset = 4.0;// 饼图阴影与图基垂直方向相距多远

	protected String[] plotPieIgnoreKeys = null;// 饼图中需要忽略的一部分，被忽略的部分不显示标签不显示工具提示，但会被绘制在图基上
	protected String plotPieExplodePercentKey = null;// 饼图中需要突出的一部分
	protected double plotPieExplodePercentValue = 0;// 饼图中需要突出的一部分突出多少，数值越大突出越多（0.0~1.0）
	protected boolean plotPieIsSimpleLabels = false;// 是否绘制简单的标签，如果为true，则将标签之间绘制在图基上
	protected double plotPieInteriorGap = 0.08;// 饼图饼内部间隙（不同于图基间隙） ，数值越大图形越小（0.0~1.0）
	protected int plotPieStartAngle = 90;// 饼图绘制开始角度（0到360），默认90
	protected Rotation plotPieDirection = Rotation.CLOCKWISE;// 饼图绘制方向，默认顺时针方向
	protected Shape plotPieLegendItemShape = new RoundRectangle2D.Float(0, 0, 15, 12, 5, 5);// 饼图图例

	// 数据格式,饼图, {0} = 所在系列（每列数据）的标题, {1} =所在时间系列（时间列数据）的名称,{2} = 数据本身的值, {3} = 此数据在同一时间节点上占所有系列的百分比
	protected PieSectionLabelGenerator plotPieLabelFormatGenerator = new StandardPieSectionLabelGenerator("{0} {2}", chartNumberFormat, chartPercentFormat);// 图表节点上数据显示格式
	protected PieToolTipGenerator plotPieToolTipFormatGenerator = new StandardPieToolTipGenerator("{1}", chartNumberFormat, chartPercentFormat);// 图表节点上工具提示数据显示格式

	protected IgnoreKeyPieSectionLabelGenerator plotPieIgnoreKeyLabelFormatGenerator = new IgnoreKeyPieSectionLabelGenerator("{0} {2}", chartNumberFormat,
			chartPercentFormat, plotPieIgnoreKeys);// 图表节点上数据显示格式
	protected IgnoreKeyPieToolTipGenerator plotPieIgnoreKeyToolTipFormatGenerator = new IgnoreKeyPieToolTipGenerator("{1}", chartNumberFormat,
			chartPercentFormat, plotPieIgnoreKeys);// 图表节点上工具提示数据显示格式

	/*------------------------------分割线开始：标题--------------------------------*/

	// 主标题（TextTitle）
	protected Paint titleColor = new Color(45, 45, 45);// 主标题的颜色
	protected Paint titleBackground = Color.WHITE;
	protected Font titleFont = new Font("宋体", Font.PLAIN, 14);// 主标题的字体
	protected RectangleEdge titlePosition = RectangleEdge.TOP;// 主标题摆放在图形中的位置
	protected RectangleInsets titleMargin = new RectangleInsets(0, 0, 0, 0);// 外间距
	protected RectangleInsets titlePadding = new RectangleInsets(0, 0, 0, 0);// 内间距
	protected HorizontalAlignment titleHorizontalAlignment = HorizontalAlignment.CENTER;// 主标题文字水平对齐方式

	// 副标题（TextTitle）,副标题的其他属性和主标题相同，故不再定义，如果不同请自行定义并使用之
	protected Paint subtitleColor = new Color(45, 45, 45);// 主标题的颜色
	protected Paint subtitleBackground = Color.WHITE;
	protected Font subtitleFont = new Font("宋体", Font.PLAIN, 12);// 主标题的字体
	protected RectangleEdge subtitlePosition = RectangleEdge.TOP;// 副标题摆放在图形中的位置
	protected RectangleInsets subtitleMargin = new RectangleInsets(0, 0, 0, 0);// 外间距
	protected RectangleInsets subtitlePadding = new RectangleInsets(0, 0, 0, 0);// 内间距
	protected HorizontalAlignment subtitleHorizontalAlignment = HorizontalAlignment.CENTER;// 主标题文字水平对齐方式

	// 图例标题（LegendTitle）
	protected Paint legendTitleColor = new Color(45, 45, 45);// 图例标题的颜色
	protected Paint legendTitleBackground = Color.WHITE;
	protected Font legendTitleFont = new Font("宋体", Font.PLAIN, 14);// 图例标题的字体
	protected RectangleEdge legendTitlePosition = RectangleEdge.BOTTOM;// 图例摆放在图形中的位置
	protected RectangleInsets legendTitleMargin = new RectangleInsets(6, 0, 0, 0);// 外间距
	protected RectangleInsets legendTitlePadding = new RectangleInsets(0, 0, 0, 0);// 内间距
	protected HorizontalAlignment legendTitleHorizontalAlignment = HorizontalAlignment.CENTER;

	/*------------------------------分割线开始：轴--------------------------------*/

	protected boolean axisLineVisible = false;// 是否显示轴线
	protected Paint axisLineColor = plotBorderColor;// 轴的颜色
	protected Paint axisLabelColor = new Color(66, 66, 66);// 轴上的刻度值标签的颜色
	protected boolean axisDomainLabelVisible = true;// X轴上的刻度值标签的颜色
	protected boolean axisRangeLabelVisible = true;// Y轴上的刻度值标签的颜色
	protected Font axisLabelFont = new Font("宋体", Font.PLAIN, 14);// 轴的字体
	protected RectangleInsets axisLabelInsets = new RectangleInsets(0, 0, 0, 0);
	protected boolean axisTickMarkVisible = false;
	protected Paint axisTickMarkColor = new Color(100, 100, 100);// 轴上的刻度的颜色

	protected double axisUpperMargin = 0.2;// 设置顶部Y坐标轴间距,防止数据无法显示
	protected double axisLowerMargin = 0.2;// 设置顶部Y坐标轴间距,防止数据无法显示
	protected boolean axisNumberAutoRangeIncludesZero = true;// 自动范围时是否必须从0开始
	protected CategoryLabelPositions axisDomainLabelPositions = CategoryLabelPositions.UP_90;// X轴标签的方向
	protected boolean axisIsFixedWidth = false;// 是否固定轴宽
	protected int axisFixedWidth = 40;// 如果固定轴宽，轴宽为多少

	/*------------------------------分割线开始：渲染器--------------------------------*/

	private final float rendererSeriesShapeRoundSize = 6;// 像素是最小单位，所以此处请一定要用2的倍数，避免半径有小数点
	protected boolean rendererSeriesShapeVisible = true;// 是否显示节点图形
	protected Shape rendererSeriesShape = new Ellipse2D.Float(-(rendererSeriesShapeRoundSize / 2), -(rendererSeriesShapeRoundSize / 2),
			rendererSeriesShapeRoundSize, rendererSeriesShapeRoundSize);// 图表节点图形

	protected boolean useFocusedSeriesShape = false;// 是否使用鼠标悬浮图标
	private final float rendererFocusedSeriesShapeRoundSize = 10;// 像素是最小单位，所以此处请一定要用2的倍数，避免半径有小数点
	protected Shape rendererFocusedSeriesShape = new Ellipse2D.Float(-(rendererFocusedSeriesShapeRoundSize / 2), -(rendererFocusedSeriesShapeRoundSize / 2),
			rendererFocusedSeriesShapeRoundSize, rendererFocusedSeriesShapeRoundSize);// 图表鼠标悬浮时节点图形

	protected Stroke rendererSeriesStroke = new BasicStroke(2);
	protected boolean rendererItemLabelsVisible = false;// 图表节点上数据是否显示

	protected Shape rendererBarLegendShape = new Rectangle2D.Double(-10.0, -4.0, 15.0, 8.0);// 柱状图图例图形
	protected double rendererBarMaximumWidth = 0.06;// 柱状图的柱子最宽占比
	protected double rendererBarItemMargin = 0.01;// 柱状图的柱子间隙占比
	protected boolean rendererBarShodowVisible = false;// 柱状图的柱子阴影是否显示
	protected double rendererBarShadowXOffset = 4.0;// 柱状图的柱子阴影水平方向相距多远
	protected double rendererBarShadowYOffset = 4.0;// 柱状图的柱子阴影垂直方向相距多远

	// 数据格式, 非饼图 , {0} = 所在系列（每列数据）的标题, {1} =所在时间系列（时间列数据）的名称,{2} = 数据本身的值, {3} = 此数据在同一时间节点上占所有系列的百分比
	protected CategoryItemLabelGenerator rendererLabelFormatGenerator = new StandardCategoryItemLabelGenerator("{2}", chartNumberFormat, chartPercentFormat);// 图表节点上数据显示格式
	protected CategoryToolTipGenerator rendererToolTipFormatGenerator = new StandardCategoryToolTipGenerator("{2}", chartNumberFormat);// 图表节点上工具提示数据显示格式

	public ChartUtil() {
		System.out.println("正在初始化：" + getClass().getSimpleName());
	}

	/**
	 * 创建图形面板
	 * 
	 * @param mainTitle
	 *            图形主标题
	 * @param titles
	 *            每个系列（每列数据）的标题
	 * @param units
	 *            每个系列（每列数据）的单位
	 * @param colors
	 *            每个系列（每列数据）的颜色，此参数为二维结构，第一级数组是表示轴，第二级表示每个系列（每列数据），<br>
	 *            这样做主要是因为单轴（使用一个Y轴）和双轴（使用两个Y轴）的差别造成的，为了方便使用，所以统一使用双轴的颜色参数。<br>
	 *            示例：<br>
	 *            如果为饼图，假设标题参数为String[] titles={"title1","title2"},则颜色参数为Color[][] colors={{color1,color2}};<br>
	 *            如果为单轴系列图，假设标题参数为String[] titles={"title1","title2"},则颜色参数为Color[][] colors={{color1,color2}};<br>
	 *            如果为双轴系列图，假设标题参数为String[] titles={"title1","title2"},则颜色参数为Color[][] colors={{color1},{color2}};<br>
	 * @param timeData
	 *            时间系列（时间列数据）
	 * @param tableData
	 *            所有系列（所有列）数据
	 * @param chartType
	 *            每个系列（每列数据）的图形类型 <br>
	 *            示例：<br>
	 *            如果为饼图，假设标题参数为String[] titles={"title1","title2"},则图形类型参数为ChartType[] chartTypes={chartType1};<br>
	 *            如果为单轴系列图，假设标题参数为String[] titles={"title1","title2"},则图形类型参数为ChartType[] chartTypes={chartType1};<br>
	 *            如果为双轴系列图，假设标题参数为String[] titles={"title1","title2"},则图形类型参数为ChartType[] chartTypes={chartType1,chartType2};<br>
	 * @param isSingleAxis
	 *            是否使用一个Y轴
	 * @return
	 */
	public ChartPanel createChartPanel(String mainTitle, String subtitle, String[] titles, String[] units, Color[][] colors, Vector timeData,
			Vector tableData, ChartType[] chartTypes, boolean isSingleAxis) {

		boolean isPie = chartTypes[0] == ChartType.Pie;

		Dataset[] datasets = createDataset(titles, units, timeData, tableData, chartTypes[0], isSingleAxis);

		JFreeChart chart = createChart(mainTitle, chartTypes[0]);
		TextTitle mainTextTitle = chart.getTitle();
		if (mainTextTitle != null) {
			setMainTitleStyle(mainTextTitle);
		}
		if (!Utils.isEmpty(subtitle)) {
			TextTitle subTextTitle = createSubTitle(subtitle);
			chart.addSubtitle(subTextTitle);
		}

		if (isPie) {
			PiePlot plot = (PiePlot) chart.getPlot();
			setPiePlotStyle(plot, timeData, colors[0]);
			plot.setDataset((PieDataset) datasets[0]);
		} else {
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			setCategoryPlotStyle(plot);

			plot.setDomainAxis(new CategoryAxis());
			setDomainAxisStyle(plot.getDomainAxis());

			BlockContainer titleContainer = createTitleContainer();
			CompositeTitle compositeTitle = createCompositeTitle(chart, titleContainer, legendTitlePosition);
			chart.addSubtitle(compositeTitle);

			for (int i = 0; i < datasets.length; i++) {
				plot.setDataset(i, (CategoryDataset) datasets[i]);
				plot.mapDatasetToRangeAxis(i, i);

				plot.setRenderer(i, createRenderer(chartTypes[i], colors[i]));

				plot.setRangeAxis(i, new NumberAxis());
				setValueAxisStyle(plot.getRangeAxis(i), units[i], axisFixedWidth);

				LegendTitle legendTitle = createLegendTitle(plot.getRenderer(i));
				titleContainer.add(legendTitle, i == 0 ? RectangleEdge.LEFT : RectangleEdge.RIGHT);
			}
		}
		ChartPanel chartPanel = createChartPanel(chart);
		return chartPanel;

	}

	/**
	 * 创建图形面板
	 * 
	 * @param mainTitle
	 *            图形主标题
	 * @param titles
	 *            每个系列（每列数据）的标题
	 * @param units
	 *            每个系列（每列数据）的单位
	 * @param colors
	 *            每个系列（每列数据）的颜色
	 * @param timeData
	 *            时间系列（时间列数据）
	 * @param tableData
	 *            所有系列（所有列）数据
	 * @param chartType
	 *            图形类型
	 * @param isSingleAxis
	 *            是否使用一个Y轴
	 * @return
	 */
	public ChartPanel createChartPanel(String mainTitle, String subtitle, String[] titles, String[] units, Color[] colors, Vector timeData, Vector tableData,
			ChartType chartType, boolean isSingleAxis) {
		int len=Utils.isEmpty(titles)?1:titles.length;
		ChartType[] chartTypes = new ChartType[len];
		Arrays.fill(chartTypes, chartType);
		Color[][] colorss = new Color[len][];
		Arrays.fill(colorss, colors);
		return createChartPanel(mainTitle, subtitle, titles, units, colorss, timeData, tableData, chartTypes, isSingleAxis);
	}

	/**
	 * 根据单位检查轴是否能固定宽度<br>
	 * 此方法根据需要重写，不然永远返回false 示例实现：return isPercent(unit);
	 * 
	 * @param unit
	 * @return
	 */
	public boolean checkValueAxisCanFixedWidth(String unit) {
		// 此方法根据需要重写
		// return isPercent(unit);
		return false;
	}

	public boolean isPercent(String unit) {
		if ("%".equals(unit) || "％".equals(unit) || "﹪".equals(unit) || "‰".equals(unit)) {
			return true;
		} else {
			return false;
		}
	}

	public String getString(List list, int index) {
		Object object = list.get(index);
		String result;
		if (object instanceof List) {
			List timeRowList = (List) object;
			result = Utils.toString(timeRowList.get(0));
		} else {
			result = Utils.toString(object);
		}
		return result;
	}

	/*------------------------------分割线开始： 数据--------------------------------*/

	// 根据是否使用同一个Y轴创建数据集
	public Dataset[] createDataset(String[] titles, String[] units, Vector timeData, Vector tableData, ChartType chartType, boolean isSingleAxis) {
		Dataset[] datasets;
		if (chartType == ChartType.Pie) {
			datasets = createPieDataset(timeData, tableData);
		} else if (isSingleAxis) {
			datasets = createDataset(titles, units[0], timeData, tableData);
		} else {
			datasets = createCategoryDataset(titles, units, timeData, tableData);
		}
		return datasets;
	}

	// 创建饼图数据集
	public DefaultPieDataset[] createPieDataset(List timeData, List tableData) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int r = 0; r < timeData.size(); r++) {
			String time = getString(timeData, r);
			String value = getString(tableData, r);
			if (ValidUtil.isPercent(value)) {
				value = value.substring(0, value.length() - 1);
				dataset.setValue(time, Double.parseDouble(value));
			} else if (ValidUtil.isNumber(value)) {
				dataset.setValue(time, Double.parseDouble(value));
			}
		}
		return new DefaultPieDataset[] { dataset };

	}

	// 根据一列或两列数据创建数据集数组(如果有两个列则显示有两个Y轴)
	public DefaultCategoryDataset[] createCategoryDataset(String[] titles, String[] units, Vector timeData, Vector<Vector> tableData) {
		DefaultCategoryDataset[] datasets = new DefaultCategoryDataset[titles.length];
		for (int i = 0; i < datasets.length; i++) {
			datasets[i] = new DefaultCategoryDataset();
			for (int r = 0; r < timeData.size(); r++) {
				String time = getString(timeData, r);
				Vector dataRow = tableData.get(r);
				addValue(datasets[i], titles, units[i], time, dataRow, i);
			}
		}
		return datasets;
	}

	// 根据一列或两列数据创建数据集数(始终显示一个Y轴)
	public DefaultCategoryDataset[] createDataset(String[] titles, String unit, Vector timeData, Vector<Vector> tableData) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int r = 0; r < timeData.size(); r++) {
			String time = getString(timeData, r);
			Vector dataRow = tableData.get(r);
			for (int j = 0; j < dataRow.size(); j++) {
				addValue(dataset, titles, unit, time, dataRow, j);
			}
		}
		return new DefaultCategoryDataset[] { dataset };
	}

	// 向数据集中添加系列（列）中的一个节点数据
	public void addValue(DefaultCategoryDataset dataset, String[] titles, String unit, String time, Vector dataRow, int index) {
		String value = Utils.toString(dataRow.get(index));
		if (ValidUtil.isPercent(value)) {
			value = value.substring(0, value.length() - 1);
			addValue(dataset, titles[index], unit, time, value);
		} else if (ValidUtil.isNumber(value)) {
			addValue(dataset, titles[index], unit, time, value);
		}
	}

	// 向数据集中添加系列（列）中的一个节点数据
	public void addValue(DefaultCategoryDataset dataset, String title, String unit, String time, String value) {
		if (Utils.toString(unit).trim().isEmpty()) {
			dataset.addValue(Double.parseDouble(value), title, time);
		} else {
			dataset.addValue(Double.parseDouble(value), title + "(" + unit + ")", time);
		}
	}

	/*------------------------------分割线开始： 图形面板--------------------------------*/

	// 创建图形面板，已设置样式
	public ChartPanel createChartPanel(JFreeChart chart) {
		ChartPanel chartPanel = new ChartPanel(chart);
		setChartPanelStyle(chartPanel);
		return chartPanel;
	}
	
	private static <T extends EventListener> void removeListenerAll(Component component, Class<T> listener) {
		Class<? extends Component> clazz = component.getClass();
		Method method = null;
		try {
			method = clazz.getMethod("remove" + listener.getSimpleName(), listener);
			method.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		T[] listeners = component.getListeners(listener);
		for (int i = 0; i < listeners.length; i++) {
			try {
				method.invoke(component, listeners[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public final static int MaxScreenWidth = 2560;
	public final static int MaxScreenHeight = 1600;

	// 设置图形面板样式
	public void setChartPanelStyle(final ChartPanel chartPanel) {

		chartPanel.setMinimumDrawWidth(chartMinimumDrawWidth);
		chartPanel.setMaximumDrawWidth(MaxScreenWidth);
		chartPanel.setMinimumDrawHeight(chartMinimumDrawHeight);
		chartPanel.setMaximumDrawHeight(MaxScreenHeight);
		removeListenerAll(chartPanel, MouseListener.class);
		if (useFocusedSeriesShape) {
			chartPanel.addChartMouseListener(new ChartMouseListener() {
				public void chartMouseMoved(ChartMouseEvent event) {
					JFreeChart chart = event.getChart();
					ChartEntity chartEntity = event.getEntity();
					if (chartEntity != null && chartEntity instanceof CategoryItemEntity) {
						CategoryItemEntity categoryItemEntity = (CategoryItemEntity) chartEntity;

						if (chart.getPlot() instanceof CategoryPlot) {
							CategoryPlot plot = (CategoryPlot) chart.getPlot();

							CategoryDataset dataset = categoryItemEntity.getDataset();
							CategoryItemRenderer renderer = plot.getRendererForDataset(dataset);

							if (renderer instanceof FocusedItemShapeLineAndShapeRenderer) {
								FocusedItemShapeLineAndShapeRenderer focusedLineRenderer = (FocusedItemShapeLineAndShapeRenderer) renderer;

								int rowIndex = dataset.getRowIndex(categoryItemEntity.getRowKey());
								int columnIndex = dataset.getColumnIndex(categoryItemEntity.getColumnKey());
								focusedLineRenderer.setFocusedIndex(rowIndex, columnIndex);
							}
						}
					} else {
						if (chart.getPlot() instanceof CategoryPlot) {
							CategoryPlot plot = (CategoryPlot) chart.getPlot();
							for (int i = 0; i < plot.getRendererCount(); i++) {
								CategoryItemRenderer renderer = plot.getRenderer(i);
								if (renderer instanceof FocusedItemShapeLineAndShapeRenderer) {
									FocusedItemShapeLineAndShapeRenderer focusedLineRenderer = (FocusedItemShapeLineAndShapeRenderer) renderer;
									focusedLineRenderer.setFocusedIndex(-1, -1);
								}
							}
						}
					}
				}

				public void chartMouseClicked(ChartMouseEvent event) {
				}
			});
		}
	}

	/*------------------------------分割线开始： 图形--------------------------------*/

	// 根据ChartType创建JFreeChart对象，已设置样式
	public JFreeChart createChart(String title, ChartType chartType) {
		Plot plot = createPlot(chartType);
		JFreeChart chart;
		if (chartType == ChartType.Stacked || chartType == ChartType.Bar || chartType == ChartType.Line) {
			chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, isCreateLegend);
		} else if (chartType == ChartType.Pie) {
			chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, isCreateLegend);
		} else {
			throw new IllegalStateException("参数错误!ChartType暂不支持:" + chartType);
		}
		setChartStyle(chart);
		return chart;
	}

	// 设置图形样式
	public void setChartStyle(JFreeChart chart) {
		getChartDefaultStyle().apply(chart);// 应用默认样式
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(false);
	}

	// 创建统一的默认样式
	public StandardChartTheme createChartTheme() {

		StandardChartTheme defaultChartStyle = new StandardChartTheme("defaultChartStyle");

		// 设置标题字体
		defaultChartStyle.setExtraLargeFont(defaultFont);
		// 设置图例的字体
		defaultChartStyle.setRegularFont(defaultFont);
		// 设置轴向的字体
		defaultChartStyle.setLargeFont(defaultFont);
		defaultChartStyle.setSmallFont(defaultFont);
		defaultChartStyle.setTitlePaint(new Color(51, 51, 51));
		defaultChartStyle.setSubtitlePaint(new Color(85, 85, 85));

		defaultChartStyle.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		defaultChartStyle.setLegendItemPaint(Color.BLACK);//
		defaultChartStyle.setChartBackgroundPaint(Color.WHITE);
		// 绘制器颜色源
		Color[] defaultChartRendererColors = { new Color(31, 129, 188), new Color(241, 92, 128), new Color(124, 181, 236), new Color(102, 172, 204),
				new Color(102, 102, 0), new Color(204, 153, 102), new Color(0, 153, 255), new Color(204, 255, 255), new Color(51, 153, 153),
				new Color(255, 204, 102), new Color(102, 102, 0), new Color(204, 204, 204), new Color(204, 255, 255), new Color(255, 204, 204),
				new Color(255, 255, 204), new Color(255, 153, 204), new Color(51, 0, 0), new Color(0, 51, 102), new Color(0, 153, 102),
				new Color(153, 102, 153), new Color(102, 153, 204), new Color(153, 204, 153), new Color(204, 204, 153), new Color(255, 255, 153),
				new Color(255, 204, 153), new Color(255, 153, 204), new Color(204, 153, 153), new Color(204, 204, 255), new Color(204, 255, 204),
				new Color(153, 204, 153), new Color(255, 204, 102) };// 颜色
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(defaultChartRendererColors, defaultChartRendererColors,
				new Paint[] { Color.WHITE }, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		defaultChartStyle.setDrawingSupplier(drawingSupplier);

		defaultChartStyle.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		defaultChartStyle.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		defaultChartStyle.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		defaultChartStyle.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		defaultChartStyle.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		defaultChartStyle.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		defaultChartStyle.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		defaultChartStyle.setBaselinePaint(Color.WHITE);
		defaultChartStyle.setCrosshairPaint(Color.BLUE);// 不确定含义
		defaultChartStyle.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		defaultChartStyle.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		defaultChartStyle.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		defaultChartStyle.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

		defaultChartStyle.setItemLabelPaint(Color.BLACK);
		defaultChartStyle.setThermometerPaint(Color.WHITE);// 温度计

		return defaultChartStyle;
	}

	/*------------------------------分割线开始：图基--------------------------------*/

	// 根据ChartType创建 图基
	public Plot createPlot(ChartType chartType) {
		Plot plot;
		if (chartType == ChartType.Stacked || chartType == ChartType.Bar || chartType == ChartType.Line) {
			plot = new BairuiCategoryPlot();
		} else if (chartType == ChartType.Pie) {
			plot = new IgnoreKeyPiePlot();
		} else {
			throw new IllegalStateException("参数错误!ChartType暂不支持:" + chartType);
		}
		return plot;
	}

	// 设置图基样式
	public void setPlotStyle(Plot plot) {
		plot.setNoDataMessage(plotNoneDataMessage);
		plot.setInsets(plotInsets);
		plot.setOutlineVisible(plotBorderVisible);
		plot.setOutlinePaint(plotBorderColor);
		plot.setOutlineStroke(plotBorderStroke);
	}

	// 设置系列图基样式
	public void setCategoryPlotStyle(CategoryPlot plot) {
		setPlotStyle(plot);
		plot.setDomainGridlinesVisible(plotDomainGridlineVisible);
		plot.setDomainGridlinePaint(plotDomainGridlineColor);
		plot.setDomainGridlineStroke(plotDomainGridlineStroke);
		plot.setRangeGridlinesVisible(plotRangeGridlineVisible);
		plot.setRangeGridlinePaint(plotRangeGridlineColor);
		plot.setRangeGridlineStroke(plotRangeGridlineStroke);
		plot.setBackgroundPaint(plotBackground);
		plot.setAxisOffset(plotAxisOffset);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
	}

	// 设饼图基样式
	public void setPiePlotStyle(PiePlot plot, Vector timeData, Color[] colors) {
		setPlotStyle(plot);
		for (int i = 0; i < timeData.size(); i++) {
			String time = getString(timeData, i);
			plot.setSectionPaint(time, colors[i]);// 指定分类饼的颜色
			plot.setSectionOutlinePaint(time, plotPieSectionBorderColor);
		}
		plot.setCircular(true);// 是否一定是正圆
		plot.setLabelLinksVisible(plotPieLabelLinksVisible);// 显示数据标签与饼图之间的连线
		plot.setIgnoreNullValues(plotPieIgnoreNullValue);// 是否忽略空数据标签
		plot.setIgnoreZeroValues(plotPieIgnoreZeroValue);// 是否忽略0数据标签
		plot.setSectionOutlinesVisible(plotPieSectionBorderVisible);
		plot.setLabelLinkMargin(plotPieLabelLinkMargin);// 数据标签与图的连接线边距，相当于间接的设置饼图的大小，0为最大，1为最小（一个点）
		plot.setLabelGap(plotPieLabelGap);
		plot.setLabelBackgroundPaint(plotPieLabelBackground);// 数据标签背景色
		plot.setLabelFont(plotPieLabelFont);
		plot.setLabelPaint(plotPieLabelColor);
		plot.setLabelOutlinePaint(plotPieLabelBorderColor);// 数据标签边框颜色
		plot.setLabelShadowPaint(plotPieLabelShadowColor);// 数据标签阴影颜色
		plot.setShadowPaint(plotPieShadowColor);// 去掉饼图阴影颜色
		plot.setInsets(plotPieInsets);

		if (plot instanceof IgnoreKeyPiePlot && plotPieIgnoreKeys != null) {
			IgnoreKeyPiePlot ignoreKeyPiePlot = (IgnoreKeyPiePlot) plot;
			ignoreKeyPiePlot.setIgnoreKeys(plotPieIgnoreKeys);
			if (plotPieIgnoreKeyLabelFormatGenerator != null) {
				plotPieIgnoreKeyLabelFormatGenerator.setIgnoreKeys(plotPieIgnoreKeys);
			}
			if (plotPieIgnoreKeyToolTipFormatGenerator != null) {
				plotPieIgnoreKeyToolTipFormatGenerator.setIgnoreKeys(plotPieIgnoreKeys);
			}
			ignoreKeyPiePlot.setLabelGenerator(plotPieIgnoreKeyLabelFormatGenerator);
			ignoreKeyPiePlot.setToolTipGenerator(plotPieIgnoreKeyToolTipFormatGenerator);
		} else {
			plot.setLabelGenerator(plotPieLabelFormatGenerator);
			plot.setToolTipGenerator(plotPieToolTipFormatGenerator);
		}
		if (plotPieExplodePercentKey != null) {
			plot.setExplodePercent(plotPieExplodePercentKey, plotPieExplodePercentValue);
		}
		plot.setSimpleLabels(plotPieIsSimpleLabels);
		plot.setInteriorGap(plotPieInteriorGap);
		plot.setStartAngle(plotPieStartAngle);
		plot.setDirection(plotPieDirection);
		plot.setLegendItemShape(plotPieLegendItemShape);
	}

	/*------------------------------分割线开始：标题--------------------------------*/
	// 创建主标题，已设置样式
	public TextTitle createMainTitle(String text) {
		TextTitle title = new TextTitle(text);
		setMainTitleStyle(title);
		return title;
	}

	// 创建副标题，已设置样式
	public TextTitle createSubTitle(String text) {
		TextTitle title = new TextTitle(text);
		setSubTitleStyle(title);
		return title;
	}

	// 创建图例标题，已设置样式
	public LegendTitle createLegendTitle(LegendItemSource renderer) {
		LegendTitle title = new LegendTitle(renderer);
		setLegendTitleStyle(title);
		return title;
	}

	// 创建组合标题
	public CompositeTitle createCompositeTitle(JFreeChart chart, BlockContainer titleContainer, RectangleEdge oosition) {
		CompositeTitle compositeTitle = new CompositeTitle(titleContainer);
		compositeTitle.setPosition(oosition);
		return compositeTitle;
	}

	// 创建标题容器
	public BlockContainer createTitleContainer() {
		BlockContainer blockContainer = new BlockContainer(new BorderArrangement());
		return blockContainer;
	}

	// 设置主标题样式
	public void setMainTitleStyle(TextTitle title) {
		title.setPaint(titleColor);
		title.setFont(titleFont);
		title.setBackgroundPaint(titleBackground);
		title.setHorizontalAlignment(titleHorizontalAlignment);
		title.setMargin(titleMargin);
		title.setPadding(titlePadding);
		title.setPosition(titlePosition);
	}

	// 设置副标题样式
	public void setSubTitleStyle(TextTitle title) {
		title.setPaint(subtitleColor);
		title.setFont(subtitleFont);
		title.setBackgroundPaint(subtitleBackground);
		title.setHorizontalAlignment(subtitleHorizontalAlignment);
		title.setMargin(subtitleMargin);
		title.setPadding(subtitlePadding);
		title.setPosition(subtitlePosition);
	}

	// 设置图例标题样式
	public void setLegendTitleStyle(LegendTitle title) {
		title.setItemPaint(legendTitleColor);
		title.setItemFont(legendTitleFont);
		title.setBackgroundPaint(legendTitleBackground);
		title.setHorizontalAlignment(legendTitleHorizontalAlignment);
		title.setMargin(legendTitleMargin);// 增加与图形的间隙
		title.setPadding(legendTitlePadding);// 增加与图形的间隙
	}

	/*------------------------------分割线开始：轴--------------------------------*/

	// 设置轴样式
	public void setAxisStyle(Axis axis, boolean isFixedWidth, int width) {
		axis.setTickLabelPaint(axisLabelColor);
		axis.setTickLabelFont(axisLabelFont);
		axis.setTickLabelInsets(axisLabelInsets);
		axis.setTickMarksVisible(axisTickMarkVisible);
		axis.setTickMarkPaint(axisTickMarkColor);
		axis.setAxisLineVisible(axisLineVisible);
		axis.setAxisLinePaint(axisLineColor);
		if (isFixedWidth) {
			axis.setFixedDimension(width);// 固定轴宽度
		}
	}

	// 设置横轴（X轴）样式
	public void setDomainAxisStyle(CategoryAxis axis) {
		setAxisStyle(axis, false, 0);
		axis.setCategoryLabelPositions(axisDomainLabelPositions);
		axis.setTickLabelsVisible(axisDomainLabelVisible);
	}

	// 设置竖轴（Y轴）样式
	public void setValueAxisStyle(ValueAxis axis, String unit, int width) {
		setAxisStyle(axis, axisIsFixedWidth || checkValueAxisCanFixedWidth(unit), width);
		axis.setUpperMargin(axisUpperMargin);// 设置顶部Y坐标轴间距,防止数据无法显示
		axis.setLowerMargin(axisLowerMargin);// 设置顶部Y坐标轴间距,防止数据无法显示
		axis.setTickLabelsVisible(axisRangeLabelVisible);
		if (axis instanceof NumberAxis) {
			NumberAxis numberAxis = (NumberAxis) axis;
			numberAxis.setNumberFormatOverride(chartNumberFormat);// 解决数值全部为0时出现小数位很长的情况
			numberAxis.setAutoRangeIncludesZero(axisNumberAutoRangeIncludesZero);// 不从0开始
		}
	}

	/*------------------------------分割线开始：渲染器--------------------------------*/

	// 根据图形类型创建渲染器
	public CategoryItemRenderer createRenderer(ChartType chartType, Color[] color) {
		CategoryItemRenderer renderer;
		if (chartType == ChartType.Stacked) {
			renderer = createStackedRenderer(color);
		} else if (chartType == ChartType.Bar) {
			renderer = createBarRenderer(color);
		} else {
			renderer = createLineRenderer(color);
		}
		return renderer;
	}

	// 创建堆积图渲染器
	public CategoryItemRenderer createStackedRenderer(Color[] color) {
		StackedBarRenderer renderer = new StackedBarRenderer();
		setBarRendererStyle(color, renderer);
		return renderer;
	}

	// 创建线图渲染器
	public CategoryItemRenderer createLineRenderer(Color[] color) {
		FocusedItemShapeLineAndShapeRenderer renderer = new FocusedItemShapeLineAndShapeRenderer();
		setCategoryItemRendererStyle(color, renderer);
		renderer.setBaseShapesVisible(rendererSeriesShapeVisible);
		renderer.setUseFocusedSeriesShape(useFocusedSeriesShape);
		if (useFocusedSeriesShape) {
			for (int i = 0; i < color.length; i++) {
				renderer.setFocusedSeriesShape(i, rendererFocusedSeriesShape);
			}
		}
		return renderer;
	}

	// 创建柱状图渲染器
	public CategoryItemRenderer createBarRenderer(Color[] color) {
		BarRenderer renderer = new BarRenderer();
		setBarRendererStyle(color, renderer);
		return renderer;
	}

	// 设置抽象系列渲染器样式
	public void setCategoryItemRendererStyle(Color[] color, AbstractCategoryItemRenderer renderer) {
		renderer.setBaseToolTipGenerator(rendererToolTipFormatGenerator);// 只显示数字
		renderer.setBaseItemLabelsVisible(rendererItemLabelsVisible);
		renderer.setBaseItemLabelGenerator(rendererLabelFormatGenerator);
		for (int i = 0; i < color.length; i++) {
			renderer.setSeriesCreateEntities(i, true);
			renderer.setSeriesFillPaint(i, color[i]);
			renderer.setSeriesPaint(i, color[i]);
			renderer.setLegendTextFont(i, legendTitleFont);
			renderer.setSeriesShape(i, rendererSeriesShape);
			renderer.setSeriesStroke(i, rendererSeriesStroke);
		}
	}

	// 设置柱状图渲染器样式
	public void setBarRendererStyle(Color[] color, BarRenderer renderer) {
		setCategoryItemRendererStyle(color, renderer);
		for (int i = 0; i < color.length; i++) {
			renderer.setLegendShape(i, rendererBarLegendShape);// 图例形状
		}
		renderer.setShadowVisible(rendererBarShodowVisible);
		renderer.setShadowXOffset(rendererBarShadowXOffset);
		renderer.setShadowYOffset(rendererBarShadowYOffset);
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setMaximumBarWidth(rendererBarMaximumWidth);
		renderer.setItemMargin(rendererBarItemMargin);
	}

	/*------------------------------分割线开始：get 和 set方法--------------------------------*/

	public void setPlotGridlineVisible(boolean gridlineVisible) {
		setPlotDomainGridlineVisible(gridlineVisible);
		setPlotRangeGridlineVisible(gridlineVisible);
	}

	public void setPlotGridlineColor(Paint gridlineColor) {
		setPlotDomainGridlineColor(gridlineColor);
		setPlotRangeGridlineColor(gridlineColor);
	}

	public void setPlotGridlineStroke(Stroke gridlineStroke) {
		setPlotDomainGridlineStroke(gridlineStroke);
		setPlotRangeGridlineStroke(gridlineStroke);
	}

	public void setPlotPieExplodePercent(String plotPieExplodePercentKey, double plotPieExplodePercentValue) {
		setPlotPieExplodePercentKey(plotPieExplodePercentKey);
		setPlotPieExplodePercentValue(plotPieExplodePercentValue);
	}

	public void setAxisLabelVisible(boolean axisLabelVisible) {
		setAxisDomainLabelVisible(axisLabelVisible);
		setAxisRangeLabelVisible(axisLabelVisible);
	}

	public StandardChartTheme getChartDefaultStyle() {
		if (chartDefaultStyle == null) {
			chartDefaultStyle = createChartTheme();
		}
		return chartDefaultStyle;
	}

	public void setChartDefaultStyle(StandardChartTheme chartDefaultStyle) {
		this.chartDefaultStyle = chartDefaultStyle;
	}

	public int getChartMinimumDrawHeight() {
		return chartMinimumDrawHeight;
	}

	public int getChartMinimumDrawWidth() {
		return chartMinimumDrawWidth;
	}

	public DecimalFormat getChartNumberFormat() {
		return chartNumberFormat;
	}

	public void setChartNumberFormat(DecimalFormat chartNumberFormat) {
		this.chartNumberFormat = chartNumberFormat;
	}

	public DecimalFormat getChartPercentFormat() {
		return chartPercentFormat;
	}

	public void setChartPercentFormat(DecimalFormat chartPercentFormat) {
		this.chartPercentFormat = chartPercentFormat;
	}

	public boolean isCreateLegend() {
		return isCreateLegend;
	}

	public void setCreateLegend(boolean isCreateLegend) {
		this.isCreateLegend = isCreateLegend;
	}

	public Paint getPlotBackground() {
		return plotBackground;
	}

	public void setPlotBackground(Paint plotBackground) {
		this.plotBackground = plotBackground;
	}

	public RectangleInsets getPlotInsets() {
		return plotInsets;
	}

	public void setPlotInsets(RectangleInsets plotInsets) {
		this.plotInsets = plotInsets;
	}

	public boolean isPlotBorderVisible() {
		return plotBorderVisible;
	}

	public void setPlotBorderVisible(boolean plotBorderVisible) {
		this.plotBorderVisible = plotBorderVisible;
	}

	public Stroke getPlotBorderStroke() {
		return plotBorderStroke;
	}

	public void setPlotBorderStroke(Stroke plotBorderStroke) {
		this.plotBorderStroke = plotBorderStroke;
	}

	public Paint getPlotBorderColor() {
		return plotBorderColor;
	}

	public void setPlotBorderColor(Paint plotBorderColor) {
		this.plotBorderColor = plotBorderColor;
	}

	public String getPlotNoneDataMessage() {
		return plotNoneDataMessage;
	}

	public void setPlotNoneDataMessage(String plotNoneDataMessage) {
		this.plotNoneDataMessage = plotNoneDataMessage;
	}

	public boolean isPlotDomainGridlineVisible() {
		return plotDomainGridlineVisible;
	}

	public void setPlotDomainGridlineVisible(boolean plotDomainGridlineVisible) {
		this.plotDomainGridlineVisible = plotDomainGridlineVisible;
	}

	public Paint getPlotDomainGridlineColor() {
		return plotDomainGridlineColor;
	}

	public void setPlotDomainGridlineColor(Paint plotDomainGridlineColor) {
		this.plotDomainGridlineColor = plotDomainGridlineColor;
	}

	public Stroke getPlotDomainGridlineStroke() {
		return plotDomainGridlineStroke;
	}

	public void setPlotDomainGridlineStroke(Stroke plotDomainGridlineStroke) {
		this.plotDomainGridlineStroke = plotDomainGridlineStroke;
	}

	public boolean isPlotRangeGridlineVisible() {
		return plotRangeGridlineVisible;
	}

	public void setPlotRangeGridlineVisible(boolean plotRangeGridlineVisible) {
		this.plotRangeGridlineVisible = plotRangeGridlineVisible;
	}

	public Paint getPlotRangeGridlineColor() {
		return plotRangeGridlineColor;
	}

	public void setPlotRangeGridlineColor(Paint plotRangeGridlineColor) {
		this.plotRangeGridlineColor = plotRangeGridlineColor;
	}

	public Stroke getPlotRangeGridlineStroke() {
		return plotRangeGridlineStroke;
	}

	public void setPlotRangeGridlineStroke(Stroke plotRangeGridlineStroke) {
		this.plotRangeGridlineStroke = plotRangeGridlineStroke;
	}

	public RectangleInsets getPlotAxisOffset() {
		return plotAxisOffset;
	}

	public void setPlotAxisOffset(RectangleInsets plotAxisOffset) {
		this.plotAxisOffset = plotAxisOffset;
	}

	public RectangleInsets getPlotPieInsets() {
		return plotPieInsets;
	}

	public void setPlotPieInsets(RectangleInsets plotPieInsets) {
		this.plotPieInsets = plotPieInsets;
	}

	public boolean isPlotPieIgnoreNullValue() {
		return plotPieIgnoreNullValue;
	}

	public void setPlotPieIgnoreNullValue(boolean plotPieIgnoreNullValue) {
		this.plotPieIgnoreNullValue = plotPieIgnoreNullValue;
	}

	public boolean isPlotPieIgnoreZeroValue() {
		return plotPieIgnoreZeroValue;
	}

	public void setPlotPieIgnoreZeroValue(boolean plotPieIgnoreZeroValue) {
		this.plotPieIgnoreZeroValue = plotPieIgnoreZeroValue;
	}

	public boolean isPlotPieSectionBorderVisible() {
		return plotPieSectionBorderVisible;
	}

	public void setPlotPieSectionBorderVisible(boolean plotPieSectionBorderVisible) {
		this.plotPieSectionBorderVisible = plotPieSectionBorderVisible;
	}

	public Paint getPlotPieSectionBorderColor() {
		return plotPieSectionBorderColor;
	}

	public void setPlotPieSectionBorderColor(Paint plotPieSectionBorderColor) {
		this.plotPieSectionBorderColor = plotPieSectionBorderColor;
	}

	public boolean isPlotPieLabelLinksVisible() {
		return plotPieLabelLinksVisible;
	}

	public void setPlotPieLabelLinksVisible(boolean plotPieLabelLinksVisible) {
		this.plotPieLabelLinksVisible = plotPieLabelLinksVisible;
	}

	public double getPlotPieLabelLinkMargin() {
		return plotPieLabelLinkMargin;
	}

	public void setPlotPieLabelLinkMargin(double plotPieLabelLinkMargin) {
		this.plotPieLabelLinkMargin = plotPieLabelLinkMargin;
	}

	public double getPlotPieLabelGap() {
		return plotPieLabelGap;
	}

	public void setPlotPieLabelGap(double plotPieLabelGap) {
		this.plotPieLabelGap = plotPieLabelGap;
	}

	public Paint getPlotPieLabelBackground() {
		return plotPieLabelBackground;
	}

	public void setPlotPieLabelBackground(Paint plotPieLabelBackground) {
		this.plotPieLabelBackground = plotPieLabelBackground;
	}

	public Font getPlotPieLabelFont() {
		return plotPieLabelFont;
	}

	public void setPlotPieLabelFont(Font plotPieLabelFont) {
		this.plotPieLabelFont = plotPieLabelFont;
	}

	public Paint getPlotPieLabelColor() {
		return plotPieLabelColor;
	}

	public void setPlotPieLabelColor(Paint plotPieLabelColor) {
		this.plotPieLabelColor = plotPieLabelColor;
	}

	public Paint getPlotPieLabelBorderColor() {
		return plotPieLabelBorderColor;
	}

	public void setPlotPieLabelBorderColor(Paint plotPieLabelBorderColor) {
		this.plotPieLabelBorderColor = plotPieLabelBorderColor;
	}

	public Paint getPlotPieLabelShadowColor() {
		return plotPieLabelShadowColor;
	}

	public void setPlotPieLabelShadowColor(Paint plotPieLabelShadowColor) {
		this.plotPieLabelShadowColor = plotPieLabelShadowColor;
	}

	public Paint getPlotPieShadowColor() {
		return plotPieShadowColor;
	}

	public void setPlotPieShadowColor(Paint plotPieShadowColor) {
		this.plotPieShadowColor = plotPieShadowColor;
	}

	public double getPlotPieShadowXOffset() {
		return plotPieShadowXOffset;
	}

	public void setPlotPieShadowXOffset(double plotPieShadowXOffset) {
		this.plotPieShadowXOffset = plotPieShadowXOffset;
	}

	public double getPlotPieShadowYOffset() {
		return plotPieShadowYOffset;
	}

	public void setPlotPieShadowYOffset(double plotPieShadowYOffset) {
		this.plotPieShadowYOffset = plotPieShadowYOffset;
	}

	public String getPlotPieExplodePercentKey() {
		return plotPieExplodePercentKey;
	}

	public void setPlotPieExplodePercentKey(String plotPieExplodePercentKey) {
		this.plotPieExplodePercentKey = plotPieExplodePercentKey;
	}

	public double getPlotPieExplodePercentValue() {
		return plotPieExplodePercentValue;
	}

	public void setPlotPieExplodePercentValue(double plotPieExplodePercentValue) {
		this.plotPieExplodePercentValue = plotPieExplodePercentValue;
	}

	public boolean isPlotPieIsSimpleLabels() {
		return plotPieIsSimpleLabels;
	}

	public void setPlotPieIsSimpleLabels(boolean plotPieIsSimpleLabels) {
		this.plotPieIsSimpleLabels = plotPieIsSimpleLabels;
	}

	public String[] getPlotPieIgnoreKeys() {
		return plotPieIgnoreKeys;
	}

	public void setPlotPieIgnoreKeys(String[] plotPieIgnoreKeys) {
		this.plotPieIgnoreKeys = plotPieIgnoreKeys;
	}

	public double getPlotPieInteriorGap() {
		return plotPieInteriorGap;
	}

	public void setPlotPieInteriorGap(double plotPieInteriorGap) {
		this.plotPieInteriorGap = plotPieInteriorGap;
	}

	public int getPlotPieStartAngle() {
		return plotPieStartAngle;
	}

	public void setPlotPieStartAngle(int plotPieStartAngle) {
		this.plotPieStartAngle = plotPieStartAngle;
	}

	public Rotation getPlotPieDirection() {
		return plotPieDirection;
	}

	public void setPlotPieDirection(Rotation plotPieDirection) {
		this.plotPieDirection = plotPieDirection;
	}

	public Shape getPlotPieLegendItemShape() {
		return plotPieLegendItemShape;
	}

	public void setPlotPieLegendItemShape(Shape plotPieLegendItemShape) {
		this.plotPieLegendItemShape = plotPieLegendItemShape;
	}

	public PieSectionLabelGenerator getPlotPieLabelFormatGenerator() {
		return plotPieLabelFormatGenerator;
	}

	public void setPlotPieLabelFormatGenerator(PieSectionLabelGenerator plotPieLabelFormatGenerator) {
		this.plotPieLabelFormatGenerator = plotPieLabelFormatGenerator;
	}

	public PieToolTipGenerator getPlotPieToolTipFormatGenerator() {
		return plotPieToolTipFormatGenerator;
	}

	public void setPlotPieToolTipFormatGenerator(PieToolTipGenerator plotPieToolTipFormatGenerator) {
		this.plotPieToolTipFormatGenerator = plotPieToolTipFormatGenerator;
	}

	public IgnoreKeyPieSectionLabelGenerator getPlotPieIgnoreKeyLabelFormatGenerator() {
		return plotPieIgnoreKeyLabelFormatGenerator;
	}

	public void setPlotPieIgnoreKeyLabelFormatGenerator(IgnoreKeyPieSectionLabelGenerator plotPieIgnoreKeyLabelFormatGenerator) {
		this.plotPieIgnoreKeyLabelFormatGenerator = plotPieIgnoreKeyLabelFormatGenerator;
	}

	public IgnoreKeyPieToolTipGenerator getPlotPieIgnoreKeyToolTipFormatGenerator() {
		return plotPieIgnoreKeyToolTipFormatGenerator;
	}

	public void setPlotPieIgnoreKeyToolTipFormatGenerator(IgnoreKeyPieToolTipGenerator plotPieIgnoreKeyToolTipFormatGenerator) {
		this.plotPieIgnoreKeyToolTipFormatGenerator = plotPieIgnoreKeyToolTipFormatGenerator;
	}

	public Paint getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(Paint titleColor) {
		this.titleColor = titleColor;
	}

	public Paint getTitleBackground() {
		return titleBackground;
	}

	public void setTitleBackground(Paint titleBackground) {
		this.titleBackground = titleBackground;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public RectangleEdge getTitlePosition() {
		return titlePosition;
	}

	public void setTitlePosition(RectangleEdge titlePosition) {
		this.titlePosition = titlePosition;
	}

	public RectangleInsets getTitleMargin() {
		return titleMargin;
	}

	public void setTitleMargin(RectangleInsets titleMargin) {
		this.titleMargin = titleMargin;
	}

	public RectangleInsets getTitlePadding() {
		return titlePadding;
	}

	public void setTitlePadding(RectangleInsets titlePadding) {
		this.titlePadding = titlePadding;
	}

	public HorizontalAlignment getTitleHorizontalAlignment() {
		return titleHorizontalAlignment;
	}

	public void setTitleHorizontalAlignment(HorizontalAlignment titleHorizontalAlignment) {
		this.titleHorizontalAlignment = titleHorizontalAlignment;
	}

	public Paint getSubtitleColor() {
		return subtitleColor;
	}

	public void setSubtitleColor(Paint subtitleColor) {
		this.subtitleColor = subtitleColor;
	}

	public Paint getSubtitleBackground() {
		return subtitleBackground;
	}

	public void setSubtitleBackground(Paint subtitleBackground) {
		this.subtitleBackground = subtitleBackground;
	}

	public Font getSubtitleFont() {
		return subtitleFont;
	}

	public void setSubtitleFont(Font subtitleFont) {
		this.subtitleFont = subtitleFont;
	}

	public RectangleEdge getSubtitlePosition() {
		return subtitlePosition;
	}

	public void setSubtitlePosition(RectangleEdge subtitlePosition) {
		this.subtitlePosition = subtitlePosition;
	}

	public RectangleInsets getSubtitleMargin() {
		return subtitleMargin;
	}

	public void setSubtitleMargin(RectangleInsets subtitleMargin) {
		this.subtitleMargin = subtitleMargin;
	}

	public RectangleInsets getSubtitlePadding() {
		return subtitlePadding;
	}

	public void setSubtitlePadding(RectangleInsets subtitlePadding) {
		this.subtitlePadding = subtitlePadding;
	}

	public HorizontalAlignment getSubtitleHorizontalAlignment() {
		return subtitleHorizontalAlignment;
	}

	public void setSubtitleHorizontalAlignment(HorizontalAlignment subtitleHorizontalAlignment) {
		this.subtitleHorizontalAlignment = subtitleHorizontalAlignment;
	}

	public Paint getLegendTitleColor() {
		return legendTitleColor;
	}

	public void setLegendTitleColor(Paint legendTitleColor) {
		this.legendTitleColor = legendTitleColor;
	}

	public Paint getLegendTitleBackground() {
		return legendTitleBackground;
	}

	public void setLegendTitleBackground(Paint legendTitleBackground) {
		this.legendTitleBackground = legendTitleBackground;
	}

	public Font getLegendTitleFont() {
		return legendTitleFont;
	}

	public void setLegendTitleFont(Font legendTitleFont) {
		this.legendTitleFont = legendTitleFont;
	}

	public RectangleEdge getLegendTitlePosition() {
		return legendTitlePosition;
	}

	public void setLegendTitlePosition(RectangleEdge legendTitlePosition) {
		this.legendTitlePosition = legendTitlePosition;
	}

	public RectangleInsets getLegendTitleMargin() {
		return legendTitleMargin;
	}

	public void setLegendTitleMargin(RectangleInsets legendTitleMargin) {
		this.legendTitleMargin = legendTitleMargin;
	}

	public RectangleInsets getLegendTitlePadding() {
		return legendTitlePadding;
	}

	public void setLegendTitlePadding(RectangleInsets legendTitlePadding) {
		this.legendTitlePadding = legendTitlePadding;
	}

	public HorizontalAlignment getLegendTitleHorizontalAlignment() {
		return legendTitleHorizontalAlignment;
	}

	public void setLegendTitleHorizontalAlignment(HorizontalAlignment legendTitleHorizontalAlignment) {
		this.legendTitleHorizontalAlignment = legendTitleHorizontalAlignment;
	}

	public Paint getAxisLineColor() {
		return axisLineColor;
	}

	public void setAxisLineColor(Paint axisLineColor) {
		this.axisLineColor = axisLineColor;
	}

	public boolean isAxisDomainLabelVisible() {
		return axisDomainLabelVisible;
	}

	public void setAxisDomainLabelVisible(boolean axisDomainLabelVisible) {
		this.axisDomainLabelVisible = axisDomainLabelVisible;
	}

	public boolean isAxisRangeLabelVisible() {
		return axisRangeLabelVisible;
	}

	public void setAxisRangeLabelVisible(boolean axisRangeLabelVisible) {
		this.axisRangeLabelVisible = axisRangeLabelVisible;
	}

	public Paint getAxisLabelColor() {
		return axisLabelColor;
	}

	public void setAxisLabelColor(Paint axisLabelColor) {
		this.axisLabelColor = axisLabelColor;
	}

	public Font getAxisLabelFont() {
		return axisLabelFont;
	}

	public void setAxisLabelFont(Font axisLabelFont) {
		this.axisLabelFont = axisLabelFont;
	}

	public RectangleInsets getAxisLabelInsets() {
		return axisLabelInsets;
	}

	public void setAxisLabelInsets(RectangleInsets axisLabelInsets) {
		this.axisLabelInsets = axisLabelInsets;
	}

	public boolean isAxisTickMarkVisible() {
		return axisTickMarkVisible;
	}

	public void setAxisTickMarkVisible(boolean axisTickMarkVisible) {
		this.axisTickMarkVisible = axisTickMarkVisible;
	}

	public Paint getAxisTickMarkColor() {
		return axisTickMarkColor;
	}

	public void setAxisTickMarkColor(Paint axisTickMarkColor) {
		this.axisTickMarkColor = axisTickMarkColor;
	}

	public double getAxisUpperMargin() {
		return axisUpperMargin;
	}

	public void setAxisUpperMargin(double axisUpperMargin) {
		this.axisUpperMargin = axisUpperMargin;
	}

	public double getAxisLowerMargin() {
		return axisLowerMargin;
	}

	public void setAxisLowerMargin(double axisLowerMargin) {
		this.axisLowerMargin = axisLowerMargin;
	}

	public boolean isAxisNumberAutoRangeIncludesZero() {
		return axisNumberAutoRangeIncludesZero;
	}

	public void setAxisNumberAutoRangeIncludesZero(boolean axisNumberAutoRangeIncludesZero) {
		this.axisNumberAutoRangeIncludesZero = axisNumberAutoRangeIncludesZero;
	}

	public CategoryLabelPositions getAxisDomainLabelPositions() {
		return axisDomainLabelPositions;
	}

	public void setAxisDomainLabelPositions(CategoryLabelPositions axisDomainLabelPositions) {
		this.axisDomainLabelPositions = axisDomainLabelPositions;
	}

	public boolean isAxisLineVisible() {
		return axisLineVisible;
	}

	public void setAxisLineVisible(boolean axisLineVisible) {
		this.axisLineVisible = axisLineVisible;
	}

	public boolean isAxisIsFixedWidth() {
		return axisIsFixedWidth;
	}

	public void setAxisIsFixedWidth(boolean axisIsFixedWidth) {
		this.axisIsFixedWidth = axisIsFixedWidth;
	}

	public int getAxisFixedWidth() {
		return axisFixedWidth;
	}

	public void setAxisFixedWidth(int axisFixedWidth) {
		this.axisFixedWidth = axisFixedWidth;
	}

	public boolean isRendererSeriesShapeVisible() {
		return rendererSeriesShapeVisible;
	}

	public void setRendererSeriesShapeVisible(boolean rendererSeriesShapeVisible) {
		this.rendererSeriesShapeVisible = rendererSeriesShapeVisible;
	}

	public Shape getRendererSeriesShape() {
		return rendererSeriesShape;
	}

	public void setRendererSeriesShape(Shape rendererSeriesShape) {
		this.rendererSeriesShape = rendererSeriesShape;
	}

	public boolean isUseFocusedSeriesShape() {
		return useFocusedSeriesShape;
	}

	public void setUseFocusedSeriesShape(boolean useFocusedSeriesShape) {
		this.useFocusedSeriesShape = useFocusedSeriesShape;
	}

	public Shape getRendererFocusedSeriesShape() {
		return rendererFocusedSeriesShape;
	}

	public void setRendererFocusedSeriesShape(Shape rendererFocusedSeriesShape) {
		this.rendererFocusedSeriesShape = rendererFocusedSeriesShape;
	}

	public Stroke getRendererSeriesStroke() {
		return rendererSeriesStroke;
	}

	public void setRendererSeriesStroke(Stroke rendererSeriesStroke) {
		this.rendererSeriesStroke = rendererSeriesStroke;
	}

	public boolean isRendererItemLabelsVisible() {
		return rendererItemLabelsVisible;
	}

	public void setRendererItemLabelsVisible(boolean rendererItemLabelsVisible) {
		this.rendererItemLabelsVisible = rendererItemLabelsVisible;
	}

	public Shape getRendererBarLegendShape() {
		return rendererBarLegendShape;
	}

	public void setRendererBarLegendShape(Shape rendererBarLegendShape) {
		this.rendererBarLegendShape = rendererBarLegendShape;
	}

	public double getRendererBarMaximumWidth() {
		return rendererBarMaximumWidth;
	}

	public void setRendererBarMaximumWidth(double rendererBarMaximumWidth) {
		this.rendererBarMaximumWidth = rendererBarMaximumWidth;
	}

	public double getRendererBarItemMargin() {
		return rendererBarItemMargin;
	}

	public void setRendererBarItemMargin(double rendererBarItemMargin) {
		this.rendererBarItemMargin = rendererBarItemMargin;
	}

	public boolean isRendererBarShodowVisible() {
		return rendererBarShodowVisible;
	}

	public void setRendererBarShodowVisible(boolean rendererBarShodowVisible) {
		this.rendererBarShodowVisible = rendererBarShodowVisible;
	}

	public double getRendererBarShadowXOffset() {
		return rendererBarShadowXOffset;
	}

	public void setRendererBarShadowXOffset(double rendererBarShadowXOffset) {
		this.rendererBarShadowXOffset = rendererBarShadowXOffset;
	}

	public double getRendererBarShadowYOffset() {
		return rendererBarShadowYOffset;
	}

	public void setRendererBarShadowYOffset(double rendererBarShadowYOffset) {
		this.rendererBarShadowYOffset = rendererBarShadowYOffset;
	}

	public CategoryItemLabelGenerator getRendererLabelFormatGenerator() {
		return rendererLabelFormatGenerator;
	}

	public void setRendererLabelFormatGenerator(CategoryItemLabelGenerator rendererLabelFormatGenerator) {
		this.rendererLabelFormatGenerator = rendererLabelFormatGenerator;
	}

	public CategoryToolTipGenerator getRendererToolTipFormatGenerator() {
		return rendererToolTipFormatGenerator;
	}

	public void setRendererToolTipFormatGenerator(CategoryToolTipGenerator rendererToolTipFormatGenerator) {
		this.rendererToolTipFormatGenerator = rendererToolTipFormatGenerator;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}
}
