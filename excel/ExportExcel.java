/**
 * 
 */
package com.shinedu.utils.excel;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
/**
 * @author zhoumingming
 *
 * Mar 31, 2010
 */
public class ExportExcel {

	private HSSFWorkbook workbook;
	private HSSFCellStyle defaultHeaderCellStyle;
	private HSSFCellStyle defaultListCellStyle;
	
	public ExportExcel(){
		workbook = new HSSFWorkbook();
		generateDefaultCellStyle();
	}
	
	public ExportExcel(HSSFWorkbook workbook){
		if(workbook != null){
			this.workbook = workbook;
		}else{
			this.workbook = new HSSFWorkbook();
		}
		generateDefaultCellStyle();
	}
	
	private void generateDefaultCellStyle(){

	    // 设置默认标题样式
		HSSFCellStyle style = createCellStyle();
	    style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    HSSFFont font = workbook.createFont();
	    font.setColor(HSSFColor.VIOLET.index);
	    font.setFontHeightInPoints((short) 12);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font);
	    defaultHeaderCellStyle = style;

	    // 设置列表样式
		HSSFCellStyle style1 = createCellStyle();
		style1.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    HSSFFont font1 = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    style1.setFont(font1);
	    defaultListCellStyle = style1;
	}
	
	public HSSFWorkbook getWorkbook(){
		return workbook;
	}
	
	public HSSFCellStyle getDefaultHeaderCellStyle(){
		return defaultHeaderCellStyle;
	}
	
	public HSSFCellStyle getDefaultListCellStyle(){
		return defaultListCellStyle;
	}
	
	private HSSFSheet getSheet(ExcelSheet excelSheet) throws Exception{
		HSSFSheet sheet = null;
		String sheetName = excelSheet.getSheetName();
		if(excelSheet.isOverride()){
			sheet = workbook.getSheet(sheetName);
		}
		if(sheet == null){
			if(sheetName == null || "".equals(sheetName)){
				sheet = workbook.createSheet();
			}else{
				removeSheet(sheetName);
			    sheet = workbook.createSheet(sheetName);
			}
		}
		return sheet;
	}
	private HSSFRow getRow(HSSFSheet sheet, int rowIndex, boolean override) throws Exception{
		HSSFRow row = null;
		if(override){
			return sheet.getRow(rowIndex);
		}
		if(row == null){
			row = sheet.createRow(rowIndex);
		}
		return row;
	}
	private HSSFCell getCell(HSSFRow row, int colIndex, boolean override) throws Exception{
		HSSFCell cell = null;
		if(override){
			cell = row.getCell(colIndex);
		}
		if(cell == null){
			cell = row.createCell(colIndex);
		}
		return cell;
	}
	public HSSFSheet writeSheet(ExcelSheet excelSheet) throws Exception{
		HSSFSheet sheet = getSheet(excelSheet);
		if(excelSheet.getRowList() == null || excelSheet.getRowList().size() == 0 || sheet == null){
			return sheet;
		}
		//设置默认宽高
		if(excelSheet.getDefaultRowHeightInPoints() != null){
			sheet.setDefaultRowHeightInPoints(excelSheet.getDefaultRowHeightInPoints());
		}
		if(excelSheet.getDefaultColumnWidthInChars() != null){
			sheet.setDefaultColumnWidth(excelSheet.getDefaultColumnWidthInChars());
		}
		//设置列宽
		if(excelSheet.getColumnWidthInCharsMap() != null){
			Iterator<Short> colNumIt = excelSheet.getColumnWidthInCharsMap().keySet().iterator();
			Short colNumTemp;
			while(colNumIt.hasNext()){
				colNumTemp = colNumIt.next();
				sheet.setColumnWidth(colNumTemp, (excelSheet.getColumnWidthInCharsMap().get(colNumTemp) * 256));
			}
		}
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		ExcelRow excelRow;
		ExcelCell excelCell;
		HSSFRow row;
		HSSFCell cell;
		
		for(int i = 0; i < excelSheet.getRowList().size(); i++){
			excelRow = excelSheet.getRowList().get(i);
			row = getRow(sheet, excelRow.getRowIndex(), excelRow.isOverride());
			if(excelRow.getHeightInPoints() != null){
				row.setHeightInPoints(excelRow.getHeightInPoints());
			}
			for(short k = 0; k < excelRow.getCellList().size(); k++){
				excelCell = excelRow.getCellList().get(k);
				cell = getCell(row, excelCell.getColIndex(), excelCell.isOverride());
				if(excelCell.getCellStyle() != null){
					cell.setCellStyle(excelCell.getCellStyle());
				}
				//向单元格写值
				Object content = excelCell.getContent();
				if(content != null){
					//判断值的类型后进行强制类型转换
					Double valueDouble = null;
					if (content instanceof Integer) {
						valueDouble = ((Integer) content).doubleValue();
					} else if (content instanceof Float) {
						valueDouble = ((Float) content).doubleValue();
					} else if (content instanceof Double) {
						valueDouble = (Double) content;
					} else if (content instanceof Long) {
						valueDouble = ((Long) content).doubleValue();
					}
					if (valueDouble != null) {
						cell.setCellValue(valueDouble);
					} else {
						// 其它数据类型都当作字符串简单处理
						HSSFRichTextString richString = new HSSFRichTextString(content.toString());
						cell.setCellValue(richString);
					}
				}
				
				//向单元格写入图片
				if(excelCell.getPicture() != null){
					ExcelPicture ep = (ExcelPicture) excelCell.getContent();
					HSSFClientAnchor anchor = ep.getAnchor();
					anchor.setCol1(excelCell.getColIndex());
					anchor.setRow1(excelRow.getRowIndex());
					anchor.setCol2(excelCell.getColIndex());
					anchor.setRow2(excelRow.getRowIndex());
					anchor.setAnchorType(2);
					row.setHeightInPoints(ep.getRowHeightPx());
					sheet.setColumnWidth(excelCell.getColIndex(), (short) (37.5*ep.getColWidthPx()));
					patriarch.createPicture(anchor, workbook.addPicture(ep.getImgValue(), ep.getPictureType()));
				}
			}
		}
		return sheet;
	}
	
	public HSSFSheet createSheet4SurveyAnalysis(ExcelSheet excelSheet){
		HSSFSheet sheet;
		String sheetName = excelSheet.getSheetName();
		if(sheetName == null || "".equals(sheetName)){
			sheet = workbook.createSheet();
		}else{
			sheet = workbook.getSheet(sheetName);
			if(sheet == null){
			    sheet = workbook.createSheet(sheetName);
			}else{
				sheet = workbook.createSheet();
			}
		}
		//设置默认宽高
		if(excelSheet.getDefaultRowHeightInPoints() != null){
			sheet.setDefaultRowHeightInPoints(excelSheet.getDefaultRowHeightInPoints());
		}
		if(excelSheet.getDefaultColumnWidthInChars() != null){
			sheet.setDefaultColumnWidth(excelSheet.getDefaultColumnWidthInChars());
		}
		//设置列宽
		if(excelSheet.getColumnWidthInCharsMap() != null){
			Iterator<Short> colNumIt = excelSheet.getColumnWidthInCharsMap().keySet().iterator();
			Short colNumTemp;
			while(colNumIt.hasNext()){
				colNumTemp = colNumIt.next();
				sheet.setColumnWidth(colNumTemp, (excelSheet.getColumnWidthInCharsMap().get(colNumTemp) * 256));
			}
		}
		if(excelSheet.getRowList() == null || excelSheet.getRowList().size() == 0){
			return sheet;
		}
		ExcelRow excelRow;
		ExcelCell excelCell;
		HSSFRow row;
		sheet.addMergedRegion(new Region(0, (short) (0), 0,(short)(1)));
		for(int i = 0; i < excelSheet.getRowList().size(); i++){
			excelRow = excelSheet.getRowList().get(i);
			if(excelRow.getCellList().size()==1){
				sheet.addMergedRegion(new Region(i, (short) (0), i,(short)(1)));
			}
			row = sheet.createRow(excelRow.getRowIndex());
			if(excelRow.getHeightInPoints() != null){
				row.setHeightInPoints(excelRow.getHeightInPoints());
			}
			for(short k = 0; k < excelRow.getCellList().size(); k++){
				excelCell = excelRow.getCellList().get(k);
				HSSFCell cell = row.createCell(excelCell.getColIndex());
				if(excelCell.getCellStyle() != null){
					cell.setCellStyle(excelCell.getCellStyle());
				}
				//向单元格写值
				Object content = excelCell.getContent();
				if(content != null){
					//判断值的类型后进行强制类型转换
					Double valueDouble = null;
					if (content instanceof Integer) {
						valueDouble = ((Integer) content).doubleValue();
					} else if (content instanceof Float) {
						valueDouble = ((Float) content).doubleValue();
					} else if (content instanceof Double) {
						valueDouble = (Double) content;
					} else if (content instanceof Long) {
						valueDouble = ((Long) content).doubleValue();
					}
					if (valueDouble != null) {
						cell.setCellValue(valueDouble);
					} else {
						// 其它数据类型都当作字符串简单处理
						HSSFRichTextString richString = new HSSFRichTextString(content.toString());
						cell.setCellValue(richString);
					}
				}
				
			}
		}
		return sheet;
    }
	public void writeInto(OutputStream os) throws IOException{
		if(os != null){
			workbook.write(os);
		}
	}
	
	public HSSFCellStyle createCellStyle(){
		return workbook.createCellStyle();
	}
	
	public HSSFFont createFont(){
		return workbook.createFont();
	}
	
	public void removeSheet(String sheetName)throws Exception{
		if(sheetName == null || "".equals(sheetName)){
			return;
		}
		int index = workbook.getSheetIndex(sheetName);
		if(index >= 0){
			removeSheet(index);
		}
	}
	
	public void removeSheet(int sheetIndex)throws Exception{
	    workbook.removeSheetAt(sheetIndex);
	}
	
	public void clearSheets(){
		while(workbook.getSheetAt(0) != null){
			workbook.removeSheetAt(0);
		}
	}
}