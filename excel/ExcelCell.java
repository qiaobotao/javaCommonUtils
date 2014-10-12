/**
 * 
 */
package com.shinedu.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * @author zhoumingming
 *
 * Apr 7, 2010
 */
public class ExcelCell {

	private Integer colIndex;
	private Object content;
	private HSSFCellStyle cellStyle;
	private ExcelPicture picture;
	private boolean override;
	public ExcelCell(){
	}
	
	public ExcelCell(Integer colIndex, Object content, HSSFCellStyle cellStyle){
		this.colIndex = colIndex;
		this.content = content;
		this.cellStyle = cellStyle;
		this.override = false;
	}
	
	public ExcelCell(Integer colIndex, Object content, HSSFCellStyle cellStyle, boolean override){
		this.colIndex = colIndex;
		this.content = content;
		this.cellStyle = cellStyle;
		this.override = override;
	}
	
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}
	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public ExcelPicture getPicture() {
		return picture;
	}

	public void setPicture(ExcelPicture picture) {
		this.picture = picture;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
}
