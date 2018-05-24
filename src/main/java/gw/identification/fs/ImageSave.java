package gw.identification.fs;

import java.io.InputStream;
import java.nio.file.Path;

public interface ImageSave {
    void execute(InputStream inputStream, Path path) throws FSException;
}