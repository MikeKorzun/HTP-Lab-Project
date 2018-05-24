package gw.identification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageSearchView {
    private String path;
    private String description;
    private Integer coefficient;

    public static class ImageSearchViewBuilder {
        private ImageSearchView imageSearchView = new ImageSearchView();

        public ImageSearchViewBuilder path(String path) {
            imageSearchView.path = path;
            return this;
        }

        public ImageSearchViewBuilder description(String description) {
            imageSearchView.description = description;
            return this;
        }

        public ImageSearchViewBuilder coefficient(Integer coefficient) {
            imageSearchView.coefficient = coefficient;
            return this;
        }

        public ImageSearchView build() {
            return imageSearchView;
        }
    }
    public static ImageSearchViewBuilder builder() {
        return new ImageSearchViewBuilder();
    }
}
