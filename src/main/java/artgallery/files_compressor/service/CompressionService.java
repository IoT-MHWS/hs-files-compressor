package artgallery.files_compressor.service;

import artgallery.files_compressor.model.CompressionParams;

import java.io.IOException;

public interface CompressionService<T extends CompressionParams> {
  byte[] compress(byte[] image, T params) throws IOException;
}
