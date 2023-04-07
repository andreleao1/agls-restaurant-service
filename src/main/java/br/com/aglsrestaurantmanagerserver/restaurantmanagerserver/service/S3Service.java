package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class S3Service {

    @Value("${cloud.aws.s3.foodbucketname}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile multipartFile) {
        File file = convertToFile(multipartFile);
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        try{
            log.debug("Uploading file to bucket");
            s3Client.putObject(new PutObjectRequest(bucketName, fileName,file));
        }catch (AmazonS3Exception e) {
            log.error("Error to upload file");
            throw new AmazonS3Exception(e.getMessage());
        } finally {
            file.delete();
        }
        return fileName;
    }

    public void deleteFile(String fileName) {
        try {
            log.debug(String.format("Deleting %s file", fileName));
            s3Client.deleteObject(bucketName, fileName);
        } catch (AmazonS3Exception e) {
            log.error("Error to delete file");
            throw new AmazonS3Exception(e.getMessage());
        }
    }

    public byte[] getObject(String fileName) {
        log.info("Starting to get bucket object");
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            log.debug("Extracting object content");
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            log.error("Error to extract content file");
        }
        return null;
    }

    private File convertToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartfile to file", e);
        }
        return convertedFile;
    }
}
