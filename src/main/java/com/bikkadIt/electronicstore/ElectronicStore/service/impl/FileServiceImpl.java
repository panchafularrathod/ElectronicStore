package com.bikkadIt.electronicstore.ElectronicStore.service.impl;

import com.bikkadIt.electronicstore.ElectronicStore.exception.BadApiRequeast;
import com.bikkadIt.electronicstore.ElectronicStore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename : {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtention=filename+extention;
        String fullPathWithFileName=path+ File.separator+fileNameWithExtention;
         if (extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg")
                 || extention.equalsIgnoreCase(".jpeg")){
             //file save
             File folder=new File(path);

             if (!folder.exists()) {

                 //create the folder
                 folder.mkdirs();
             }
             //upload
             Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
             return fileNameWithExtention;
         }else {
             throw new BadApiRequeast("File with this"+extention+ "not allowed !!");
         }

    }
        @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

            String fullpath = path + File.separator + name;
            InputStream inputStream=new FileInputStream(fullpath);

            return inputStream;
    }
}
