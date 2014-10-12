/**
 * 
 */
package com.shinedu.utils.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoumingming
 *
 * Apr 7, 2010
 */
public class ExcelSheet {

	private String sheetName;
	private Integer defaultColumnWidthInChars;
	private Integer defaultRowHeightInPoints;
	private Map<Short, Integer> columnWidthInCharsMap;
	private List<ExcelRow> rowList;
	private boolean override;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getDefaultColumnWidthInChars() {
		return defaultColumnWidthInChars;
	}

	public void setDefaultColumnWidthInChars(Integer defaultColumnWidthInChars) {
		this.defaultColumnWidthInChars = defaultColumnWidthInChars;
	}

	public Integer getDefaultRowHeightInPoints() {
		return defaultRowHeightInPoints;
	}

	public void setDefaultRowHeightInPoints(Integer defaultRowHeightInPoints) {
		this.defaultRowHeightInPoints = defaultRowHeightInPoints;
	}

	public Map<Short, Integer> getColumnWidthInCharsMap() {
		return columnWidthInCharsMap;
	}

	public void setColumnWidthInChars(Short colIndex, int widthInChars) {
		if(columnWidthInCharsMap == null){
			columnWidthInCharsMap = new HashMap<Short, Integer>();
		}
		columnWidthInCharsMap.put(colIndex, widthInChars);
	}

	public List<ExcelRow> getRowList() {
		return rowList;
	}

	public void setRowList(List<ExcelRow> rowList) {
		this.rowList = rowList;
	}
	public void addRow(ExcelRow excelRow){
		if(rowList == null){
			rowList = new ArrayList<ExcelRow>();
		}
		rowList.add(excelRow);
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
}
