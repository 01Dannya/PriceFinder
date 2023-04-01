package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	private static Workbook book;
	private static Sheet sheet;
	
	// load excel connection and open workbook
	
	private static void openExcel(String filePath) {
		
		try {
			FileInputStream fileIs = new FileInputStream(filePath);
			book = new XSSFWorkbook(fileIs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//loads sheet passed by caller
	private static void loadSheet(String sheetName) {
		sheet = book.getSheet(sheetName);
	}
	
	//get row count
	private static int rowCount() {
		
		return sheet.getPhysicalNumberOfRows();
	}
	
	//returns cell amnt
	private static int colsCount(int rowIndex) {
		return sheet.getRow(rowIndex).getLastCellNum();
	}
	
	private static String cellData(int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex).toString();
	}
	
	public static Object[][] excelIntoArray(String filePath, String sheetName) {
		
		openExcel(filePath);
		loadSheet(sheetName);
		
		int row = rowCount();
		int cols = colsCount(0);
		
		Object[][] data = new Object[row-1][cols];
		
		for (int i = 1; i< row; i++) {
			
			for (int j = 0; j <cols; j++) {
				data[i-1][j] = cellData(i, j);
				System.out.println(cellData(i,j));
			}
		}
		
		return data;
	}

}
