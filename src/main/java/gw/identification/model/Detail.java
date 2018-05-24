package gw.identification.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
        image.setDetail(this);
    }

    public static class DetailBuilder {
        private Detail detail = new Detail();

        public DetailBuilder description(String description) {
            detail.description = description;
            return this;
        }
        public Detail build() {
            return detail;
        }
    }

    public static DetailBuilder builder() {
        return new DetailBuilder();
    }
}
