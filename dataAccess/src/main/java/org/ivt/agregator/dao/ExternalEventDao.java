package org.ivt.agregator.dao;

import java.util.List;

public interface ExternalEventDao<T> {

    /**
     * Найти события во внешней системе
     * @param text строка поиска
     * @param cityId идентификатор города
     * @return список событий
     */
    List<T> find(String text, int cityId);

    List<T> findAll(int cityId, int count, long offset);
}
