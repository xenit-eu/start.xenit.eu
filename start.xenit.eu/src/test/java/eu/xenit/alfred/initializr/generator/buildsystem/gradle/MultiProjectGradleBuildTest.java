package eu.xenit.alfred.initializr.generator.buildsystem.gradle;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class MultiProjectGradleBuildTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        new MultiProjectGradleBuild(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        new MultiProjectGradleBuild("");
    }

    @Test
    public void testParentChildProject() {

        MultiProjectGradleBuild root = new MultiProjectGradleBuild("parent");

        MultiProjectGradleBuild child = new MultiProjectGradleBuild("child", null, root);
        assertThat(root.getModules(), hasEntry("child", child));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateChildProject() {

        MultiProjectGradleBuild root = new MultiProjectGradleBuild("parent");

        new MultiProjectGradleBuild("duplicate", null, root);
        new MultiProjectGradleBuild("duplicate", null, root);

    }

}