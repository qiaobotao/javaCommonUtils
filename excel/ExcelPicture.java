/**
 * 
 */
package com.shinedu.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author zhoumingming
 *
 * Apr 1, 2010
 */
public class ExcelPicture {

	private byte[] imgValue;
	private HSSFClientAnchor anchor;

	private int pictureType;
	private int rowHeightPx;
	private int colWidthPx;
	{
		pictureType = HSSFWorkbook.PICTURE_TYPE_JPEG;
		rowHeightPx = 50;
		colWidthPx = 100;
	}
	public ExcelPicture(byte[] imgValue, int dx2, int dy2){
		this.imgValue = imgValue;
		anchor = new HSSFClientAnchor();
		anchor.setDx1(0);
		anchor.setDy1(0);
		anchor.setDx2(dx2);
		anchor.setDy2(dy2);
	}
	public ExcelPicture(byte[] imgValue, HSSFClientAnchor anchor){
		this.imgValue = imgValue;
		this.anchor = anchor;
	}
	public byte[] getImgValue() {
		return imgValue;
	}
	public void setImgValue(byte[] imgValue) {
		this.imgValue = imgValue;
	}
	public HSSFClientAnchor getAnchor() {
		return anchor;
	}
	public void setAnchor(HSSFClientAnchor anchor) {
		this.anchor = anchor;
	}
	public int getPictureType() {
		return pictureType;
	}
	public void setPictureType(int pictureType) {
		this.pictureType = pictureType;
	}
	public int getRowHeightPx() {
		return rowHeightPx;
	}
	public void setRowHeightPx(int rowHeightPx) {
		this.rowHeightPx = rowHeightPx;
	}
	public int getColWidthPx() {
		return colWidthPx;
	}
	public void setColWidthPx(int colWidthPx) {
		this.colWidthPx = colWidthPx;
	}
}
