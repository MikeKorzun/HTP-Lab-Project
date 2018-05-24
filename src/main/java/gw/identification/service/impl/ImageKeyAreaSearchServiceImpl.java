package gw.identification.service.impl;

import gw.identification.dao.ImageFindByMatlabIdDao;
import gw.identification.dto.ImageSearchView;
import gw.identification.dto.MatlabView;
import gw.identification.fs.FSException;
import gw.identification.fs.ImageSave;
import gw.identification.matlab.ImageKeyAreaIdentify;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.model.Detail;
import gw.identification.model.Image;
import gw.identification.service.ImageKeyAreaSearchService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@PropertySource(value = "classpath:server.properties")
public class ImageKeyAreaSearchServiceImpl implements ImageKeyAreaSearchService {
    @Value("${contextPath}")
    private String contextPath;

    @Autowired
    private ImageSave imageSave;

    @Autowired
    private ImageKeyAreaIdentify imageKeyAreaIdentify;

    @Autowired
    private ImageFindByMatlabIdDao imageFindByMatlabId;

    @Override
    public List<ImageSearchView> execute(ArrayList<DetailImageView> detailImageViews) throws FSException, MatlabException {
        for (DetailImageView detailImageView : detailImageViews) {
            saveToFs(detailImageView);
        }

        Path[] pathsArray = detailImageViewListToPathArray(detailImageViews);

        List<MatlabView> matlabViews = imageKeyAreaIdentify.execute(pathsArray);

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

    private Path[] detailImageViewListToPathArray(ArrayList<DetailImageView> detailImageViews) {
        int size = detailImageViews.size();
        Path[] paths = new Path[size];
        for (int i = 0; i < size; i++) {
            paths[i] = detailImageViews.get(i).getPath();
        }
        return paths;
    }
}
