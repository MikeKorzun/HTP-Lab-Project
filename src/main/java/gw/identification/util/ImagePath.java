package gw.identification.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@PropertySource(value = "classpath:fs.properties")
public class ImagePath {
    @Value("${location}")
    private String location;

    public Path determine(String originalFilename) {
        String fileName = fileName();
        String extension = extension(originalFilename);
        return path(fileName, extension);
    }

    private String fileName() {
        return UUID.randomUUID().toString();
    }

    private String extension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    private Path path(String fileName, String extension) {
        String pathName = String.join("", location, "/", fileName, ".", extension);
        return Paths.get(pathName);
    }
}
