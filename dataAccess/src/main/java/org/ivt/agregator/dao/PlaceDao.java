package org.ivt.agregator.dao;

import org.ivt.agregator.entity.Place;
import org.ivt.agregator.entity.PlaceCategory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

public class PlaceDao {

    private final static Logger LOGGER = Logger.getLogger(PlaceDao.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Place> getAll(PlaceCategory category, int count, int offset) {
        Query query = em.createQuery("select p from Place p where :category member p.categories");
        query.setFirstResult(offset);
        query.setMaxResults(offset + count);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<Place> getAll(int count, int offset) {
        Query query = em.createQuery("select p from Place p where p.googleId is not null");
        query.setFirstResult(offset);
        query.setMaxResults(offset + count);
        return query.getResultList();
    }

    @Transactional
    public void save(Place place) {
        Place exist = getByGoogleId(place.getGoogleId());
        if (exist != null) {
            place.setId(exist.getId());
            em.merge(place);
            LOGGER.info("Место обновлено " + place.getName());
        } else {
            em.merge(place);
            LOGGER.info("Место сохранено " + place.getName());
        }
    }

    public Place getByGoogleId(String googleId) {
        TypedQuery<Place> query = em.createQuery("select p from Place p where p.googleId = :qid", Place.class);
        query.setParameter("qid", googleId);
        List<Place> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
}
