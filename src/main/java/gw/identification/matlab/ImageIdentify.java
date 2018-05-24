package gw.identification.matlab;

import gw.identification.dto.MatlabView;
import gw.identification.matlab.exception.MatlabException;

import java.nio.file.Path;
import java.util.List;

public interface ImageIdentify {
    List<MatlabView> execute(Path path) throws MatlabException;
}