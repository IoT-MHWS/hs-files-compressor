package artgallery.files_compressor.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FsPaintingConfiguration {

  @Value("${app.paintings.repository.fs.mount}")
  private String paintingsMount;

}
