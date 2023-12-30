package artgallery.files_compressor.service;

import artgallery.files_compressor.model.CompressionParams;
import artgallery.files_compressor.model.ImageCompressionDTO;
import artgallery.files_compressor.repository.fs.FsPaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaintingCompressionService<T extends CompressionParams> {

  private final CompressionService<T> compressionService;
  private final FsPaintingRepository paintingRepository;

  ImageCompressionDTO<T> compressImage(ImageCompressionDTO<T> compressionDTO) throws IOException {
    byte[] painting = paintingRepository.getPainting(compressionDTO.getSource());
    byte[] result = compressionService.compress(painting, compressionDTO.getParams());
    paintingRepository.putPainting(compressionDTO.getDestination(), result);
    return compressionDTO;
  }
}
