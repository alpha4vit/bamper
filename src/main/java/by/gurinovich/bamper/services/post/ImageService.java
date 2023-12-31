package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ImageUploadException;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.props.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public String upload(final Image image) {
        try{
            createBucket();
        }
        catch (Exception ex){
            throw new ImageUploadException("Error bucket creating: "+ex.getMessage());
        }
        MultipartFile file = image.getFile();
        if (file.getOriginalFilename() == null || file.isEmpty() )
            throw new ImageUploadException("Image must not be null!!!");
        String filename = generateFileName(file);
        InputStream inputStream;
        try{
            inputStream = file.getInputStream();
            saveImage(inputStream, filename);
        }
        catch (Exception ex) {
            throw new ImageUploadException("Image upload failed: " + ex.getMessage());
        }
        return filename;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(minioProperties.getBucket())
                .build());
        if (!found){
            minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile multipartFile){
        String extension = getExtension(multipartFile);
        return UUID.randomUUID()+"."+extension;
    }

    private String getExtension(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                .build());
    }

    @SneakyThrows
    public void removeImage(String fileName){
        minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                .build());
    }
}
