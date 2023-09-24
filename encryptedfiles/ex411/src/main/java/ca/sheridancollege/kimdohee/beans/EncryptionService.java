package ca.sheridancollege.kimdohee.beans;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class EncryptionService {

    public byte[] encryptFile(MultipartFile file, SecretKey key) throws Exception {

        byte[] fileData = file.getBytes();
        byte[] encryptedData = encryptData(fileData, key);

        return encryptedData;
    }

    private byte[] encryptData(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}
