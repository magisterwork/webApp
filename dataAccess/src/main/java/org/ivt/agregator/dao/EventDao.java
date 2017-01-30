package org.ivt.agregator.dao;

import org.ivt.agregator.entity.Event;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EventDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Event event) {
        em.persist(event);
    }
}
