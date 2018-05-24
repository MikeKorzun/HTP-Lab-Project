package gw.identification.dao;

public interface SaveDao<T> {
    void execute(T entity);
}
