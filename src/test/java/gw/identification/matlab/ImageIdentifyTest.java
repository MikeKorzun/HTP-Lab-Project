package gw.identification.matlab;

import gw.identification.dto.MatlabView;
import gw.identification.matlab.exception.MatlabException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageIdentifyTest {
    @Autowired
    private ImageIdentify imageIdentify;

    @Test
    public void execute() throws MatlabException {
        Path path =  Paths.get("storage/0b7e4baf-6f4e-4981-8ddb-197eaad027d3.jpg");


        List<MatlabView> matlabViewList = imageIdentify.execute(path);

        assertEquals(matlabViewList.get(0).getMatlabId(), 1);
        assertEquals(matlabViewList.get(0).getCoefficient(), 90);
        assertEquals(matlabViewList.get(1).getMatlabId(), 2);
        assertEquals(matlabViewList.get(1).getCoefficient(), 99);
    }
}
