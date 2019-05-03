package dik.jhipster.collmate.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import dik.jhipster.collmate.domain.enumeration.State;

/**
 * A Dvd.
 */
@Document(collection = "dvd")
public class Dvd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("release_year")
    private String releaseYear;

    @Field("disk_count")
    private String diskCount;

    @Field("format")
    private String format;

    @Field("lang")
    private String lang;

    @Field("state")
    private State state;

    @Field("added")
    private Instant added;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Dvd name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public Dvd releaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDiskCount() {
        return diskCount;
    }

    public Dvd diskCount(String diskCount) {
        this.diskCount = diskCount;
        return this;
    }

    public void setDiskCount(String diskCount) {
        this.diskCount = diskCount;
    }

    public String getFormat() {
        return format;
    }

    public Dvd format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLang() {
        return lang;
    }

    public Dvd lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public State getState() {
        return state;
    }

    public Dvd state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Instant getAdded() {
        return added;
    }

    public Dvd added(Instant added) {
        this.added = added;
        return this;
    }

    public void setAdded(Instant added) {
        this.added = added;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dvd)) {
            return false;
        }
        return id != null && id.equals(((Dvd) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dvd{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", releaseYear='" + getReleaseYear() + "'" +
            ", diskCount='" + getDiskCount() + "'" +
            ", format='" + getFormat() + "'" +
            ", lang='" + getLang() + "'" +
            ", state='" + getState() + "'" +
            ", added='" + getAdded() + "'" +
            "}";
    }
}
