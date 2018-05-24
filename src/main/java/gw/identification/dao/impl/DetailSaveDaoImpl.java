package gw.identification.dao.impl;

import gw.identification.dao.DetailSaveDao;
import gw.identification.model.Detail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetailSaveDaoImpl implements DetailSaveDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void execute(Detail detail) {
        sessionFactory.getCurrentSession().saveOrUpdate(detail);
    }
}
