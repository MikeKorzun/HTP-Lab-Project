package gw.identification.fs.impl;

import gw.identification.fs.FSException;
import gw.identification.fs.ImageSave;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ImageSaveImpl implements ImageSave {
    private static Logger logger = LoggerFactory.getLogger(ImageSaveImpl.class.getName());
    
    @Override
    public void execute(InputStream inputStream, Path path) throws FSException {
        try {
            Files.copy(inputStream, path);
        } catch (IOException e) {
            logger.debug("Could not save file on disk", e.getMessage());
            throw new FSException(e.getMessage());
        }
    }
}