package gw.identification.matlab.impl;

import com.mathworks.engine.MatlabEngine;
import gw.identification.dto.MatlabView;
import gw.identification.matlab.ImageIdentify;
import gw.identification.matlab.exception.MatlabException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@PropertySource(value = "classpath:matlab.properties")
public class ImageIdentifyImpl implements ImageIdentify {
    @Value("${function.identification}")
    private String command;

    @Autowired
    private MatlabEngine engine;

    @Override
    public List<MatlabView> execute(Path path) throws MatlabException {
        try {
            engine.putVariableAsync("PathName", path.getParent().toString());
            engine.putVariableAsync("FileName", path.getFileName().toString());
            engine.eval(function());

            Object[][] num = engine.getVariable("Num");
            Object err = engine.getVariable("err");

            if (err == null && num == null) {
                throw new MatlabException("Output parameter is null");
            }
            if (!"0.0".equals(err)) {
                throw new MatlabException("Matlab search error");
            }
            return result(num);
        } catch (InterruptedException | ExecutionException e) {
            throw new MatlabException(e.getMessage());
        }
    }

    private String function() {
        return String.format("[Num, err] = %s (PathName, FileName);", command);
    }

    private List<MatlabView> result(Object[][] num) {
        int x = num.length;
        int y = num[0].length;

        List<MatlabView> matlabViews = new ArrayList<>();

        Map<Integer, Integer> mapMatlabView = new HashMap<Integer, Integer>();

        for (int j = 0; j < y; j++) {
            mapMatlabView.put(Integer.parseInt((String) num[0][j]), Integer.parseInt((String) num[1][j]));
        }

        Map<Integer, Integer> mapUniqueMatlabView = getMapUniqueMatlabView(mapMatlabView);

        for (Map.Entry<Integer, Integer> entry : mapUniqueMatlabView.entrySet()) {
            MatlabView matlabView = MatlabView.builder()
                    .matlabId(entry.getKey())
                    .coefficient(entry.getValue())
                    .build();
            matlabViews.add(matlabView);
        }

        return matlabViews;
    }

    private Map<Integer, Integer> getMapUniqueMatlabView(Map<Integer, Integer> map) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            int id = entry.getKey() / 10;
            if (result.get(id) == null) {
                result.put(id, entry.getValue());
            } else {
                if (entry.getValue() > result.get(id)) {
                    result.put(id, entry.getValue());
                }
            }

        }
        return result;
    }
}