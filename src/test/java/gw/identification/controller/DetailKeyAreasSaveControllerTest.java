package gw.identification.controller;

import gw.identification.controller.response.ImageSaveResponse;
import gw.identification.model.Detail;
import gw.identification.model.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetailKeyAreasSaveControllerTest {
    @Value("${location}")
    private String location;
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() throws IOException {
        Path path = Paths.get(location);
        if (Files.exists(path)) {
            Files.list(path).map(Path::toFile).forEach(File::delete);
        }
    }

    @Test
    public void execute() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource("src/test/resources/Test image.png"));
        map.add("description", new String("Test image description"));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, header);

        ResponseEntity<ImageSaveResponse> imageSaveResponse = restTemplate.postForEntity("/register/images", request, ImageSaveResponse.class);
        assertThat(imageSaveResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(imageSaveResponse.getBody().getMessage(), equalTo("success"));
    }
}

