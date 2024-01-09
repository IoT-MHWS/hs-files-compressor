package artgallery.files_compressor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageCompressionResponse {
  private String source;
  private String destination;
  private String mimeType;
  private boolean result;
}
