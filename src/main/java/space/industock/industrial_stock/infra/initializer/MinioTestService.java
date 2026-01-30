package space.industock.industrial_stock.infra.initializer;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

//@Service
@RequiredArgsConstructor
public class MinioTestService {

  private final MinioClient minioClient;

  @Value("${minio.bucket}")
  private String bucket;

  @PostConstruct
  public void testConnection() throws Exception {
    boolean exists = minioClient.bucketExists(
        BucketExistsArgs.builder().bucket(bucket).build()
    );

    if (!exists) {
      throw new IllegalStateException("Bucket não encontrado: " + bucket);
    }
    //uploadTest();

    System.out.println("✅ Conectado ao MinIO com sucesso");
  }

  public void uploadTest() throws Exception {
    byte[] data = "teste-minio".getBytes();

    minioClient.putObject(
        PutObjectArgs.builder()
            .bucket(bucket)
            .object("teste.txt")
            .stream(new ByteArrayInputStream(data), data.length, -1)
            .contentType("text/plain")
            .build()
    );
  }
}
