package artgallery.files_compressor.service;

import artgallery.files_compressor.model.ImageCompressionRequest;
import artgallery.files_compressor.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PaintingCompressionService {

  private final CompressionService compressionService;
  private final PaintingRepository paintingRepository;

  public ImageCompressionRequest compressImage(ImageCompressionRequest compressionDTO) throws IOException {
    byte[] painting = paintingRepository.getPainting(compressionDTO.getSource());
    var input = ImageIO.read(new ByteArrayInputStream(painting));

    BufferedImage result = compressionService.compress(input, compressionDTO.getParams());

    var baos = new ByteArrayOutputStream();
    var iaos = ImageIO.createImageOutputStream(baos);
    var imageWriter = ImageIO.getImageWritersByMIMEType(compressionDTO.getMimeType()).next();
    imageWriter.setOutput(iaos);
    imageWriter.write(result);

    paintingRepository.putPainting(compressionDTO.getDestination(), baos.toByteArray());

    return compressionDTO;
  }

}
