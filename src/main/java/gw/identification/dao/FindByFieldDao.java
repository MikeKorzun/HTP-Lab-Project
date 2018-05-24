package gw.identification.dao;

public interface FindByFieldDao<T, R> {
    R execute(T field);
}
