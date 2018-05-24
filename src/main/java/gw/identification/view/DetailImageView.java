package gw.identification.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;
import java.nio.file.Path;

@Getter @Setter
@NoArgsConstructor
public class DetailImageView {
    private Path path;
    private InputStream inputStream;
    private String description;

    public static class DetailImageViewBuilder {
        private DetailImageView detailImageView = new DetailImageView();
        public DetailImageViewBuilder path(Path path) {
            detailImageView.path = path;
            return this;
        }
        public DetailImageViewBuilder inputStream(InputStream inputStream) {
            detailImageView.inputStream = inputStream;
            return this;
        }

        public DetailImageViewBuilder description(String description) {
            detailImageView.description = description;
            return this;
        }

        public DetailImageView build() {
            return detailImageView;
        }
    }
    public static DetailImageViewBuilder builder() {
        return new DetailImageViewBuilder();
    }
} 