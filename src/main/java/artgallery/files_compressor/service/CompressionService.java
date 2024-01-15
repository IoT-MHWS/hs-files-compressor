package artgallery.files_compressor.service;

import artgallery.files_compressor.model.CompressionParams;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface CompressionService {
  BufferedImage compress(BufferedImage input, CompressionParams params) throws IOException;
}
