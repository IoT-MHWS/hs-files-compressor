package artgallery.files_compressor.repository;

import artgallery.files_compressor.util.FsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
@RequiredArgsConstructor
public class FsPaintingRepository {

  private final FsPaintingConfiguration configuration;

  public byte[] getPainting(Path path) throws IOException {
    return FsUtil.get(this.getPaintingPath(path));
  }

  public void putPainting(Path path, byte[] paintingBytes) throws IOException {
    FsUtil.put(this.getPaintingPath(path), paintingBytes);
  }

  public Path getPaintingPath(Path path) {
    return Paths.get(configuration.getPaintingsMount(), path.toString());
  }
}
