package ch.computerscience.fluri.ci.sportsground.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a sports ground on which at least one {@link Sport} can be practiced.
 * 
 * @author Beat Fluri
 * @author zubi
 */
@Entity
@NamedQuery(name = SportsGround.FIND_ALL, query = "from SportsGround")
@XmlRootElement
public class SportsGround extends AbstractEntity implements java.io.Serializable {

    private static final long serialVersionUID = -3944937037239806691L;
    private static final String PREFIX = "ch.computerscience.fluri.ci.sportsground.entity.SportsGround";

    public static final String FIND_ALL = PREFIX + ".findAll";

    private String description;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @ManyToOne
    private Sport sport;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Min(1)
    @Max(20)
    private int numberOfSportCourts;

    public SportsGround() {
        setRegistrationDate(new Date());
    }

    /**
     * Creates a new sports ground.
     * 
     * @param sport
     *            that will be played
     * @param name
     *            of the sports ground
     */
    public SportsGround(Sport sport, String name) {
        this();
        this.sport = sport;
        this.name = name;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SportsGround [id:");
        builder.append(getId());
        builder.append("; name:");
        builder.append(name);
        builder.append("; sport:");
        builder.append(sport);
        builder.append("; description:");
        builder.append(description);
        builder.append("; amountOfSportCourts:");
        builder.append(numberOfSportCourts);
        builder.append("; registrationDate:");
        builder.append(registrationDate);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SportsGround other = (SportsGround) obj;
        return new EqualsBuilder().append(getName(), other.getName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getName()).toHashCode();
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setNumberOfSportCourts(int amountOfSportCourts) {
        numberOfSportCourts = amountOfSportCourts;
    }

    public int getNumberOfSportCourts() {
        return numberOfSportCourts;
    }

}
