package com.xxbs.v1.util.chart;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryCrosshairState;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;

/**
 * �������X�ܴ�ֱ��ʾʱ��Y���ǩ��ʾ�����������
 * 
 * @author zsk
 * @date 2015.04.01
 * 
 */
@SuppressWarnings("rawtypes")
public class BairuiCategoryPlot extends CategoryPlot {

	private static final long serialVersionUID = 1L;

	public BairuiCategoryPlot() {
		super();
	}

	public BairuiCategoryPlot(CategoryDataset dataset, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryItemRenderer renderer) {
		super(dataset, domainAxis, rangeAxis, renderer);
	}

	/**
	 * Draws the plot on a Java 2D graphics device (such as the screen or a printer).
	 * <P>
	 * At your option, you may supply an instance of {@link PlotRenderingInfo}. If you do, it will be populated with information about the drawing, including
	 * various plot dimensions and tooltip info.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area within which the plot (including axes) should be drawn.
	 * @param anchor
	 *            the anchor point (<code>null</code> permitted).
	 * @param parentState
	 *            the state from the parent plot, if there is one.
	 * @param state
	 *            collects info as the chart is drawn (possibly <code>null</code>).
	 */
	@Override
	public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo state) {

		// if the plot area is too small, just return...
		boolean b1 = (area.getWidth() <= MINIMUM_WIDTH_TO_DRAW);
		boolean b2 = (area.getHeight() <= MINIMUM_HEIGHT_TO_DRAW);
		if (b1 || b2) {
			return;
		}

		// record the plot area...
		if (state == null) {
			// if the incoming state is null, no information will be passed
			// back to the caller - but we create a temporary state to record
			// the plot area, since that is used later by the axes
			state = new PlotRenderingInfo(null);
		}
		state.setPlotArea(area);

		// adjust the drawing area for the plot insets (if any)...
		RectangleInsets insets = getInsets();
		insets.trim(area);

		// calculate the data area...
		AxisSpace space = calculateAxisSpace(g2, area);

		// 2015.04.01 zsk ���Y����ʾ�����Ƶ����� start
		space.setLeft(0.0);
		space.setRight(0.0);
		// 2015.04.01 zsk ���Y����ʾ�����Ƶ����� end

		Rectangle2D dataArea = space.shrink(area, null);

		// 2015.04.01 zsk ���Y����ʾ�����Ƶ����� start
		space = calculateRangeAxisSpace(g2, dataArea, space);
		dataArea = space.shrink(area, null);
		// 2015.04.01 zsk ���Y����ʾ�����Ƶ����� end

		this.getAxisOffset().trim(dataArea);
		dataArea = integerise(dataArea);
		if (dataArea.isEmpty()) {
			return;
		}
		state.setDataArea(dataArea);
		createAndAddEntity((Rectangle2D) dataArea.clone(), state, null, null);

		// if there is a renderer, it draws the background, otherwise use the
		// default background...
		if (getRenderer() != null) {
			getRenderer().drawBackground(g2, this, dataArea);
		} else {
			drawBackground(g2, dataArea);
		}

		Map axisStateMap = drawAxes(g2, area, dataArea, state);

		// the anchor point is typically the point where the mouse last
		// clicked - the crosshairs will be driven off this point...
		if (anchor != null && !dataArea.contains(anchor)) {
			anchor = ShapeUtilities.getPointInRectangle(anchor.getX(), anchor.getY(), dataArea);
		}
		CategoryCrosshairState crosshairState = new CategoryCrosshairState();
		crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
		crosshairState.setAnchor(anchor);

		// specify the anchor X and Y coordinates in Java2D space, for the
		// cases where these are not updated during rendering (i.e. no lock
		// on data)
		crosshairState.setAnchorX(Double.NaN);
		crosshairState.setAnchorY(Double.NaN);
		if (anchor != null) {
			ValueAxis rangeAxis = getRangeAxis();
			if (rangeAxis != null) {
				double y;
				if (getOrientation() == PlotOrientation.VERTICAL) {
					y = rangeAxis.java2DToValue(anchor.getY(), dataArea, getRangeAxisEdge());
				} else {
					y = rangeAxis.java2DToValue(anchor.getX(), dataArea, getRangeAxisEdge());
				}
				crosshairState.setAnchorY(y);
			}
		}
		crosshairState.setRowKey(getDomainCrosshairRowKey());
		crosshairState.setColumnKey(getDomainCrosshairColumnKey());
		crosshairState.setCrosshairY(getRangeCrosshairValue());

		// don't let anyone draw outside the data area
		Shape savedClip = g2.getClip();
		g2.clip(dataArea);

		drawDomainGridlines(g2, dataArea);

		AxisState rangeAxisState = (AxisState) axisStateMap.get(getRangeAxis());
		if (rangeAxisState == null) {
			if (parentState != null) {
				rangeAxisState = (AxisState) parentState.getSharedAxisStates().get(getRangeAxis());
			}
		}
		if (rangeAxisState != null) {
			drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
			drawZeroRangeBaseline(g2, dataArea);
		}

		Graphics2D savedG2 = g2;
		BufferedImage dataImage = null;
		boolean suppressShadow = Boolean.TRUE.equals(g2.getRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION));
		if (this.getShadowGenerator() != null && !suppressShadow) {
			dataImage = new BufferedImage((int) dataArea.getWidth(), (int) dataArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2 = dataImage.createGraphics();
			g2.translate(-dataArea.getX(), -dataArea.getY());
			g2.setRenderingHints(savedG2.getRenderingHints());
		}

		// draw the markers...
		for (int i = 0; i < this.getRendererCount(); i++) {
			drawDomainMarkers(g2, dataArea, i, Layer.BACKGROUND);
		}
		for (int i = 0; i < this.getRendererCount(); i++) {
			drawRangeMarkers(g2, dataArea, i, Layer.BACKGROUND);
		}

		// now render data items...
		boolean foundData = false;

		// set up the alpha-transparency...
		Composite originalComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getForegroundAlpha()));

		DatasetRenderingOrder order = getDatasetRenderingOrder();
		if (order == DatasetRenderingOrder.FORWARD) {
			for (int i = 0; i < this.getDatasetCount(); i++) {
				foundData = render(g2, dataArea, i, state, crosshairState) || foundData;
			}
		} else { // DatasetRenderingOrder.REVERSE
			for (int i = this.getDatasetCount() - 1; i >= 0; i--) {
				foundData = render(g2, dataArea, i, state, crosshairState) || foundData;
			}
		}
		// draw the foreground markers...
		for (int i = 0; i < this.getRendererCount(); i++) {
			drawDomainMarkers(g2, dataArea, i, Layer.FOREGROUND);
		}
		for (int i = 0; i < this.getRendererCount(); i++) {
			drawRangeMarkers(g2, dataArea, i, Layer.FOREGROUND);
		}

		// draw the annotations (if any)...
		drawAnnotations(g2, dataArea);

		if (this.getShadowGenerator() != null && !suppressShadow) {
			BufferedImage shadowImage = this.getShadowGenerator().createDropShadow(dataImage);
			g2 = savedG2;
			g2.drawImage(shadowImage, (int) dataArea.getX() + this.getShadowGenerator().calculateOffsetX(), (int) dataArea.getY()
					+ this.getShadowGenerator().calculateOffsetY(), null);
			g2.drawImage(dataImage, (int) dataArea.getX(), (int) dataArea.getY(), null);
		}
		g2.setClip(savedClip);
		g2.setComposite(originalComposite);

		if (!foundData) {
			drawNoDataMessage(g2, dataArea);
		}

		int datasetIndex = crosshairState.getDatasetIndex();
		setCrosshairDatasetIndex(datasetIndex, false);

		// draw domain crosshair if required...
		Comparable rowKey = crosshairState.getRowKey();
		Comparable columnKey = crosshairState.getColumnKey();
		setDomainCrosshairRowKey(rowKey, false);
		setDomainCrosshairColumnKey(columnKey, false);
		if (isDomainCrosshairVisible() && columnKey != null) {
			Paint paint = getDomainCrosshairPaint();
			Stroke stroke = getDomainCrosshairStroke();
			drawDomainCrosshair(g2, dataArea, this.getOrientation(), datasetIndex, rowKey, columnKey, stroke, paint);
		}

		// draw range crosshair if required...
		ValueAxis yAxis = getRangeAxisForDataset(datasetIndex);
		RectangleEdge yAxisEdge = getRangeAxisEdge();
		if (!this.isRangeCrosshairLockedOnData() && anchor != null) {
			double yy;
			if (getOrientation() == PlotOrientation.VERTICAL) {
				yy = yAxis.java2DToValue(anchor.getY(), dataArea, yAxisEdge);
			} else {
				yy = yAxis.java2DToValue(anchor.getX(), dataArea, yAxisEdge);
			}
			crosshairState.setCrosshairY(yy);
		}
		setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
		if (isRangeCrosshairVisible()) {
			double y = getRangeCrosshairValue();
			Paint paint = getRangeCrosshairPaint();
			Stroke stroke = getRangeCrosshairStroke();
			drawRangeCrosshair(g2, dataArea, getOrientation(), y, yAxis, stroke, paint);
		}

		// draw an outline around the plot area...
		if (isOutlineVisible()) {
			if (getRenderer() != null) {
				getRenderer().drawOutline(g2, this, dataArea);
			} else {
				drawOutline(g2, dataArea);
			}
		}

	}

	/**
	 * Trims a rectangle to integer coordinates.
	 * 
	 * @param rect
	 *            the incoming rectangle.
	 * 
	 * @return A rectangle with integer coordinates.
	 */
	private Rectangle integerise(Rectangle2D rect) {
		int x0 = (int) Math.ceil(rect.getMinX());
		int y0 = (int) Math.ceil(rect.getMinY());
		int x1 = (int) Math.floor(rect.getMaxX());
		int y1 = (int) Math.floor(rect.getMaxY());
		return new Rectangle(x0, y0, (x1 - x0), (y1 - y0));
	}

}
