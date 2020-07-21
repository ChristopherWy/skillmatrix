package de.skillmatrix.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Arbeitszeiten.
 */
@Entity
@Table(name = "arbeitszeiten")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Arbeitszeiten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "wochenstunden", nullable = false)
    private Integer wochenstunden;

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

    public Arbeitszeiten email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWochenstunden() {
        return wochenstunden;
    }

    public Arbeitszeiten wochenstunden(Integer wochenstunden) {
        this.wochenstunden = wochenstunden;
        return this;
    }

    public void setWochenstunden(Integer wochenstunden) {
        this.wochenstunden = wochenstunden;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arbeitszeiten)) {
            return false;
        }
        return id != null && id.equals(((Arbeitszeiten) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arbeitszeiten{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", wochenstunden=" + getWochenstunden() +
            "}";
    }
}
