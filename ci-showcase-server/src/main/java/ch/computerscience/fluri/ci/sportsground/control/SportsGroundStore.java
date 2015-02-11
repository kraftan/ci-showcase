package ch.computerscience.fluri.ci.sportsground.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.computerscience.fluri.ci.sportsground.entity.Sport;
import ch.computerscience.fluri.ci.sportsground.entity.SportsGround;

/**
 * Store for accessing and storing persisted {@link SportsGround}s.
 * 
 * @author Beat Fluri
 */
@Stateless
public class SportsGroundStore {

    @PersistenceContext
    EntityManager em;

    /**
     * Returns all persisted {@link SportsGround}s.
     * 
     * @return all persisted sports grounds
     */
    public List<SportsGround> getAllSportsGrounds() {
        return em.createNamedQuery(SportsGround.FIND_ALL, SportsGround.class).getResultList();
    }

    /**
     * Persists the given {@link SportsGround}.
     * 
     * @param ground
     *            to persist
     */
    public void persist(SportsGround ground) {
        Sport sport = em.find(Sport.class, ground.getSport().getId());
        ground.setSport(sport);
        em.persist(ground);
    }
}
