package com.xxbs.v1.util.export;

public class ExcelGroupCell implements GroupCellInterface {
	private int startRow;
	private int endRow;
	private int startCol;
	private int endCol;
	private String value;

	public ExcelGroupCell(int startRow, int endRow, int startCol, int endCol, String text) {
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
		this.value = text;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	@Override
	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	@Override
	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	@Override
	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	@Override
	public String getValue() {
		return value.trim();
	}

	public void setValue(String text) {
		this.value = text.trim();
	}

	@Override
	public int compareTo(GroupCellInterface o) {
		if (this.getStartRow() != o.getStartRow()) {
			return this.getStartRow() - o.getStartRow();
		}
		return this.getStartCol() - o.getStartCol();
	}

	@Override
	public String toString() {
		return "Excel [startRow=" + startRow + ", endRow=" + endRow + ", startCol=" + startCol + ", endCol=" + endCol + ", value=" + value + "]";
	}
}
