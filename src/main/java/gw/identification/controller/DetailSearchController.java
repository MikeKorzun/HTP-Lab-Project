package gw.identification.controller;

import gw.identification.controller.exception.ImageSearchException;
import gw.identification.controller.response.ImageSearchResponse;
import gw.identification.dto.ImageSearchView;
import gw.identification.fs.FSException;
import gw.identification.mapping.DetailImageSaveMapping;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.service.DetailSearchService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/identify/image", consumes = "multipart/form-data", produces = "application/json")
public class DetailSearchController {
    @Autowired
    private DetailSearchService detailSearchService;
    @Autowired
    private DetailImageSaveMapping detailImageSaveMapping;

    @PostMapping
    public ResponseEntity<ImageSearchResponse> execute(@RequestParam("file") MultipartFile multipartFile ) throws ImageSearchException {
        try {
            DetailImageView detailImageView = detailImageSaveMapping.toObject(multipartFile);
            List<ImageSearchView> imageSearchViewList = detailSearchService.execute(detailImageView);
            ImageSearchResponse response = new ImageSearchResponse();
            response.setListObjectPOJO(imageSearchViewList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            throw new ImageSearchException(20001, "Could not get InputStream from MultipartFile.");
        } catch (MatlabException e) {
            throw new ImageSearchException(30001, "Identification failed.");
        } catch (FSException e) {
            throw new ImageSearchException(10001, "Could not save the file to the disk.");
        }
    }
}