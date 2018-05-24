package gw.identification.service;

import gw.identification.fs.FSException;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.view.DetailImageView;

public interface DetailSaveService {
    Long execute(DetailImageView detailImageView) throws FSException, MatlabException;
}
