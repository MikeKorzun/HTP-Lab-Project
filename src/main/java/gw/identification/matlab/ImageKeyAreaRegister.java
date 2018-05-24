package gw.identification.matlab;

import gw.identification.matlab.exception.MatlabException;

import java.nio.file.Path;

public interface ImageKeyAreaRegister {
    long execute(Path[] paths) throws MatlabException;
}
