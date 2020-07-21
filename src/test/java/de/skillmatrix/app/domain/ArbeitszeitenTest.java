package de.skillmatrix.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.skillmatrix.app.web.rest.TestUtil;

public class ArbeitszeitenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arbeitszeiten.class);
        Arbeitszeiten arbeitszeiten1 = new Arbeitszeiten();
        arbeitszeiten1.setId(1L);
        Arbeitszeiten arbeitszeiten2 = new Arbeitszeiten();
        arbeitszeiten2.setId(arbeitszeiten1.getId());
        assertThat(arbeitszeiten1).isEqualTo(arbeitszeiten2);
        arbeitszeiten2.setId(2L);
        assertThat(arbeitszeiten1).isNotEqualTo(arbeitszeiten2);
        arbeitszeiten1.setId(null);
        assertThat(arbeitszeiten1).isNotEqualTo(arbeitszeiten2);
    }
}
