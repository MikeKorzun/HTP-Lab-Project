package gw.identification.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.nio.file.Path;
import java.util.Date;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private String path;
    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;
    @Column(name = "matlab_id")
    private Long matlabId;

    public static class ImageBuilder {
        private Image image = new Image();

        public ImageBuilder path(Path path) {
            image.path = path.toString();
            return this;
        }

        public ImageBuilder matlabId(long matlabId) {
            image.matlabId = matlabId;
            return this;
        }

        public Image build() {
            return image;
        }
    }

    public static ImageBuilder builder() {
        return new ImageBuilder();
    }
}