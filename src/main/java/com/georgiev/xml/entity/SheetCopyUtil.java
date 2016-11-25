/**
 * ***********************************************************************
 * Sheet copy Utility operations.
 * 
 * @author volker/sascha
 * 
 */
package com.georgiev.xml.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class SheetCopyUtil {

	/**
	 * Copy newSheet to sheet overwriting the contents of sheet.
	 * 
	 * @param newSheet
	 * @param sheet
	 */
	public static void copySheets(Sheet newSheet, Sheet sheet) {
		copySheets(newSheet, sheet, true);
	}

	public static void copySheets(Sheet newSheet, Sheet sheet, boolean copyStyle) {
		int maxColumnNum = 0;
		Map<Short, CellStyle> styleMap = (copyStyle) ? new HashMap<Short, CellStyle>() : null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row srcRow = sheet.getRow(i);
			Row destRow = newSheet.createRow(i);
			if (srcRow != null) {
				SheetCopyUtil.copyRow(sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++) {
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	public static void copyRow(Sheet srcSheet, Sheet destSheet, Row srcRow, Row destRow,
			Map<Short, CellStyle> styleMap) {
		final Set<InternalCellRangeAddress> mergedRegions = new HashSet<InternalCellRangeAddress>();
		destRow.setHeight(srcRow.getHeight());
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
			if (j >= 0) {
				Cell oldCell = srcRow.getCell(j);
				Cell newCell = destRow.getCell(j);
				if (oldCell != null) {
					if (newCell == null) {
						newCell = destRow.createCell(j);
					}
					copyCell(oldCell, newCell, styleMap);
					CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(),
							(short) oldCell.getColumnIndex());
					if (mergedRegion != null) {
						CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(),
								mergedRegion.getLastRow(), mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
						if (isNewMergedRegion(newMergedRegion, mergedRegions)) {
							mergedRegions.add(new InternalCellRangeAddress(newMergedRegion));
							destSheet.addMergedRegion(newMergedRegion);
						}
					}
				}
			}
		}
	}

	public static void copyCell(Cell oldCell, Cell newCell, Map<Short, CellStyle> styleMap) {
		if (styleMap != null) {
			if (oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()) {
				newCell.setCellStyle(oldCell.getCellStyle());
			} else {
				CellStyle newCellStyle = styleMap.get(oldCell.getCellStyle().getIndex());
				if (newCellStyle == null) {
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
					CellStyle cellStyle = oldCell.getCellStyle();
					//newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					
					XSSFColor bgColor = (XSSFColor) oldCell.getCellStyle().getFillBackgroundColorColor();
					//XSSFColor bgColor2 = (XSSFColor) oldCell.getCellStyle().getFillForegroundColorColor();
					//short t = oldCell.getCellStyle().getFillBackgroundColor();
					//short t1 = oldCell.getCellStyle().getFillForegroundColor();
					
				
					
					//System.out.println(t + " " +t1);
					//System.out.println(bgColor + " " +bgColor2);
					//((XSSFCellStyle) newCellStyle).setFillBackgroundColor(bgColor);
					newCellStyle=cellStyle;
					styleMap.put(oldCell.getCellStyle().getIndex(), newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		switch (oldCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getStringCellValue());
			System.out.println(oldCell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			newCell.setCellType(Cell.CELL_TYPE_BLANK);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}

	}

	public static CellRangeAddress getMergedRegion(Sheet sheet, int rowNum, short cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	private static boolean isNewMergedRegion(CellRangeAddress newMergedRegion,
			Collection<InternalCellRangeAddress> mergedRegions) {
		return !mergedRegions.contains(newMergedRegion);
	}

	/**
	 * ***********************************************************************
	 * InternalCellRangeAddress collections compatible wrapper for a POI
	 * CellRangeAddress object.
	 * 
	 * @author 4FB
	 * 
	 */
	private static class InternalCellRangeAddress {
		private final int firstRow;

		private final int firstCol;

		private final int lastRow;

		private final int lastCol;

		public InternalCellRangeAddress(CellRangeAddress cra) {
			this.firstRow = cra.getFirstRow();
			this.firstCol = cra.getFirstColumn();
			this.lastRow = cra.getLastRow();
			this.lastCol = cra.getLastColumn();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + firstCol;
			result = prime * result + firstRow;
			result = prime * result + lastCol;
			result = prime * result + lastRow;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InternalCellRangeAddress other = (InternalCellRangeAddress) obj;
			if (firstCol != other.firstCol)
				return false;
			if (firstRow != other.firstRow)
				return false;
			if (lastCol != other.lastCol)
				return false;
			if (lastRow != other.lastRow)
				return false;
			return true;
		}
	}

}
