package com.clothes.service.upload;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UploadServiceImpl implements UploadService{

    static  final Path storageFolder = Paths.get("src/main/resources/static/images");

    //controller
    public UploadServiceImpl(){
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    public boolean checkIsImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }


    @Override
    public String save(MultipartFile uploadFile) {
        try {
            if (!uploadFile.isEmpty()) {
                System.out.println("Upload");
                if (uploadFile.isEmpty()) {
                    throw new RuntimeException("Failed to store empty file.");
                }
                //check is file?
                if (!checkIsImage(uploadFile)) {
                    throw new RuntimeException("You can only upload image file.");
                }
                // file must <= 5mb
                float fileSizeMegabytes = uploadFile.getSize() / 1_000_000f;
                if (fileSizeMegabytes > 5.0f) {
                    throw new RuntimeException("File must be <= 5Mb");
                }

                //rename file
                String fileExtension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
                String generatedFileName = UUID.randomUUID().toString().replace("-", "");
                generatedFileName = generatedFileName + "." + fileExtension;

                Path destinationFilePath = storageFolder.resolve(
                                Paths.get(generatedFileName))
                        .normalize().toAbsolutePath();

                if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                    throw new RuntimeException("Cannot store files outside the current directory.");
                }
                try (InputStream inputStream = uploadFile.getInputStream()) {
                    Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
                return generatedFileName;
            }else {
                return  "Loi trong file";
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file.", e);
        }

    }

    @Override
    public List<String> saveList(List<MultipartFile> uploadFiles) {
        List<String> list = new ArrayList<>();

            for (MultipartFile files : uploadFiles) {
                try {
                    System.out.println("Upload");
                    if (files.isEmpty()) {
                        throw new RuntimeException("Failed to store empty file.");
                    }
                    //check is file?
                    if (!checkIsImage(files)) {
                        throw new RuntimeException("You can only upload image file.");
                    }
                    // file must <= 5mb
                    float fileSizeMegabytes = files.getSize() / 1_000_000f;
                    if (fileSizeMegabytes > 5.0f) {
                        throw new RuntimeException("File must be <= 5Mb");
                    }

                    //rename file
                    String fileExtension = FilenameUtils.getExtension(files.getOriginalFilename());
                    String generatedFileName = UUID.randomUUID().toString().replace("-", "");
                    generatedFileName = generatedFileName +"."+ fileExtension;

                    Path destinationFilePath  = storageFolder.resolve(
                                    Paths.get(generatedFileName))
                            .normalize().toAbsolutePath();

                    if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                        throw new RuntimeException("Cannot store files outside the current directory.");
                    }
                    try (InputStream inputStream = files.getInputStream()){
                        Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    list.add(generatedFileName);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to store file list.", e);
                }
            }
            return list;

    }



    @Override
    public void delete(String filename) {
        new File(String.valueOf(storageFolder),filename).delete();
    }
    public void deleteList(List<String> filenames) {
        System.out.println("Thưc hien test case");
        for (String filename : filenames) {
            new File(String.valueOf(storageFolder),filename).delete();
            System.out.println("test case voi --- "+ filename);
        }
        System.out.println("ko the xoa test case");
    }

    @Override
    public File getFile(String virtualPath) {
        return null;
    }
}
