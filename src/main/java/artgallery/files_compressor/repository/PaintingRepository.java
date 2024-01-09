package artgallery.files_compressor.repository;

import java.io.IOException;

public interface PaintingRepository {

  byte[] getPainting(String path) throws IOException;

  void putPainting(String path, byte[] imageModel) throws IOException;

}
