package org.ivt.agregator.dao;

import org.hibernate.ejb.criteria.OrderImpl;
import org.ivt.agregator.entity.Event;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EventDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Event event) {
        em.persist(event);
    }

    public List<Event> get(int count, int offset) {
        checkArgs(count, offset);
        return em.createQuery("select e from Event e order by e.beginTime")
                .setFirstResult(offset).setMaxResults(count).getResultList();
    }

    private void checkArgs(int count, int offset) {
        if (count < 0 || offset < 0) {
            throw new IllegalArgumentException("Illegal count or offset");
        }
    }
}
