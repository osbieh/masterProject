package com.file.dateSheet;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = { "fileName", "fileSize", "fileType", "compressionType", "compressionSize", "compressionTime",
		"encrypionType", "encryptionFileSize", "encryptionTime" })
public class DataSheet {
	/*
	 * public DataSheet(String fileName, String fileSize, String encrypionType,
	 * String encryptionTime, String encryptionFileSize, String compressionType,
	 * String compressionTime, String compressionFileSize,String
	 * compressionBytesSize,String fileType) {
	 * 
	 * this.fileName = fileName; this.fileSize = fileSize; this.encrypionType =
	 * encrypionType; this.encryptionTime = encryptionTime; this.encryptionFileSize
	 * = encryptionFileSize; this.compressionType = compressionType;
	 * this.compressionTime = compressionTime; this.compressionFileSize =
	 * compressionFileSize; this.compressionBytesSize=compressionBytesSize;
	 * this.fileType=fileType; }
	 */

	public DataSheet() {
		super();
	}
	
	@Column (ignoreType=true,name="fileName")
	String fileName;
	 
	 @Column (ignoreType=true,name="fileSize")
	String fileSize;
	 
	 @Column (ignoreType=true,name="encrypionType")	 
	String encrypionType;
	 
	 @Column (ignoreType=true,name="encryptionTime")
	String encryptionTime;
	 
	 @Column (ignoreType=true,name="encryptionFileSize")
	String encryptionFileSize;
	 
	 @Column (ignoreType=true,name="compressionType")
	String compressionType;
	 
	 @Column (ignoreType=true,name="compressionTime")
	String compressionTime;
	 
	 @Column (ignoreType=true,name="compressionSize")
	 String compressionFileSize;
	 
	 @Column (ignoreType=true,name="fileType")
	 String fileType;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getEncrypionType() {
		return encrypionType;
	}

	public void setEncrypionType(String encrypionType) {
		this.encrypionType = encrypionType;
	}

	public String getEncryptionTime() {
		return encryptionTime;
	}

	public void setEncryptionTime(String encryptionTime) {
		this.encryptionTime = encryptionTime;
	}

	public String getEncryptionFileSize() {
		return encryptionFileSize;
	}

	public void setEncryptionFileSize(String encryptionFileSize) {
		this.encryptionFileSize = encryptionFileSize;
	}

	public String getCompressionType() {
		return compressionType;
	}

	public void setCompressionType(String compressionType) {
		this.compressionType = compressionType;
	}

	public String getCompressionTime() {
		return compressionTime;
	}

	public void setCompressionTime(String compressionTime) {
		this.compressionTime = compressionTime;
	}

	public String getCompressionFileSize() {
		return compressionFileSize;
	}

	public void setCompressionFileSize(String compressionFileSize) {
		this.compressionFileSize = compressionFileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
