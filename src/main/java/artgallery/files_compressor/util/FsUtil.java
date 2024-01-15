package artgallery.files_compressor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FsUtil {

  public static byte[] get(Path path) throws IOException {
    return Files.readAllBytes(path);
  }

  public static void put(Path path, byte[] bytes) throws IOException {
    try (var outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
      outputStream.write(bytes);
    }
  }

}
