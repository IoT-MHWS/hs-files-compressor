package artgallery.files_compressor.repository.fs;

import artgallery.files_compressor.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
@RequiredArgsConstructor
public class FsPaintingRepository implements PaintingRepository {

  private final FsPaintingConfiguration configuration;

  public byte[] getPainting(String path) throws IOException {
    return FsUtil.get(this.getPaintingPath(path));
  }

  public void putPainting(String path, byte[] paintingBytes) throws IOException {
    FsUtil.put(this.getPaintingPath(path), paintingBytes);
  }

  public Path getPaintingPath(String name) {
    return Paths.get(configuration.getPaintingsMount(), name);
  }

}
