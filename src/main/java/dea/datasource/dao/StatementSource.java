package dea.datasource.dao;

public interface StatementSource {
    String sql();
    Object[] parameters();
}
