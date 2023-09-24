package ca.sheridancollege.kimdohee.controllers;

import java.awt.PageAttributes.MediaType;
import java.net.http.HttpHeaders;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ca.sheridancollege.kimdohee.beans.AESKeyGenerator;
import ca.sheridancollege.kimdohee.beans.EncryptionService;

@Controller
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("encryptedfile", "");
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
        	try {

                SecretKey aesKey = AESKeyGenerator.generateKey();

                byte[] encryptedData = encryptionService.encryptFile(file, aesKey);

                String encryptedContent = Base64.getEncoder().encodeToString(encryptedData);
                model.addAttribute("encryptedContent", encryptedContent);

                return "upload";
                
            } catch (Exception e) {
                model.addAttribute("error", "An error occurred while processing the file.");
            }

        } else {
            model.addAttribute("error", "No file was uploaded.");
        }
        return "upload";
    }
    

}

