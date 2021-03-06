package org.ivt.agregator.dao;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.integration.ExtSystem;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

public class EventDao {

    public static final String GET_FUTURE_EVENTS_QUERY = "select e from Event e " +
            "where (e.beginTime > :begin_time) or (e.endTime > :begin_time and e.endTime < :end_time ) " +
            "order by e.beginTime";
    private static Logger logger = Logger.getLogger(EventDao.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Event event) {
        logger.info(String.format("Saving event %s", event.getName()));
        em.merge(event);
    }

    public Event get(Long id) {
        return em.find(Event.class, id);
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

    /**
     * Получить грядущие события
     * @param count количество в запросе
     * @param offset смещение
     * @return
     */
    public List<Event> getFutureEvents(int count, int offset) {
        checkArgs(count, offset);
        Query query = em.createQuery(GET_FUTURE_EVENTS_QUERY);
        query.setParameter("begin_time", new Date());
        query.setParameter("end_time", getTomorrowDate());
        List resultList = query.setFirstResult(offset).setMaxResults(count).getResultList();
        logger.info(String.format("Получение всех событий по count %s offset %s", count, offset));
        return resultList;
    }

    private Date getTomorrowDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private void checkArgs(int count, int offset) {
        if (count < 0 || offset < 0) {
            throw new IllegalArgumentException("Illegal count or offset");
        }
    }
}
