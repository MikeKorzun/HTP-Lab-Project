package gw.identification.dao.impl;

import gw.identification.dao.DetailFindByDescriptionDao;
import gw.identification.model.Detail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetailFindByDescriptionDaoImpl implements DetailFindByDescriptionDao {
    private final static String GET_DETAIL_BY_DESCRIPTION = "FROM Detail WHERE description = :description";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Detail execute(String description) {
        return (Detail) sessionFactory.getCurrentSession()
                .createQuery(GET_DETAIL_BY_DESCRIPTION)
                .setParameter("description", description)
                .uniqueResult();
    }
}
