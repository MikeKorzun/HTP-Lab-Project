package gw.identification.matlab.impl;

import com.mathworks.engine.MatlabEngine;
import gw.identification.matlab.ImageKeyAreaRegister;
import gw.identification.matlab.exception.MatlabException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
@PropertySource(value = "classpath:matlab.properties")
public class ImageKeyAreaRegisterImpl implements ImageKeyAreaRegister {
    @Value("${function.imageKeyAreaRegistration}")
    private String command;

    @Autowired
    private MatlabEngine engine;

    @Override
    public long execute(Path[] paths) throws MatlabException {
        try {
            List<String> pathNamesList = new ArrayList<>();
            List<String> fileNamesList = new ArrayList<>();

            for (Path path : paths) {
                pathNamesList.add(path.getParent().toString());
                fileNamesList.add(path.getFileName().toString());
            }

            String[] pathNames = pathNamesList.toArray(new String[0]);
            String[] fileNames = fileNamesList.toArray(new String[0]);

            engine.putVariableAsync("PathNames", pathNames);
            engine.putVariableAsync("FileNames", fileNames);

            engine.eval(function());

            Object num = engine.getVariable("Num");
            Object err = engine.getVariable("err");

            if (err == null && num == null) {
                throw new MatlabException("Output parameter is null");
            }

            if (!"0.0".equals(err)) {
                throw new MatlabException("Matlab register error");
            }

            return result(num);


        } catch (InterruptedException | ExecutionException e) {
            throw new MatlabException(e.getMessage());
        }


    }

    private long result(Object num) {
        if (num instanceof String) {
            String matlabId = (String) num;
            return getMatlabIds(matlabId);
        }
        throw new ClassCastException("Output parameter is not String[]");
    }

    private long getMatlabIds(String value) {
        return Long.parseLong(value.substring(0, value.length() - 1));
    }

    private String function() {
        return String.format("[Num, err] = %s (PathNames, FileNames);", command);
    }
}
