package com.xxbs.v1.util.chart;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelRecord;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlotState;
import org.jfree.data.general.PieDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;

import com.xxbs.v1.util.Utils;
import com.xxbs.v1.util.collection.CollectionUtil;

/**
 * �ܺ��Բ���key��PiePlot
 * 
 * @author tang
 * 
 */
@SuppressWarnings("rawtypes")
public class IgnoreKeyPiePlot extends PiePlot {

	private static final long serialVersionUID = 1L;

	protected Comparable[] ignoreKeys;

	public IgnoreKeyPiePlot(Comparable[] ignoreKeys) {
		this.ignoreKeys = ignoreKeys;
	}

	public IgnoreKeyPiePlot() {
	}

	public IgnoreKeyPiePlot(PieDataset dataset) {
		super(dataset);
	}

	public IgnoreKeyPiePlot(PieDataset dataset, Comparable[] ignoreKeys) {
		super(dataset);
		this.ignoreKeys = ignoreKeys;
	}

	@Override
	protected void drawSimpleLabels(Graphics2D g2, List keys, double totalValue, Rectangle2D plotArea, Rectangle2D pieArea, PiePlotState state) {

		Composite originalComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		Rectangle2D labelsArea = this.getSimpleLabelOffset().createInsetRectangle(pieArea);
		double runningTotal = 0.0;
		Iterator iterator = keys.iterator();
		while (iterator.hasNext()) {
			Comparable key = (Comparable) iterator.next();
			if (CollectionUtil.isExist(ignoreKeys, key)) {
				continue;
			}
			boolean include;
			double v = 0.0;
			Number n = getDataset().getValue(key);
			if (n == null) {
				include = !getIgnoreNullValues();
			} else {
				v = n.doubleValue();
				include = getIgnoreZeroValues() ? v > 0.0 : v >= 0.0;
			}

			if (include) {
				runningTotal = runningTotal + v;
				// work out the mid angle (0 - 90 and 270 - 360) = right,
				// otherwise left
				double mid = getStartAngle() + (getDirection().getFactor() * ((runningTotal - v / 2.0) * 360) / totalValue);

				Arc2D arc = new Arc2D.Double(labelsArea, getStartAngle(), mid - getStartAngle(), Arc2D.OPEN);
				int x = (int) arc.getEndPoint().getX();
				int y = (int) arc.getEndPoint().getY();

				PieSectionLabelGenerator myLabelGenerator = getLabelGenerator();
				if (myLabelGenerator == null) {
					continue;
				}
				String label = myLabelGenerator.generateSectionLabel(this.getDataset(), key);
				if (label == null) {
					continue;
				}
				g2.setFont(this.getLabelFont());
				FontMetrics fm = g2.getFontMetrics();
				Rectangle2D bounds = TextUtilities.getTextBounds(label, g2, fm);
				Rectangle2D out = this.getLabelPadding().createOutsetRectangle(bounds);
				Shape bg = ShapeUtilities.createTranslatedShape(out, x - bounds.getCenterX(), y - bounds.getCenterY());
				if (this.getLabelShadowPaint() != null && this.getShadowGenerator() == null) {
					Shape shadow = ShapeUtilities.createTranslatedShape(bg, this.getShadowXOffset(), this.getShadowYOffset());
					g2.setPaint(this.getLabelShadowPaint());
					g2.fill(shadow);
				}
				if (this.getLabelBackgroundPaint() != null) {
					g2.setPaint(this.getLabelBackgroundPaint());
					g2.fill(bg);
				}
				if (this.getLabelOutlinePaint() != null && this.getLabelOutlineStroke() != null) {
					g2.setPaint(this.getLabelOutlinePaint());
					g2.setStroke(this.getLabelOutlineStroke());
					g2.draw(bg);
				}

				g2.setPaint(this.getLabelPaint());
				g2.setFont(this.getLabelFont());
				TextUtilities.drawAlignedString(label, g2, x, y, TextAnchor.CENTER);

			}
		}

		g2.setComposite(originalComposite);

	}

	@Override
	protected void drawLeftLabel(Graphics2D arg0, PiePlotState arg1, PieLabelRecord arg2) {
		if (!CollectionUtil.isExist(ignoreKeys, arg2.getKey())) {
			super.drawLeftLabel(arg0, arg1, arg2);
		}
	}

	@Override
	protected void drawRightLabel(Graphics2D arg0, PiePlotState arg1, PieLabelRecord arg2) {
		if (!CollectionUtil.isExist(ignoreKeys, arg2.getKey())) {
			super.drawRightLabel(arg0, arg1, arg2);
		}
	}

	public Comparable[] getIgnoreKeys() {
		return ignoreKeys;
	}

	public void setIgnoreKeys(Comparable[] ignoreKeys) {
		this.ignoreKeys = ignoreKeys;
	}
}
