package com.file.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Osama on 3/14/2016.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.file.dateSheet.DataSheet;
import com.file.encryption.AESEncryptor;

public class ZipFile {

	public ZipFile() {

	}

	public File compress(File f, String destination) {
		String fileName = f.getName();
		System.out.println("File " + fileName + " with Size " + f.length() + " byte before Compress");

		byte[] buffer = new byte[4096];
		File zippedFile = new File(destination + fileName + ".zip");
		try {
			FileOutputStream fos = new FileOutputStream(zippedFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry(fileName);
			zos.putNextEntry(ze);

			/*
			 * BufferedInputStream in =new BufferedInputStream( new FileInputStream(f)); int
			 * len; while ((len = in.read(buffer)) > 0) {
			 * 
			 * zos.write(buffer, 0, len); } in.close();
			 */

			byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
			zos.write(bytes, 0, bytes.length);

			zos.closeEntry();
			zos.close();
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return zippedFile;
	}

	public File fileCompressEnycrept(File f, String destination, DataSheet dataSheet) throws Exception {

		Long startCompTime = System.currentTimeMillis();

		String fileName = f.getName();
		File enycreptedFile = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ZipOutputStream zos = new ZipOutputStream(baos)) {
			
			ZipEntry ze = new ZipEntry(fileName);
			zos.putNextEntry(ze);

			byte[] bytes = Files.readAllBytes(Paths.get(f.getAbsolutePath()));
			zos.write(bytes, 0, bytes.length);

			Long endCompTime = System.currentTimeMillis();
			Long startEncryptTime = System.currentTimeMillis();
			
			enycreptedFile = AESEncryptor.encryptByteArray(baos.toByteArray(), fileName, destination, 128);
			
			Long endEncryptTime = System.currentTimeMillis();
			dataSheet.setCompressionFileSize("" + baos.size());
			dataSheet.setCompressionTime("" + (endCompTime - startCompTime));
			dataSheet.setEncryptionFileSize("" + enycreptedFile.length());
			dataSheet.setEncryptionTime("" + (endEncryptTime - startEncryptTime));

			baos.close();
			zos.closeEntry();
			zos.close();

		} 

		return enycreptedFile;
	}

	public static byte[] zipBytes(String filename, byte[] input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry entry = new ZipEntry(filename);
		entry.setSize(input.length);
		zos.putNextEntry(entry);
		zos.write(input);
		zos.closeEntry();
		zos.close();
		return baos.toByteArray();
	}

}
