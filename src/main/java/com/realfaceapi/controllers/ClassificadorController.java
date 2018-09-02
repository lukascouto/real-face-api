package com.realfaceapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/classificador")
public class ClassificadorController {
	

    private static String UPLOADED_FOLDER = "src/main/resources/classificadores/";

    @PostMapping("/upload")
    @ResponseBody
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {

        try {   	
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Classificador enviado com sucesso: " + file.getOriginalFilename() + ".";
    }
    
	/*
	@PostMapping
	public ResponseEntity<Object> salvarClassificador(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("src/main/resources/classificadores/"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("Arquivo enviado com sucesso.", HttpStatus.OK);
	}
	
	
	@PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {
		
		File convertFile = new File("src/main/resources/classificadores/"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new String("Arquivo enviado com sucesso.");
    }*/

}