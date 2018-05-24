package gw.identification.mapping.impl;

import gw.identification.mapping.DetailImageSaveMapping;
import gw.identification.util.ImagePath;
import gw.identification.view.DetailImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DetailImageSaveMappingImpl implements DetailImageSaveMapping {
    private static final Logger logger = LoggerFactory.getLogger(DetailImageSaveMappingImpl.class);

    @Autowired
    private ImagePath imagePath;

    @Override
    public DetailImageView toObject(MultipartFile from) throws IOException {
        String filename = from.getOriginalFilename();
        InputStream inputStream;
        try {
            inputStream = from.getInputStream();
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            throw e;
        }

        return DetailImageView.builder()
                .path(imagePath.determine(filename))
                .inputStream(inputStream)
                .build();
    }
}
