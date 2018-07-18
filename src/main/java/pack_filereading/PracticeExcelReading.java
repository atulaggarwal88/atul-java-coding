package pack_filereading;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.util.*;

public class PracticeExcelReading {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\test file.xlsx";
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		try(XSSFWorkbook wkbk = new XSSFWorkbook(fis)){
			XSSFSheet wksht = wkbk.getSheetAt(0);
			Iterator<Row> rwIter = wksht.rowIterator();
			while(rwIter.hasNext()){
				Row rw = rwIter.next();
				Iterator<Cell> cellIter	= rw.cellIterator();
				while(cellIter.hasNext()){
					Cell cell = cellIter.next();
					switch(cell.getCellTypeEnum()){
					case STRING:
						System.out.println(cell.getStringCellValue());
						break;
					case NUMERIC:
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

}
