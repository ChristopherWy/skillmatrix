package de.skillmatrix.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.skillmatrix.app.web.rest.TestUtil;

public class MitarbeiterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mitarbeiter.class);
        Mitarbeiter mitarbeiter1 = new Mitarbeiter();
        mitarbeiter1.setId(1L);
        Mitarbeiter mitarbeiter2 = new Mitarbeiter();
        mitarbeiter2.setId(mitarbeiter1.getId());
        assertThat(mitarbeiter1).isEqualTo(mitarbeiter2);
        mitarbeiter2.setId(2L);
        assertThat(mitarbeiter1).isNotEqualTo(mitarbeiter2);
        mitarbeiter1.setId(null);
        assertThat(mitarbeiter1).isNotEqualTo(mitarbeiter2);
    }
}
