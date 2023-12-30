package artgallery.files_compressor.model;

import lombok.Data;

@Data
public abstract class CompressionParams {
  private int width;
  private int height;
  private boolean keepAspectRatio;
}
