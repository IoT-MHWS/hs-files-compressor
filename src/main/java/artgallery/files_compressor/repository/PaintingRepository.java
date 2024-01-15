package artgallery.files_compressor.repository;

import artgallery.files_compressor.model.ImageLocation;

import java.io.IOException;

public interface PaintingRepository {

  byte[] getPainting(ImageLocation path) throws IOException;

  void putPainting(ImageLocation path, byte[] imageModel) throws IOException;

}
