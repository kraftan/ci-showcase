package ch.computerscience.fluri.ci.sportsground.boundary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.computerscience.fluri.ci.sportsground.control.SportsGroundStore;
import ch.computerscience.fluri.ci.sportsground.entity.SportsGround;

/**
 * REST service for accessing and persisting {@link SportsGround}s.
 * 
 * @author Beat Fluri
 */
@Path("/sportsground")
@Produces(MediaType.APPLICATION_XML)
@Stateless
public class SportsGrounds {

    @Inject
    SportsGroundStore store;

    @Resource
    Validator validator;

    /**
     * Returns all {@link SportsGround}.
     * 
     * @return all sports grounds
     */
    @GET
    public List<SportsGround> getSportsGrounds() {
        return store.getAllSportsGrounds();
    }

    /**
     * Stores a {@link SportsGround}.
     * 
     * @param ground
     *            to store
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void storeSportsGround(SportsGround ground) {
        validate(ground);
        store.persist(ground);
    }

    private void validate(SportsGround ground) {
        Set<ConstraintViolation<SportsGround>> violations = validator.validate(ground);
        if (!violations.isEmpty()) {
            Set<ConstraintViolation<?>> propagatedViolations = new HashSet<ConstraintViolation<?>>();
            for (ConstraintViolation<?> violation : violations) {
                propagatedViolations.add(violation);
            }
            throw new ConstraintViolationException(propagatedViolations);
        }
    }

}
