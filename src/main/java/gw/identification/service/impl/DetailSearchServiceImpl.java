package gw.identification.service.impl;

import gw.identification.dao.DetailFindByDescriptionDao;
import gw.identification.dao.ImageFindByMatlabIdDao;
import gw.identification.dto.ImageSearchView;
import gw.identification.dto.MatlabView;
import gw.identification.fs.FSException;
import gw.identification.fs.ImageSave;
import gw.identification.matlab.ImageIdentify;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.model.Detail;
import gw.identification.model.Image;
import gw.identification.service.DetailSearchService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@PropertySource(value = "classpath:server.properties")
public class DetailSearchServiceImpl implements DetailSearchService {
    @Value("${contextPath}")
    private String contextPath;
    @Autowired
    private ImageSave imageSave;
    @Autowired
    private ImageIdentify imageIdentify;
    @Autowired
    private ImageFindByMatlabIdDao imageFindByMatlabId;
    @Autowired
    private DetailFindByDescriptionDao detailFindByDescriptionDao;

    @Override
    public List<ImageSearchView> execute(DetailImageView detailImageView) throws FSException, MatlabException {
        saveToFs(detailImageView);

        List<MatlabView> matlabViews = imageIdentify.execute(detailImageView.getPath());

        List<ImageSearchView> imageSearchViewList = new ArrayList<>();

        for (MatlabView matlabView : matlabViews) {
            Image image = imageFindByMatlabId.execute((long) matlabView.getMatlabId());
            Detail detail = image.getDetail();
            imageSearchViewList.add(ImageSearchView.builder()
                    .path(contextPath + image.getPath().replace('\\', '/'))
                    .description(detail.getDescription())
                    .coefficient(matlabView.getCoefficient())
                    .build());
        }

        return imageSearchViewList;
    }

    private void saveToFs(DetailImageView detailImageView) throws FSException {
        imageSave.execute(detailImageView.getInputStream(), detailImageView.getPath());
    }
}