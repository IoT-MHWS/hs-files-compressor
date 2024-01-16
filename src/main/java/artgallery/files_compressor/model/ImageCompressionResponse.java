package artgallery.files_compressor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageCompressionResponse {
  private long id;
  private ImageLocation source;
  private ImageLocation destination;
  private String mimeType;
  private boolean result;
  private String msg;
}
