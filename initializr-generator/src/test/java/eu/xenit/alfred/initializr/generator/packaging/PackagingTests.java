package eu.xenit.alfred.initializr.generator.packaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import eu.xenit.alfred.initializr.generator.packaging.amp.AmpPackaging;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.packaging.jar.JarPackaging;
import io.spring.initializr.generator.packaging.war.WarPackaging;
import org.junit.Test;

public class PackagingTests {

    @Test
    public void jarPackaging() {
        Packaging jar = Packaging.forId("jar");
        assertThat(jar).isInstanceOf(JarPackaging.class);
        assertThat(jar.id()).isEqualTo("jar");
        assertThat(jar.toString()).isEqualTo("jar");
    }

    @Test
    public void warPackaging() {
        // Do we want to disable .war packaging support ?
        Packaging war = Packaging.forId("war");
        assertThat(war).isInstanceOf(WarPackaging.class);
        assertThat(war.id()).isEqualTo("war");
        assertThat(war.toString()).isEqualTo("war");
    }

    @Test
    public void ampPackaging() {
        Packaging war = Packaging.forId("amp");
        assertThat(war).isInstanceOf(AmpPackaging.class);
        assertThat(war.id()).isEqualTo("amp");
        assertThat(war.toString()).isEqualTo("amp");
    }

    @Test
    public void unknownPackaging() {
        assertThatIllegalStateException().isThrownBy(() -> Packaging.forId("unknown"))
                .withMessageContaining("Unrecognized packaging id 'unknown'");
    }

}
