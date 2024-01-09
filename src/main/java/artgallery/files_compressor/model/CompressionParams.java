package artgallery.files_compressor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompressionParams {
  private int width;
  private int height;
  private boolean keepAspectRatio;
}
