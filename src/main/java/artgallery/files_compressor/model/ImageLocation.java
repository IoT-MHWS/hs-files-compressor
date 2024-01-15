package artgallery.files_compressor.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageLocation {
  private LocationType locationType;
  private String location;

  public enum LocationType {
    FILESYSTEM,
  }
}
