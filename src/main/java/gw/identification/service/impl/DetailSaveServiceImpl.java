package gw.identification.service.impl;

import gw.identification.dao.DetailFindByDescriptionDao;
import gw.identification.dao.DetailSaveDao;
import gw.identification.fs.FSException;
import gw.identification.fs.ImageSave;
import gw.identification.matlab.ImageRegister;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.model.Detail;
import gw.identification.model.Image;
import gw.identification.service.DetailSaveService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class DetailSaveServiceImpl implements DetailSaveService {
    @Autowired
    private DetailSaveDao detailSave;

    @Autowired
    private DetailFindByDescriptionDao detailFindByDescription;

    @Autowired
    private ImageSave imageSave;

    @Autowired
    private ImageRegister imageRegister;

    @Override
    public Long execute(DetailImageView detailImageView) throws FSException, MatlabException {
        saveToFs(detailImageView);
        long registerId = imageRegister.execute(detailImageView.getPath());
        return saveToDb(detailImageView, registerId);
    }

    private void saveToFs(DetailImageView detailImageView) throws FSException {
        imageSave.execute(detailImageView.getInputStream(), detailImageView.getPath());
    }

    private Long saveToDb(DetailImageView detailImageView, long registerId) {
        Detail detail = detailFindByDescription.execute(detailImageView.getDescription());

        if (detail == null) {
            detail = Detail.builder().description(detailImageView.getDescription()).build();
        }

        Image image = Image.builder().path(detailImageView.getPath()).matlabId(registerId).build();
        detail.addImage(image);
        detailSave.execute(detail);
        return detail.getId();
    }
}
