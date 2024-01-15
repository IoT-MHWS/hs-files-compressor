package artgallery.files_compressor.repository;

import artgallery.files_compressor.model.ImageLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;

@Repository
@RequiredArgsConstructor
public class DelegatedPaintingRepository implements PaintingRepository {
  private final FsPaintingRepository fsPaintingRepository;

  public byte[] getPainting(ImageLocation location) throws IOException {
    if (location.getLocationType() == ImageLocation.LocationType.FILESYSTEM) {
      return fsPaintingRepository.getPainting(Path.of(location.getLocation()));
    }
    throw new RuntimeException("unknown location type");
  }

  public void putPainting(ImageLocation location, byte[] imageModel) throws IOException {
    if (location.getLocationType() == ImageLocation.LocationType.FILESYSTEM) {
      fsPaintingRepository.putPainting(Path.of(location.getLocation()), imageModel);
    } else {
      throw new RuntimeException("unknown location type");
    }
  }
}
