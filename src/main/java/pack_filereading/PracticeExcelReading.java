package pack_filereading;
import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.*;

public class PracticeExcelReading {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\test file.xlsx";
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wkbk = new XSSFWorkbook(fis);
		XSSFSheet wksht = wkbk.getSheetAt(0);
		Iterator<Row> rwIter = wksht.rowIterator();
		while(rwIter.hasNext()){
			Row rw = rwIter.next();
			Iterator<Cell> cellIter	= rw.cellIterator();
			while(cellIter.hasNext()){
				Cell cell = cellIter.next();
				switch(cell.getCellType()){
					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println(cell.getNumericCellValue());
						break;
					default:
						System.out.println(cell.toString());
						break;
				}
			}
		}
	}

}
