package org.ivt.agregator.rest;

import org.ivt.agregator.entity.Event;
import org.ivt.agregator.rest.dto.*;
import org.ivt.agregator.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.ivt.agregator.rest.dto.BaseResponse.ERROR_STATUS;

@Path("user/favorites")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class FavoritesController {

    private UserService userService;

    public FavoritesController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("add")
    public WithTokenResponse addToFavorites(final WithEventIdRequest request) {
        WithTokenResponse response = new WithTokenResponse();
        try {
            String newToken = userService.checkTokenWithUpdating(request.getToken());
            userService.addFavorite(newToken, request.getEventId());
            response.setToken(newToken);
        } catch (Exception e) {
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }

    @POST
    @Path("list")
    public FavoriteListResponse getFavorites(final WithTokenRequest request) {
        FavoriteListResponse response = new FavoriteListResponse();
        try {
            String token = request.getToken();
            List<Event> favorites = userService.getFavorites(token);
            response.setFavorites(favorites);
            response.setToken(token);
        } catch (Exception e) {
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }

    @POST
    @Path("remove")
    public WithTokenResponse removeFromFavorite(final WithEventIdRequest request) {
        WithTokenResponse response = new WithTokenResponse();
        try {
            String newToken = userService.checkTokenWithUpdating(request.getToken());
            userService.removeFavorite(newToken, request.getEventId());
            response.setToken(newToken);
        } catch (Exception e) {
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }

    @POST
    @Path("isFavorite")
    public IsFavoriteResponse isFavorite(final WithEventIdRequest request) {
        IsFavoriteResponse response = new IsFavoriteResponse();
        try {
            String newToken = userService.checkTokenWithUpdating(request.getToken());
            response.setFavorite(userService.isFavorite(newToken, request.getEventId()));
            response.setToken(newToken);
        } catch (Exception e) {
            response.setStatus(ERROR_STATUS);
        }
        return response;
    }
}
