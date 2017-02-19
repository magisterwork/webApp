package org.ivt.agregator.dao;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

public class UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(User user) {
        LOGGER.info(String.format("Saving user %s", user.getName()));
        em.merge(user);
    }

    public User findByGoogleID(String googleId) {
        Validate.notEmpty(googleId);
        TypedQuery<User> query = em.createQuery("select u from User u where u.googleId = :gId", User.class);
        query.setParameter("gId", googleId);
        List<User> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            LOGGER.info(String.format("По googleId %s найден пользователь.", googleId));
            return resultList.get(0);
        }
        LOGGER.info(String.format("По googleI %s %s не найден пользователь.", googleId));
        return null;
    }

    @Transactional
    public void updateInnerToken(User user, String innerToken) {
        Validate.notNull(user);
        Validate.notEmpty(innerToken);
        User got = em.find(User.class, user.getId());
        got.setToken(innerToken);
        em.merge(got);
    }
}
