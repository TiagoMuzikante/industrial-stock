package space.industock.industrial_stock.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

  private final MinioClient minioClient;

  @Value("${minio.bucket}")
  private String bucket;

  public String upload(
      InputStream stream,
      long size,
      String contentType
  ) throws Exception {

    String objectName = UUID.randomUUID().toString();

    minioClient.putObject(
        PutObjectArgs.builder()
            .bucket(bucket)
            .object(objectName)
            .stream(stream, size, -1)
            .contentType(contentType)
            .build()
    );

    return objectName;
  }

  public InputStream download(String objectName) throws Exception {
    return minioClient.getObject(
        GetObjectArgs.builder()
            .bucket(bucket)
            .object(objectName)
            .build()
    );
  }

  public void delete(String objectName) throws Exception {
    minioClient.removeObject(
        RemoveObjectArgs.builder()
            .bucket(bucket)
            .object(objectName)
            .build()
    );
  }
}
