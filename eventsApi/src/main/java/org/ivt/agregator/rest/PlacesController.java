package org.ivt.agregator.rest;

import org.ivt.agregator.dao.PlaceDao;
import org.ivt.agregator.entity.Place;
import org.ivt.agregator.entity.PlaceCategory;

import javax.ws.rs.*;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/places")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class PlacesController {

    private static final Logger LOGGER = Logger.getLogger(PlacesController.class.getName());

    public PlacesController(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    private PlaceDao placeDao;

    @GET
    @Path("list")
    public List<Place> getAll(@QueryParam("category") String category,
                              @QueryParam("count") int count,
                              @QueryParam("offset") int offset) {
        if (category != null) {
            PlaceCategory placeCategory = PlaceCategory.valueOf(category);
            LOGGER.info("Отдаем заведения категории " + category);
            return placeDao.getAll(placeCategory, count, offset);
        } else {
            LOGGER.info("Отдаем все заведения");
            return placeDao.getAll(count, offset);
        }
    }

    @GET
    @Path("categories")
    public PlaceCategory[] getCategories() {
        LOGGER.info("Отдаем все категории");
        return PlaceCategory.values();
    }
}
