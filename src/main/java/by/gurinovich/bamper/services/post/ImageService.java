package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ImageUploadException;
import by.gurinovich.bamper.models.postsEntities.Image;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
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
        MultipartFile file = image.getMultipartFile();
        if (file.isEmpty() || file.getOriginalFilename() == null)
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

    private void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(minioProperties.name())
                .build());
        if (!found){
            minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(minioProperties.name())
                    .build());
        }
    }

    private String generateFileName(MultipartFile multipartFile){
        String extension = getExtenstion(multipartFile);
        return UUID.randomUUID()+"."+extension;
    }

    private String getExtenstion(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
    }

    private void saveImage(InputStream inputStream, String filename) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(minioProperties.name())
                        .object(filename)
                .build());
    }
}
