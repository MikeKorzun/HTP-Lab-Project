package gw.identification.fs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageSaveTest {
    @Autowired
    private ImageSave imageSave;

    @Before
    public void setup() {
        File destination = new File("src/test/resources/New Test image.png");
        if (destination.exists()) destination.delete();
    }

    @Test
    public void execute() throws IOException, FSException {
        File original = new File("src/test/resources/Test image.png");
        File copy = new File("src/test/resources/New Test image.png");
        Path path = copy.toPath();
        try (InputStream inputStream = new FileInputStream(original)) {
            imageSave.execute(inputStream, path);
        }
        
        assertThat(copy).exists();
        assertArrayEquals(Files.readAllBytes(original.toPath()), Files.readAllBytes(copy.toPath()));
        assertEquals(original.length(), copy.length());
    }
}