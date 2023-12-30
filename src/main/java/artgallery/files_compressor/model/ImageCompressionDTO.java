package artgallery.files_compressor.model;

import lombok.Data;

@Data
public abstract class ImageCompressionDTO<T extends CompressionParams> {
  private String source;
  private String destination;
  private String mimeType;
  private T params;
}
