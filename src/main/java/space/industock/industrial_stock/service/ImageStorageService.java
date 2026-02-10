package space.industock.industrial_stock.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

  public void writeChunk(UUID sessionId, UUID fileId, int index, InputStream in, long size, String contentType) {
    try {
      String chunkObject =
          "tmp/" + sessionId + "/" + fileId + "/" + index;

      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(bucket)
              .object(chunkObject)
              .stream(in, size, -1)
              .contentType(contentType)
              .build()
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public String compose(UUID sessionId, UUID fileId, int totalChunks, String contentType) {
    try {
      // nome final gerado igual ao upload()
      String objectName = UUID.randomUUID().toString();

      // objeto temporário composto
      String composedObject = "final/" + sessionId + "/" + fileId + "/" + objectName;

      List<ComposeSource> sources = new ArrayList<>();

      for (int i = 0; i < totalChunks; i++) {
        sources.add(
            ComposeSource.builder()
                .bucket(bucket)
                .object("tmp/" + sessionId + "/" + fileId + "/" + i)
                .build()
        );
      }

      // 1️⃣ compõe os chunks
      minioClient.composeObject(
          ComposeObjectArgs.builder()
              .bucket(bucket)
              .object(composedObject)
              .sources(sources)
              .build()
      );

      // 2️⃣ copia para a raiz do bucket (mesmo padrão do upload)
      minioClient.copyObject(
          CopyObjectArgs.builder()
              .bucket(bucket)
              .object(objectName)
              .source(
                  CopySource.builder()
                      .bucket(bucket)
                      .object(composedObject)
                      .build()
              )
              .build()
      );

      // 3️⃣ remove o objeto temporário composto
      minioClient.removeObject(
          RemoveObjectArgs.builder()
              .bucket(bucket)
              .object(composedObject)
              .build()
      );

      // 4️⃣ remove os chunks temporários
      for (int i = 0; i < totalChunks; i++) {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucket)
                .object("tmp/" + sessionId + "/" + fileId + "/" + i)
                .build()
        );
      }

      return objectName;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
