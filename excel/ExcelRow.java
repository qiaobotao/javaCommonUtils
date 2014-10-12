/**
 * 
 */
package com.shinedu.utils.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoumingming
 *
 * Apr 7, 2010
 */
public class ExcelRow {

	private Integer rowIndex;
	private List<ExcelCell> cellList;
	private Float heightInPoints;
	private boolean override;
	
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	public List<ExcelCell> getCellList() {
		return cellList;
	}
	public void setCellList(List<ExcelCell> cellList) {
		this.cellList = cellList;
	}
	public Float getHeightInPoints() {
		return heightInPoints;
	}
	public void setHeightInPoints(Float heightInPoints) {
		this.heightInPoints = heightInPoints;
	}
	public void addCell(ExcelCell excelCell){
		if(cellList == null){
			cellList = new ArrayList<ExcelCell>();
		}
		cellList.add(excelCell);
	}
	public boolean isOverride() {
		return override;
	}
	public void setOverride(boolean override) {
		this.override = override;
	}
}
