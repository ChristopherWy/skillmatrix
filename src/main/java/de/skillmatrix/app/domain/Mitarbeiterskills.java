package de.skillmatrix.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Mitarbeiterskills.
 */
@Entity
@Table(name = "mitarbeiterskills")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mitarbeiterskills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @ManyToOne
    @JsonIgnoreProperties(value = "mitarbeiterskills", allowSetters = true)
    private Skill skill;

    @ManyToOne
    @JsonIgnoreProperties(value = "mitarbeiterskills", allowSetters = true)
    private Mitarbeiter email;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public Mitarbeiterskills level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Skill getSkill() {
        return skill;
    }

    public Mitarbeiterskills skill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Mitarbeiter getEmail() {
        return email;
    }

    public Mitarbeiterskills email(Mitarbeiter mitarbeiter) {
        this.email = mitarbeiter;
        return this;
    }

    public void setEmail(Mitarbeiter mitarbeiter) {
        this.email = mitarbeiter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mitarbeiterskills)) {
            return false;
        }
        return id != null && id.equals(((Mitarbeiterskills) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mitarbeiterskills{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            "}";
    }
}
