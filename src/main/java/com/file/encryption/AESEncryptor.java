package com.file.encryption;

/**
 * Created by Osama Sbieh on 3/15/2016.
 */
import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESEncryptor {
    public static File encrypt(File source, String destination, int enyc) throws Exception{


        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(enyc);  //using AES-256 //
        SecretKey key = keyGen.generateKey();  //generating key
        Cipher aesCipher = Cipher.getInstance("AES");  //getting cipher for AES
        aesCipher.init(Cipher.ENCRYPT_MODE, key);  //initializing cipher for encryption with key

        File enycFile=new File(destination+source.getName()+".aes");
        //creating file output stream to write to file
        try(FileOutputStream fos = new FileOutputStream(enycFile)){
            //creating object output stream to write objects to file
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);  //saving key to file for use during decryption

            //creating file input stream to read contents for encryption
            try(FileInputStream fis = new FileInputStream(source)){
                //creating cipher output stream to write encrypted contents
                try(CipherOutputStream cos = new CipherOutputStream(fos, aesCipher)){
                    int read;
                    byte buf[] = new byte[4096];
                    while((read = fis.read(buf)) != -1)  //reading from file
                        cos.write(buf, 0, read);  //encrypting and writing to file
                }
            }
        }
        return enycFile;
    }
    
    public static File encryptByteArray(byte[] baos,String fileName ,String destination, int enyc) throws Exception{


        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(enyc);  //using AES-256 //
        SecretKey key = keyGen.generateKey();  //generating key
        Cipher aesCipher = Cipher.getInstance("AES");  //getting cipher for AES
        aesCipher.init(Cipher.ENCRYPT_MODE, key);  //initializing cipher for encryption with key

        File enycFile=new File(destination+fileName+".aes");
        //creating file output stream to write to file
        try(FileOutputStream fos = new FileOutputStream(enycFile)){
            //creating object output stream to write objects to file
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);  //saving key to file for use during decryption            
                try(CipherOutputStream cos = new CipherOutputStream(fos, aesCipher)){
                
                        cos.write(baos, 0, baos.length);  //encrypting and writing to file
            
            }
              
        }
     
        return enycFile;
    }
    
    
    public static File encryptByteArray2(byte[] baos,String fileName ,String destination, int enyc) throws Exception{


        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(enyc);  //using AES-256 //
        SecretKey key = keyGen.generateKey();  //generating key
        Cipher aesCipher = Cipher.getInstance("AES");  //getting cipher for AES
        aesCipher.init(Cipher.ENCRYPT_MODE, key);  //initializing cipher for encryption with key

        File enycFile=new File(destination+fileName+".aes");
        //creating file output stream to write to file
        try(FileOutputStream fos = new FileOutputStream(enycFile)){
            //creating object output stream to write objects to file
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);  //saving key to file for use during decryption

            //creating file input stream to read contents for encryption
            try(ByteArrayInputStream fis = new ByteArrayInputStream(baos)){
                //creating cipher output stream to write encrypted contents
                try(CipherOutputStream cos = new CipherOutputStream(fos, aesCipher)){
                    int read;
                    byte buf[] = new byte[4096];
                    while((read = fis.read(buf)) != -1)  //reading from file
                        cos.write(buf, 0, read);  //encrypting and writing to file
                }
            }
        }
        return enycFile;
    }
    
    

    public void decrypt(String fName)throws Exception{
        SecretKey key =null;

        //creating file input stream to read from file
        try(FileInputStream fis = new FileInputStream(fName)){
            //creating object input stream to read objects from file
            ObjectInputStream ois = new ObjectInputStream(fis);
            key = (SecretKey)ois.readObject();  //reading key used for encryption

            Cipher aesCipher = Cipher.getInstance("AES");  //getting cipher for AES
            aesCipher.init(Cipher.DECRYPT_MODE, key);  //initializing cipher for decryption with key
            //creating file output stream to write back original contents
            try(FileOutputStream fos = new FileOutputStream(fName.split(".aes")[0])){
                //creating cipher input stream to read encrypted contents
                try(CipherInputStream cis = new CipherInputStream(fis, aesCipher)){
                    int read;
                    byte buf[] = new byte[4096];
                    while((read = cis.read(buf)) != -1)  //reading from file
                        fos.write(buf, 0, read);  //decrypting and writing to file
                }
            }
        }

    }

}
