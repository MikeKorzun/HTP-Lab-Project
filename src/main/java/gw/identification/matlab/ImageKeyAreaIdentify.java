package gw.identification.matlab;

import gw.identification.dto.MatlabView;
import gw.identification.matlab.exception.MatlabException;

import java.nio.file.Path;
import java.util.List;

public interface ImageKeyAreaIdentify {
    List<MatlabView> execute(Path[] paths) throws MatlabException;
}
