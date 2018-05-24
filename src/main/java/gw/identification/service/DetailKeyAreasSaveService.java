package gw.identification.service;

import gw.identification.fs.FSException;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.view.DetailImageView;

import java.util.ArrayList;

public interface DetailKeyAreasSaveService {
    void execute(ArrayList<DetailImageView> detailImageViews, String description) throws FSException, MatlabException;
}
