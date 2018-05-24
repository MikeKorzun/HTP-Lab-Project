package gw.identification.service;

import gw.identification.dto.ImageSearchView;
import gw.identification.fs.FSException;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.view.DetailImageView;

import java.util.ArrayList;
import java.util.List;

public interface ImageKeyAreaSearchService {
    List<ImageSearchView> execute(ArrayList<DetailImageView> detailImageViews) throws FSException, MatlabException;
}
