package artgallery.files_compressor.service;

import artgallery.files_compressor.model.CompressionParams;
import artgallery.files_compressor.model.ImageCompressionRequest;
import artgallery.files_compressor.repository.fs.FsPaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaintingCompressionService {

  private final GenericCompressionService genericCompressionService;
  private final FsPaintingRepository paintingRepository;

  public ImageCompressionRequest compressImage(ImageCompressionRequest compressionDTO) throws IOException {
    byte[] painting = paintingRepository.getPainting(compressionDTO.getSource());
    var input = ImageIO.read(new ByteArrayInputStream(painting));

    BufferedImage result = genericCompressionService.compress(input, compressionDTO.getParams());

    var baos = new ByteArrayOutputStream();
    ImageIO.write(result, getExtensionByStringHandling(compressionDTO.getDestination())
      .orElseThrow(() -> new IOException("can't get type from path")), baos);

    paintingRepository.putPainting(compressionDTO.getDestination(), baos.toByteArray());

    return compressionDTO;
  }

  public Optional<String> getExtensionByStringHandling(String filename) {
    return Optional.ofNullable(filename)
      .filter(f -> f.contains("."))
      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }
}
