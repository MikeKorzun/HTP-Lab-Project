package gw.identification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatlabView {
    private int matlabId;
    private int coefficient;

    public static class MatlabViewBuilder {
        private MatlabView matlabView = new MatlabView();
        public MatlabViewBuilder matlabId(int matlabId) {
            matlabView.matlabId = matlabId;
            return this;
        }
        public MatlabViewBuilder coefficient(int coefficient) {
            matlabView.coefficient = coefficient;
            return this;
        }

        public MatlabView build() {
            return matlabView;
        }
    }
    public static MatlabViewBuilder builder() {
        return new MatlabViewBuilder();
    }
}
