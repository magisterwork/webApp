package org.ivt.agregator.dao;

import org.apache.commons.lang3.Validate;
import org.hibernate.ejb.criteria.OrderImpl;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class EventDao {

    private static Logger logger = Logger.getLogger(EventDao.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Event event) {
        logger.info(String.format("Saving event %s", event.getName()));
        em.persist(event);
    }

    public Event getByExtId(String extId, ExtSystem extSystem) {
        Validate.notEmpty(extId);
        TypedQuery<Event> query = em.createQuery("select e from Event e where e.extId = :extId and e.extSystem = :extSystem", Event.class);
        query.setParameter("extId", extId);
        query.setParameter("extSystem", extSystem);
        List<Event> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            logger.info(String.format("По extId %s %s найдено событие.", extId, extSystem));
            return resultList.get(0);
        }
        logger.info(String.format("По extId %s %s не найдено событие.", extId, extSystem));
        return null;
    }

    public List<Event> getAll(int count, int offset) {
        checkArgs(count, offset);
        List resultList = em.createQuery("select e from Event e order by e.beginTime")
                .setFirstResult(offset).setMaxResults(count).getResultList();
        logger.info(String.format("Получение всех событий по count %s offset %s", count, offset));
        return resultList;
    }

    private void checkArgs(int count, int offset) {
        if (count < 0 || offset < 0) {
            throw new IllegalArgumentException("Illegal count or offset");
        }
    }
}
