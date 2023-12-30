package artgallery.files_compressor.service;

import artgallery.files_compressor.model.PngCompressionParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PngCompressionService implements CompressionService<PngCompressionParams> {

  private final GenericCompressionService genericCompressionService;

  @Override
  public byte[] compress(byte[] imageBytes, PngCompressionParams params) throws IOException {
    var input = ImageIO.read(new ByteArrayInputStream(imageBytes));

    var compressed = genericCompressionService.compress(input,
      new GenericCompressionService.GenericCompressionParams(params.getWidth(), params.getHeight(), params.isKeepAspectRatio()
    ));

    var baos = new ByteArrayOutputStream();
    ImageIO.write(compressed, "png", baos);
    return baos.toByteArray();
  }
}
