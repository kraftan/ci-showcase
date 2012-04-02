package net.comerge.ci.sportsground.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a kind of a sport users can practice on the Everest platform.
 * 
 * @author Beat Fluri, zubi
 */
@Entity
@NamedQuery(name = Sport.FIND_BY_NAME, query = "from Sport where name = :name")
@XmlRootElement
public class Sport extends AbstractEntity implements java.io.Serializable {

    private static final long serialVersionUID = -5982365216001507442L;
    private static final String PREFIX = "net.comerge.ci.sportsground.entity.Sport";

    public static final String FIND_BY_NAME = PREFIX + ".findByName";

    @NotNull
    @Column(unique = true)
    private String name;

    Sport() {
    }

    /**
     * Initializers new {@link Sport}.
     * 
     * @param name
     *            of the sport
     */
    public Sport(String name) {
        this();
        setName(name);
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
        builder.append("Sport [id:");
        builder.append(getId());
        builder.append("; name:");
        builder.append(name);
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
        Sport other = (Sport) obj;
        return new EqualsBuilder().append(getName(), other.getName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getName()).toHashCode();
    }

}
