package gw.identification.service.impl;

import gw.identification.dao.DetailFindByDescriptionDao;
import gw.identification.dao.DetailSaveDao;
import gw.identification.fs.FSException;
import gw.identification.fs.ImageSave;
import gw.identification.matlab.ImageKeyAreaRegister;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.model.Detail;
import gw.identification.model.Image;
import gw.identification.service.DetailKeyAreasSaveService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.util.ArrayList;

@Service
@Transactional
public class DetailKeyAreasSaveServiceImpl implements DetailKeyAreasSaveService {
    @Autowired
    private DetailFindByDescriptionDao detailFindByDescriptionDao;

    @Autowired
    private ImageSave imageSave;

    @Autowired
    private ImageKeyAreaRegister imageKeyAreaRegister;

    @Autowired
    private DetailSaveDao detailSaveDao;

    @Override
    public void execute(ArrayList<DetailImageView> detailImageViews, String description) throws FSException, MatlabException {
        for (DetailImageView detailImageView : detailImageViews) {
            saveToFs(detailImageView);
        }

        Path[] pathsArray = detailImageViewListToPathArray(detailImageViews);
        long matlabId = imageKeyAreaRegister.execute(pathsArray);

        Detail detail = checkDetailExist(description);

        Image image = Image.builder()
                .path(detailImageViews.get(0).getPath())
                .matlabId(matlabId)
                .build();
        detail.addImage(image);

        detailSaveDao.execute(detail);
    }

    private Detail checkDetailExist(String description) {
        Detail detail = detailFindByDescriptionDao.execute(description);

        if (detail == null) {
            detail = Detail.builder().description(description).build();
        }

        return detail;
    }

    private void saveToFs(DetailImageView detailImageView) throws FSException {
        imageSave.execute(detailImageView.getInputStream(), detailImageView.getPath());
    }

    private Path[] detailImageViewListToPathArray(ArrayList<DetailImageView> detailImageViews) {
        int size = detailImageViews.size();
        Path[] paths = new Path[size];
        for (int i = 0; i < size; i++) {
            paths[i] = detailImageViews.get(i).getPath();
        }
        return paths;
    }
}
