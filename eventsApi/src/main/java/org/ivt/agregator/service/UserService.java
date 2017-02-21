package org.ivt.agregator.service;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.dao.EventDao;
import org.ivt.agregator.dao.UserDao;
import org.ivt.agregator.entity.Event;
import org.ivt.agregator.entity.User;
import org.ivt.agregator.service.exception.IllegalTokenException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserService {

    private UserDao userDao;
    private EventDao eventDao;
    private TokenGenerator tokenGenerator;

    /**
     * Добавить событие в избранное
     * @param userToken
     * @param eventId
     * @return новый токен
     */
    @Transactional
    public String addFavorite(String userToken, Long eventId) {
        Validate.notNull(eventId);
        User user = findUserByToken(userToken);
        addFavoriteToUser(eventId, user);

        String newToken = tokenGenerator.getToken();
        user.setToken(newToken);
        userDao.save(user);
        return newToken;
    }

    private void addFavoriteToUser(Long eventId, User user) {
        List<Event> favoriteEvents = user.getFavoriteEvents();
        Event event = eventDao.get(eventId);
        Validate.notNull(event);
        favoriteEvents.add(event);
    }

    private User findUserByToken(String userToken) {
        Validate.notEmpty(userToken);
        User user = userDao.findByToken(userToken);
        if (user == null) {
            throw new IllegalTokenException("User not found for userToken " + userToken);
        }
        return user;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }
}
