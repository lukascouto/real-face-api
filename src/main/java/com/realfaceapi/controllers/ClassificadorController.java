package com.realfaceapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/classificador")
public class ClassificadorController {
	
    private static String UPLOADED_FOLDER = "src/main/resources/classificadores/";

    @PostMapping("/salvar")
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
}