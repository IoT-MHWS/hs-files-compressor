package artgallery.files_compressor.service;

import artgallery.files_compressor.model.CompressionParams;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class GenericCompressionService implements CompressionService {
  public BufferedImage compress(BufferedImage input, CompressionParams params) throws IOException {
    return Thumbnails.of(input)
      .size(params.getWidth(), params.getHeight())
      .keepAspectRatio(params.isKeepAspectRatio())
      .asBufferedImage();
  }
}
