package gw.identification.mapping;

import gw.identification.view.DetailImageView;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface DetailImagesSaveMapping extends Mapping<MultipartFile[], ArrayList<DetailImageView>> {
}
