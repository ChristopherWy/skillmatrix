package de.skillmatrix.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "skill", nullable = false)
    private String skill;

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Mitarbeiterskills> mitarbeiterskills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public Skill skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Set<Mitarbeiterskills> getMitarbeiterskills() {
        return mitarbeiterskills;
    }

    public Skill mitarbeiterskills(Set<Mitarbeiterskills> mitarbeiterskills) {
        this.mitarbeiterskills = mitarbeiterskills;
        return this;
    }

    public Skill addMitarbeiterskills(Mitarbeiterskills mitarbeiterskills) {
        this.mitarbeiterskills.add(mitarbeiterskills);
        mitarbeiterskills.setSkill(this);
        return this;
    }

    public Skill removeMitarbeiterskills(Mitarbeiterskills mitarbeiterskills) {
        this.mitarbeiterskills.remove(mitarbeiterskills);
        mitarbeiterskills.setSkill(null);
        return this;
    }

    public void setMitarbeiterskills(Set<Mitarbeiterskills> mitarbeiterskills) {
        this.mitarbeiterskills = mitarbeiterskills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", skill='" + getSkill() + "'" +
            "}";
    }
}
