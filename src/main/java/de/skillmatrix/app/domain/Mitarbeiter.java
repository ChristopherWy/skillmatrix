package de.skillmatrix.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Mitarbeiter.
 */
@Entity
@Table(name = "mitarbeiter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mitarbeiter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @Column(name = "intern")
    private Boolean intern;

    @OneToOne
    @JoinColumn(unique = true)
    private Arbeitszeiten email;

    @OneToMany(mappedBy = "email")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Mitarbeiterskills> mitarbeiterskills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Mitarbeiter email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public Mitarbeiter vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Mitarbeiter nachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Boolean isIntern() {
        return intern;
    }

    public Mitarbeiter intern(Boolean intern) {
        this.intern = intern;
        return this;
    }

    public void setIntern(Boolean intern) {
        this.intern = intern;
    }

    public Arbeitszeiten getEmail() {
        return email;
    }

    public Mitarbeiter email(Arbeitszeiten arbeitszeiten) {
        this.email = arbeitszeiten;
        return this;
    }

    public void setEmail(Arbeitszeiten arbeitszeiten) {
        this.email = arbeitszeiten;
    }

    public Set<Mitarbeiterskills> getMitarbeiterskills() {
        return mitarbeiterskills;
    }

    public Mitarbeiter mitarbeiterskills(Set<Mitarbeiterskills> mitarbeiterskills) {
        this.mitarbeiterskills = mitarbeiterskills;
        return this;
    }

    public Mitarbeiter addMitarbeiterskills(Mitarbeiterskills mitarbeiterskills) {
        this.mitarbeiterskills.add(mitarbeiterskills);
        mitarbeiterskills.setEmail(this);
        return this;
    }

    public Mitarbeiter removeMitarbeiterskills(Mitarbeiterskills mitarbeiterskills) {
        this.mitarbeiterskills.remove(mitarbeiterskills);
        mitarbeiterskills.setEmail(null);
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
        if (!(o instanceof Mitarbeiter)) {
            return false;
        }
        return id != null && id.equals(((Mitarbeiter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mitarbeiter{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", nachname='" + getNachname() + "'" +
            ", intern='" + isIntern() + "'" +
            "}";
    }
}
