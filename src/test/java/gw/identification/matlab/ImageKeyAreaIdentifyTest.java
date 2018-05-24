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
public class ImageKeyAreaIdentifyTest {
    @Autowired
    private ImageKeyAreaIdentify imageKeyAreaIdentify;

    @Test
    public void execute() throws MatlabException {
        Path[] paths = new Path[2];

        paths[0] = Paths.get("storage/0b7e4baf-6f4e-4981-8ddb-197eaad027d3.jpg");
        paths[1] = Paths.get("storage/badcc333-a42a-4bb1-ba4c-b16d321eef38");

        List<MatlabView> matlabViewList = imageKeyAreaIdentify.execute(paths);

        assertEquals(matlabViewList.get(0).getMatlabId(), 1);
        assertEquals(matlabViewList.get(0).getCoefficient(), 93);
        assertEquals(matlabViewList.get(1).getMatlabId(), 2);
        assertEquals(matlabViewList.get(1).getCoefficient(), 97);
    }
}
