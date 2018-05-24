package gw.identification.controller;

import gw.identification.controller.exception.ImageSaveException;
import gw.identification.controller.exception.ImageSearchException;
import gw.identification.controller.response.ImageSearchResponse;
import gw.identification.dto.ImageSearchView;
import gw.identification.fs.FSException;
import gw.identification.mapping.DetailImagesSaveMapping;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.service.ImageKeyAreaSearchService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/identify/images", consumes = "multipart/form-data")
public class ImageKeyAreaSearchController {
    @Autowired
    private ImageKeyAreaSearchService imageKeyAreaSearchService;

    @Autowired
    private DetailImagesSaveMapping detailImagesSaveMapping;

    @PostMapping
    public ResponseEntity<ImageSearchResponse> execute(@RequestParam("file") MultipartFile[] multipartFiles) throws ImageSearchException {
        try {
            ArrayList<DetailImageView> detailImageViews = detailImagesSaveMapping.toObject(multipartFiles);
            List<ImageSearchView> imageSearchViewList = imageKeyAreaSearchService.execute(detailImageViews);
            ImageSearchResponse response = new ImageSearchResponse();
            response.setListObjectPOJO(imageSearchViewList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FSException e) {
            throw new ImageSearchException(10001, "Could not save the file to the disk.");
        } catch (IOException e) {
            throw new ImageSearchException(20001, "Could not get InputStream from MultipartFile.");
        } catch (MatlabException e) {
            throw new ImageSearchException(30001, "Matlab registration failed.");
        }
    }
}
