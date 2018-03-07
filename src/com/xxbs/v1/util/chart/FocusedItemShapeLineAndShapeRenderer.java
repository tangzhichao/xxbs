package com.xxbs.v1.util.chart;

import java.awt.Shape;

import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.util.ShapeList;

/**
 * �����ʱ,��������һ�������ʱ��Item����״
 * 
 * @author tang
 * 
 */
public class FocusedItemShapeLineAndShapeRenderer extends LineAndShapeRenderer {

	private static final long serialVersionUID = -2039193427859580518L;

	protected ShapeList focusedSeriesShapeList = new ShapeList();

	protected boolean useFocusedSeriesShape=false;//�Ƿ�ʹ�������ͼ��
	protected int focusedRow = -1;
	protected int focusedColumn = -1;

	public FocusedItemShapeLineAndShapeRenderer() {
		super();
	}

	public FocusedItemShapeLineAndShapeRenderer(boolean lines, boolean shapes) {
		super(lines, shapes);
	}

	@Override
	public Shape getItemShape(int row, int column) {
		if (isUseFocusedSeriesShape()&&(row == focusedRow && column == focusedColumn)) {
			return getFocusedItemShape(row, column);
		} else {
			return super.getItemShape(row, column);
		}
	}

	public Shape getFocusedItemShape(int row, int column) {
		return lookupFocusedSeriesShape(row);
	}

	public Shape lookupFocusedSeriesShape(int series) {
		Shape result = getFocusedSeriesShape(series);
		
		if (result == null) {
			result = super.getSeriesShape(series);
			if (result==null&&this.getAutoPopulateSeriesShape()) {
				DrawingSupplier supplier = getDrawingSupplier();
				if (supplier != null) {
					result = supplier.getNextShape();
					setFocusedSeriesShape(series, result, false);
				}
			}
		}
		if (result == null) {
			result = this.getBaseShape();
		}
		return result;

	}

	public Shape getFocusedSeriesShape(int series) {
		return this.focusedSeriesShapeList.getShape(series);
	}

	public void setFocusedSeriesShape(int series, Shape shape) {
		setFocusedSeriesShape(series, shape, true);
	}

	public void setFocusedSeriesShape(int series, Shape shape, boolean notify) {
		this.focusedSeriesShapeList.setShape(series, shape);
		if (notify) {
			fireChangeEvent();
		}
	}

	public void setFocusedIndex(int focusedRow, int focusedColumn) {
		if (isUseFocusedSeriesShape()) {
			if (this.focusedRow!=focusedRow||this.focusedColumn!=focusedColumn) {
				this.focusedRow = focusedRow;
				this.focusedColumn = focusedColumn;
				fireChangeEvent();
			}
		}
	}

	public int getFocusedRow() {
		return focusedRow;
	}

	public void setFocusedRow(int focusedRow) {
		if (isUseFocusedSeriesShape()) {
			if (this.focusedRow!=focusedRow) {
				this.focusedRow = focusedRow;
				fireChangeEvent();
			}
		}
	}

	public int getFocusedColumn() {
		return focusedColumn;
	}

	public void setFocusedColumn(int focusedColumn) {
		if (isUseFocusedSeriesShape()) {
			if (this.focusedColumn!=focusedColumn) {
				this.focusedColumn = focusedColumn;
				fireChangeEvent();
			}
		}
	}

	public boolean isUseFocusedSeriesShape() {
		return useFocusedSeriesShape;
	}

	public void setUseFocusedSeriesShape(boolean useFocusedSeriesShape) {
		this.useFocusedSeriesShape = useFocusedSeriesShape;
	}
}
