package artgallery.files_compressor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCompressionRequest {
  private ImageLocation source;
  private ImageLocation destination;
  private String mimeType;
  private CompressionParams params;
}
