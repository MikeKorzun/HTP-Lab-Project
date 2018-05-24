package gw.identification.controller.response;

import gw.identification.dto.ImageSearchView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageSearchResponse {
    List<ImageSearchView> listObjectPOJO = new ArrayList<>();
}
