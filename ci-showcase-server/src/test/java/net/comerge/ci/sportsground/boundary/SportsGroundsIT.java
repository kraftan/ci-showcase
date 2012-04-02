package net.comerge.ci.sportsground.boundary;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

import net.comerge.ci.sportsground.control.SportStore;
import net.comerge.ci.sportsground.control.SportsGroundStore;
import net.comerge.ci.sportsground.entity.Sport;
import net.comerge.ci.sportsground.entity.SportsGround;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SportsGroundsIT {

    @Deployment
    public static Archive<?> createTestArchive() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
        archive.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
        archive.addAsResource("import.sql", "import.sql");
        archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsManifestResource("jboss-deployment-structure.xml");
        archive.addPackage(SportsGrounds.class.getPackage());
        archive.addPackage(SportsGroundStore.class.getPackage());
        archive.addPackage(SportsGround.class.getPackage());
        return archive;
    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Inject
    private SportsGrounds sportsGrounds;

    @Inject
    private SportStore sportStore;

    @Inject
    private SportsGroundStore sportsGroundStore;

    private Sport sport;

    @Before
    public void prepareObjects() {
        sport = sportStore.getSportByName("Basketball");
    }

    @After
    public void cleanupObjects() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from SportsGround").executeUpdate();
        utx.commit();
    }

    @Test
    public void storeSportsGroundShouldPersistSportsGround() {
        SportsGround ground = new SportsGround();
        ground.setName("my sports ground");
        ground.setDescription("playing at my house");
        ground.setNumberOfSportCourts(1);
        ground.setSport(sport);
        sportsGrounds.storeSportsGround(ground);
        List<SportsGround> result = sportsGroundStore.getAllSportsGrounds();
        assertThat(result, hasSize(2));
        assertThat(result, hasItem(ground));
    }

    @Test
    public void storeSportsGroundWithoutNameShouldFail() {
        try {
            sportsGrounds.storeSportsGround(new SportsGround());
            Assert.fail("Expected a ConstraintViolationException");
        } catch (Exception e) {
            assertThat(e, instanceOf(EJBException.class));
            assertThat(e.getCause(), instanceOf(ConstraintViolationException.class));
        }
        assertThat(sportsGroundStore.getAllSportsGrounds(), hasSize(0));
    }

    @Test
    public void storeSportsGroundWithTooMuchSportCourtsShouldFail() {
        try {
            SportsGround ground = new SportsGround();
            ground.setName("my place");
            ground.setNumberOfSportCourts(21);
            sportsGrounds.storeSportsGround(new SportsGround());
            Assert.fail("Expected a ConstraintViolationException");
        } catch (Exception e) {
            assertThat(e, instanceOf(EJBException.class));
            assertThat(e.getCause(), instanceOf(ConstraintViolationException.class));
        }
        assertThat(sportsGroundStore.getAllSportsGrounds(), hasSize(0));
    }
}
