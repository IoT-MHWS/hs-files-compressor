package artgallery.files_compressor.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class GenericCompressionService {
  public BufferedImage compress(BufferedImage input, GenericCompressionParams params) throws IOException {
    return Thumbnails.of(input)
      .size(params.width, params.height)
      .keepAspectRatio(params.keepRatio)
      .asBufferedImage();
  }

  public record GenericCompressionParams(int width, int height, boolean keepRatio) {};
}
