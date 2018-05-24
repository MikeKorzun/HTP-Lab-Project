package gw.identification.mapping.impl;

import gw.identification.mapping.DetailImageSaveMapping;
import gw.identification.mapping.DetailImagesSaveMapping;
import gw.identification.view.DetailImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class DetailImagesSaveMappingImpl implements DetailImagesSaveMapping {
    private static final Logger logger = LoggerFactory.getLogger(DetailImagesSaveMappingImpl.class);

    @Autowired
    private DetailImageSaveMapping detailImageSaveMapping;

    @Override
    public ArrayList<DetailImageView> toObject(MultipartFile[] multipartFiles) throws IOException {
        ArrayList<DetailImageView> detailImageViews = new ArrayList<>();
        try {
            for (MultipartFile file : multipartFiles) {
                detailImageViews.add(detailImageSaveMapping.toObject(file));
            }
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            throw e;
        }

        return detailImageViews;
    }
}
