package ch.computerscience.fluri.ci.sportsground.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ch.computerscience.fluri.ci.sportsground.entity.Sport;

/**
 * Store for accessing persisted {@link Sport}s.
 * 
 * @author Beat Fluri
 */
@Stateless
public class SportStore {

    @PersistenceContext
    EntityManager em;

    /**
     * Returns the persisted {@link Sport} with the given name.
     * 
     * @param name
     *            of the sport
     * @return the sport with the given name
     */
    public Sport getSportByName(String name) {
        TypedQuery<Sport> query = em.createNamedQuery(Sport.FIND_BY_NAME, Sport.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
