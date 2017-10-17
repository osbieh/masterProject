package com.file.main;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.file.dateSheet.DataSheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WriteToExcel {
	
	 private static final String FILE_NAME = "D:\\masterProject\\MyFirstExcel.xlsx";
	 
	 
	 void addRow(){
		 
		 
	 }

	 public static void main(String[] args) {
		 
		 
	
		
		File folder = new File("D:\\masterProject");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File: " + listOfFiles[i].getName());
		      } 
		    }

		
		Xcelite xcelite = new Xcelite(new File(FILE_NAME));    
		XceliteSheet sheet = xcelite.createSheet(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
		
		
		SheetWriter<DataSheet> writer = sheet.getBeanWriter(DataSheet.class);
		List<DataSheet> datasheet = new ArrayList<DataSheet>();
	
		//datasheet.add(new DataSheet("cc","250067","ZIP","20000","786","AES","23", "78945",""));
	
		writer.write(datasheet); 
	
		xcelite.write(new File(FILE_NAME));
		
		
	 
	 }
}
