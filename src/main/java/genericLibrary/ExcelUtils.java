package genericLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
/**
 * This utility is used to perform excel operations according to user requirements
 * @author praveen.k
 * @version 1.0
 */
public class ExcelUtils {
 
	private Logger log = Logger.getLogger(ExcelUtils.class.getName());
	
	Workbook workbook;
	Sheet sheet;
	Row row;
	Cell cell;
	FileOutputStream fileoutput;
	String f="";
	private static String XLpath = System.getProperty("user.dir")+"//Test_Data.xls";
	/**
	 * This utility is used to get data from excel sheet based on given parameters
	 * @param SheetName
	 * @param rowNumber
	 * @param columnNumber
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @return String
	 */
	public String getExcelData(String SheetName,int rowNumber,int columnNumber) throws InvalidFormatException, IOException{
		String returnData=null;
		File file = new File(XLpath);
		if(file.exists()){
			log.info("Test Document Excel Exist :"+XLpath);
		}else{
			log.error("Test Document does not Exist,Operation may Fail ");
		}
		log.info("------------- Test Document Excel Info --------------");
		log.info("GetExcelData :{Sheet name :{"+SheetName+"}, Row Number :{"+rowNumber+"}, Column Number :{"+columnNumber+"}}");
		log.info("------------------------------------------------------");
		try{
		workbook = WorkbookFactory.create(new File(XLpath));
		log.info("Workbook Created Successfully");
			try{
			sheet = workbook.getSheet(SheetName);
			row = sheet.getRow(rowNumber);
			
			}catch(Exception e){
				log.error("Unable to Read Data from Sheet :"+SheetName, e);
			}
		
		returnData = row.getCell(columnNumber).getStringCellValue();
		log.info("Returned Value from getExcelData Method is  :"+returnData);
		}catch(Exception e){
			log.error("Unable to Get Data From Test Document ", e);
		}
		return returnData;
	}
	
	
	@SuppressWarnings("static-access")
	/**
	 * This utility is used to set data into particular cell in excel sheet
	 * @param XLpath
	 * @param SheetName
	 * @param rowNumber
	 * @param columnNumber
	 * @param setData
	 * @return boolean
	 */
	public boolean setExcelData(String SheetName,int rowNumber,int columnNumber,String setData) throws IOException{
		File file = new File(XLpath);
		boolean flag=false;
		if(file.exists()){
			log.info("Test Document Excel Exist :"+XLpath);
		}else{
			log.error("Test Document doesn't Exist,Operation will Fail ");
		}
		
		log.info("------------- Test Document Excel Info --------------");
		log.info("SetExcelData :  {Sheet name :{"+SheetName+"}, Row Number :{"+rowNumber+"}, Column Number :{"+columnNumber+"}}");
		log.info("------------------------------------------------------");
		
		try{
			workbook = WorkbookFactory.create(file);
			sheet = workbook.getSheet(SheetName);
			row = sheet.getRow(rowNumber);
			cell = row.createCell(columnNumber);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(setData);
			
			fileoutput = new FileOutputStream(file);
			workbook.write(fileoutput);
			log.info("Data successfully set into Test Document ");
			flag = true;
		}catch(Exception e){
			log.error("Unable to Set Data into Test Document ",e);
		}finally{
			fileoutput.close();
		}
		
		return flag;
		
	}
	
	/**
	 * This utility is used to get row count(last row number)
	 * @param SheetName
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @return int
	 */
	public int getRowCount(String SheetName) throws InvalidFormatException, IOException{
		File file = new File(XLpath);
		int rowCount=0;
		if(file.exists()){
			log.info("Test Document Excel Exist :"+XLpath);
		}else{
			log.error("Test Document doesn't Exist,Operation will Fail ");
		}
		try{
		workbook = WorkbookFactory.create(file);
		sheet  = workbook.getSheet(SheetName);
		rowCount = sheet.getLastRowNum();
		log.info("Last Row Number is  :"+rowCount);
		}catch(Exception e){
			log.error("Unable to Get Row Count ", e);
		}
		
		return rowCount;
	}
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
		ExcelUtils excelUtils = new ExcelUtils();
		System.out.println(excelUtils.getExcelData("testdata", 0, 1));
	}

}
