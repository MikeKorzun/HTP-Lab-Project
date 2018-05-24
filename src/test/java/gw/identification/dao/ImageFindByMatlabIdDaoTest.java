package gw.identification.dao;

import gw.identification.model.Detail;
import gw.identification.model.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageFindByMatlabIdDaoTest {
    @Autowired
    private DetailSaveDao detailSave;

    @Autowired
    private ImageFindByMatlabIdDao imageFindByMatlabIdDao;

    @Test
    public void execute() {
        Detail originalDetail = Detail.builder().description("Test case").build();
        Image originalImage = Image.builder().path(Paths.get("location\\38400000-8cf0-11bd-b23e-10b96e4ef00d.jpg")).matlabId(1L).build();
        originalDetail.addImage(originalImage);
        detailSave.execute(originalDetail);

        Image savedimage = imageFindByMatlabIdDao.execute(originalImage.getMatlabId());
        assertNotNull(savedimage);
        assertEquals(savedimage.getMatlabId(), originalImage.getMatlabId());
    }
}
