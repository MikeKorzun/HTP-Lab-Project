package gw.identification.dao.impl;

import gw.identification.dao.ImageFindByMatlabIdDao;
import gw.identification.model.Image;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageFindByMatlabIdDaoImpl implements ImageFindByMatlabIdDao {
    private final static String GET_IMAGE_BY_MATLAB_ID = "from Image i where i.matlabId=:matlabId";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Image execute(Long matlabId) {
        List<Image> images = (List<Image>) sessionFactory.getCurrentSession()
                .createQuery(GET_IMAGE_BY_MATLAB_ID)
                .setParameter("matlabId", matlabId)
                .list();
        Image image = images.get(images.size() - 1);
        return image;
    }
}
