package com.file.main;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.file.cloud.StorageSample;
import com.file.dateSheet.DataSheet;
import com.file.encryption.AESEncryptor;
import com.file.encryption.CryptoException;
import com.file.encryption.CryptoUtils;
import com.file.zip.ZipFile;

import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Osama on 3/9/2016.
 * 
 * mvn exec:java -Dexec.mainClass="com.file.main.Main"
 */
public class Main {
	  
	  public static void AesEncrypt(File file,int enyc,DataSheet dataSheet){
	        System.out.println("\n------------Start AES " + enyc + " Encrypt File------------");
	        try {
	            Long startEncTime = System.currentTimeMillis();
	            encryptedFile = AESEncryptor.encrypt(file,destination,enyc);
	            Long endEncTime = System.currentTimeMillis();
	            System.out.println("----END AES Encrypt : " + (endEncTime - startEncTime) + " milliSecond----");
	            
	            dataSheet.setEncrypionType("AES: "+enyc);
	            dataSheet.setEncryptionTime(""+(endEncTime - startEncTime));
	            dataSheet.setEncryptionFileSize(""+encryptedFile.length());
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

    public static void DESEnycFile(File file,DataSheet dataSheet){
        System.out.println("------------Start DES Encrypt File----------------");
        try {
            Long startEncTime = System.currentTimeMillis();
            encryptedFile = CryptoUtils.encrypt(key,file,new File(destination + fileName + ".enc"));
            Long endEncTime = System.currentTimeMillis();
            System.out.println("----END DES ENCRYPT FILE with Time : " + (endEncTime - startEncTime) + " milliSecond ----");
            
            dataSheet.setEncrypionType("DES");
            dataSheet.setEncryptionTime(""+(endEncTime - startEncTime));
            dataSheet.setEncryptionFileSize(""+encryptedFile.length());
            
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }
    public static void compressZIPFile(File file,DataSheet dataSheet){
        System.out.println("\n------------Start Compress File------------");
        
        Long startCompTime = System.currentTimeMillis();
        compressedFile = new ZipFile().compress(file,destination);
        Long endCompTime = System.currentTimeMillis();
        
        dataSheet.setCompressionFileSize(""+compressedFile.length());        
        dataSheet.setCompressionTime(""+(endCompTime-startCompTime));
        dataSheet.setCompressionType("ZIP");       
        
        System.out.println(" END Compress FILE with Time : " + (endCompTime - startCompTime) + " milliSecond ----");

    }


    public static void compressEnycreptFile(File file,DataSheet dataSheet) throws Exception{
        System.out.println("\n------------Start Compress File------------");
        
        compressedFile = new ZipFile().fileCompressEnycrept(file, destination, dataSheet);

        dataSheet.setCompressionType("ZIP-BYTE");       
        
    

    }
    


    static String bucketName = "1217_osama_cloud";
            static String sourceFile = "D:/google/";
            static String destination = "D:/google/results/";
            static String fileName = "Fault-Tolerant Encryption for.pdf";
            static File encryptedFile = null;
            static File compressedFile=null;
            static String key = "MaryHASS";
            private static final String EXCEL_FILE_NAME = "D:\\masterProject\\MyFirstExcel.xlsx";

    public static void main(String[] args) throws Exception {
    	
    	File folder = new File("D:/google/");
		File[] listOfFiles = folder.listFiles();
		
		List<DataSheet> datasheet = new ArrayList<DataSheet>();
		
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  DataSheet dataSheet=new DataSheet();
		    	  String fileName=listOfFiles[i].getName();
		    	  
		    	  dataSheet.setFileName(fileName);
		    	  dataSheet.setFileSize(""+listOfFiles[i].length());
		    	  dataSheet.setFileType( fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()));
		    	
		    	  
		    	/*  compressZIPFile(listOfFiles[i],dataSheet);
		    	  AesEncrypt(compressedFile,128,dataSheet); */
		    	  
		    	  
		   	  compressEnycreptFile(listOfFiles[i],dataSheet);
		    	
		   
		    	  
		    	  
		    	  datasheet.add(dataSheet);
		    	  
		  
		    	  
		       
		      } 
		    }

		Xcelite xcelite = new Xcelite(new File(EXCEL_FILE_NAME));    
		XceliteSheet sheet = xcelite.createSheet(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
		
		SheetWriter<DataSheet> writer = sheet.getBeanWriter(DataSheet.class);
		writer.write(datasheet); 
	
		xcelite.write();
		
     //  testDESEnycCompressUpload();
      // testCompressUploadFile();
      // testDESEnycUpload();
  //      testUploadFile();

     // testAesEnycCompressUpload(128);
        //  testAesEnycUpload(128);
    }

}
