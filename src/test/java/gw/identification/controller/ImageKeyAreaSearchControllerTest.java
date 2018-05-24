package gw.identification.controller;

import gw.identification.controller.response.ImageSearchResponse;
import gw.identification.dao.DetailSaveDao;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ImageKeyAreaSearchControllerTest {
    @Value("${location}")
    private String location;
    @Value("${contextPath}")
    private String contextPath;

    Detail detail1 = new Detail();
    Image image1 = new Image();

    Detail detail2 = new Detail();
    Image image2 = new Image();


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DetailSaveDao detailSave;

    @Before
    public void setup() throws IOException {
        Path path = Paths.get(location);
        if (Files.exists(path)) {
            Files.list(path).map(Path::toFile).forEach(File::delete);
        }
        else Files.createDirectory(path);

        detail1 = Detail.builder().description("Detail description test (image)").build();
        image1 = Image.builder().path(Paths.get("storage/38400000-8cf0-11bd-b23e-10b96e4ef00d.jpg")).matlabId(1L).build();
        detail1.addImage(image1);
        detailSave.execute(detail1);

        detail2 = Detail.builder().description("Detail description test (image key area)").build();
        image2 = Image.builder().path(Paths.get("storage/8eb54a93-9aff-43b9-9e8e-54e37b5b4e30.jpg")).matlabId(2L).build();
        detail2.addImage(image2);
        detailSave.execute(detail2);
    }

    @Test
    public void execute() {
        MultiValueMap<String, FileSystemResource> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource("src/test/resources/Test image.png"));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, FileSystemResource>> request = new HttpEntity<>(map, header);

        ResponseEntity<ImageSearchResponse> imageSearchResponse = restTemplate.postForEntity("/identify/images", request, ImageSearchResponse.class);
        assertThat(imageSearchResponse.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(imageSearchResponse.getBody().getListObjectPOJO().get(0).getDescription(), equalTo(detail1.getDescription()));
        assertThat(imageSearchResponse.getBody().getListObjectPOJO().get(0).getPath(), equalTo(contextPath + image1.getPath().replace('\\', '/')));

        assertThat(imageSearchResponse.getBody().getListObjectPOJO().get(1).getDescription(), equalTo(detail2.getDescription()));
        assertThat(imageSearchResponse.getBody().getListObjectPOJO().get(1).getPath(), equalTo(contextPath + image2.getPath().replace('\\', '/')));


    }
}

