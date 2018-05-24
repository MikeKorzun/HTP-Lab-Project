package gw.identification.service;

import gw.identification.dto.ImageSearchView;
import gw.identification.fs.FSException;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.view.DetailImageView;

import java.util.List;

public interface DetailSearchService {
    List<ImageSearchView> execute(DetailImageView detailImageView) throws FSException, MatlabException;
}
