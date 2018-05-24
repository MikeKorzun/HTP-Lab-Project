package gw.identification.matlab;


import gw.identification.matlab.exception.MatlabException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageRegisterTest {
    @Autowired
    private ImageRegister imageRegister;

    @Test
    public void execute() throws MatlabException {
        long result = imageRegister.execute(Paths.get("storage/38400000-8cf0-11bd-b23e-10b96e4ef00d.jpg"));

        assertEquals(1L, result);
    }
}
