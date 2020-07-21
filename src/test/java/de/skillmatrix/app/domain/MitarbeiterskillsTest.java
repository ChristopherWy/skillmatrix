package de.skillmatrix.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.skillmatrix.app.web.rest.TestUtil;

public class MitarbeiterskillsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mitarbeiterskills.class);
        Mitarbeiterskills mitarbeiterskills1 = new Mitarbeiterskills();
        mitarbeiterskills1.setId(1L);
        Mitarbeiterskills mitarbeiterskills2 = new Mitarbeiterskills();
        mitarbeiterskills2.setId(mitarbeiterskills1.getId());
        assertThat(mitarbeiterskills1).isEqualTo(mitarbeiterskills2);
        mitarbeiterskills2.setId(2L);
        assertThat(mitarbeiterskills1).isNotEqualTo(mitarbeiterskills2);
        mitarbeiterskills1.setId(null);
        assertThat(mitarbeiterskills1).isNotEqualTo(mitarbeiterskills2);
    }
}
