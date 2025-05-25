package cz.esgaltur.maxweight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test to verify that Tomcat is not on the classpath.
 */
public class WebServerTest {

    /**
     * Test that Tomcat is not on the classpath.
     */
    @Test
    public void testTomcatIsNotOnClasspath() {
        // Try to load a Tomcat class
        assertThrows(ClassNotFoundException.class, () -> {
            Class.forName("org.apache.catalina.startup.Tomcat");
        }, "Tomcat should not be on the classpath");

        // Print debug information
        System.out.println("[DEBUG_LOG] Tomcat is not on the classpath");

        // Verify Undertow is on the classpath
        try {
            Class<?> undertowClass = Class.forName("io.undertow.Undertow");
            System.out.println("[DEBUG_LOG] Undertow is on the classpath: " + undertowClass.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("[DEBUG_LOG] Undertow is not on the classpath");
            throw new AssertionError("Undertow should be on the classpath", e);
        }
    }
}
