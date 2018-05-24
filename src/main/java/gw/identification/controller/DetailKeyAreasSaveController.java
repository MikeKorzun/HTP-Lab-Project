package gw.identification.controller;

import gw.identification.controller.exception.ImageSaveException;
import gw.identification.controller.response.ImageSaveResponse;
import gw.identification.fs.FSException;
import gw.identification.mapping.DetailImagesSaveMapping;
import gw.identification.matlab.exception.MatlabException;
import gw.identification.service.DetailKeyAreasSaveService;
import gw.identification.view.DetailImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping(value = "/register/images", consumes = "multipart/form-data")
public class DetailKeyAreasSaveController {
    @Autowired
    private DetailKeyAreasSaveService detailKeyAreasSaveService;

    @Autowired
    private DetailImagesSaveMapping detailImagesSaveMapping;

    @PostMapping
    public ResponseEntity<ImageSaveResponse> execute(@RequestParam("file") MultipartFile[] multipartFiles,
                                                     @RequestParam("description") String description) throws ImageSaveException {
        try {
            ArrayList<DetailImageView> detailImageViews = detailImagesSaveMapping.toObject(multipartFiles);
            detailKeyAreasSaveService.execute(detailImageViews, description);
            return new ResponseEntity<>(new ImageSaveResponse("success"), HttpStatus.OK);
        } catch (FSException e) {
            throw new ImageSaveException(10001, "Could not save the file to the disk.");
        } catch (IOException e) {
            throw new ImageSaveException(20001, "Could not get InputStream from MultipartFile.");
        } catch (MatlabException e) {
            throw new ImageSaveException(30001, "Matlab registration failed.");
        }
    }
}
