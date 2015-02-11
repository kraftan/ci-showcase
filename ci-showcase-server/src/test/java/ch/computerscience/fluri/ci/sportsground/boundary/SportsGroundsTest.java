package ch.computerscience.fluri.ci.sportsground.boundary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;

import ch.computerscience.fluri.ci.sportsground.control.SportsGroundStore;
import ch.computerscience.fluri.ci.sportsground.entity.SportsGround;

import org.junit.Before;
import org.junit.Test;

public class SportsGroundsTest {

    private SportsGrounds sportsGrounds;

    @Before
    public void prepareSportsGrounds() {
        sportsGrounds = new SportsGrounds();
        sportsGrounds.store = mock(SportsGroundStore.class);
        sportsGrounds.validator = mock(Validator.class);
    }

    @Test
    public void storeSportsGroundShouldCallValidatorAndPersist() {
        SportsGround ground = new SportsGround();
        sportsGrounds.storeSportsGround(ground);
        verify(sportsGrounds.store, times(1)).persist(ground);
        verify(sportsGrounds.validator, times(1)).validate(ground);
    }

    @Test(expected = ConstraintViolationException.class)
    public void storeSportsGroundWithInvalidSportsGroundShouldFail() {
        SportsGround ground = new SportsGround();
        Set<ConstraintViolation<SportsGround>> violations = new HashSet<ConstraintViolation<SportsGround>>();
        violations.add(createConstraintViolation());
        when(sportsGrounds.validator.validate(ground)).thenReturn(violations);
        sportsGrounds.storeSportsGround(ground);
    }

    private ConstraintViolation<SportsGround> createConstraintViolation() {
        return new ConstraintViolation<SportsGround>() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public SportsGround getRootBean() {
                return null;
            }

            @Override
            public Class<SportsGround> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }
        };
    }
}
