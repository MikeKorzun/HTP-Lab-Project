package gw.identification.matlab.impl;

import com.mathworks.engine.MatlabEngine;
import gw.identification.matlab.ImageRegister;
import gw.identification.matlab.exception.MatlabException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

@Service
@PropertySource(value = "classpath:matlab.properties")
public class ImageRegisterImpl implements ImageRegister {
    @Value("${function.registration}")
    private String command;
    @Autowired
    private MatlabEngine engine;
    @Override
    public Long execute(Path path) throws MatlabException {
        try {
            engine.putVariableAsync("PathName", path.getParent().toString());
            engine.putVariableAsync("FileName", path.getFileName().toString());
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
    private Long result(Object num) {
        if (num instanceof String[]) {
            return matlabId(((String[])num)[0]);
        }
        else if (num instanceof String) {
            return matlabId(((String) num));
        }
        throw new ClassCastException("Output parameter is not String[] or String");
    }
    private Long matlabId(String value) {
        return Long.parseLong(value.substring(0, value.length() - 1));
    }
    private String function() {
        return String.format("[Num, err] = %s (PathName, FileName);", command);
    }
}

