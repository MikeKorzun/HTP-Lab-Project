package gw.identification.matlab;

import gw.identification.matlab.exception.MatlabException;

import java.nio.file.Path;

public interface ImageRegister {
    Long execute(Path path) throws MatlabException;
}
