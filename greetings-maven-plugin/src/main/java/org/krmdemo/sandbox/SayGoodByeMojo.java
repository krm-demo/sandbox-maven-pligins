package org.krmdemo.sandbox;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * The simplest MOJO that says "Goodbye!" in either maven-log
 * or to standard-output and standard-error streams. The farewell message
 * and the destination of output (log-level) is configured via mojo-properties.
 */
@Mojo(name = "say-good-bye", requiresProject = false, defaultPhase = LifecyclePhase.NONE)
public class SayGoodByeMojo extends AbstractMojo {

    /**
     * The name (the target person of greetings) to say "Hello!" to
     */
    @Parameter(property = "farewell", defaultValue="Goodbye!")
    String farewell;

    /**
     * The log-level of "Hello!" message which corresponds
     * to enumeration {@link GreetingsLogLevel}
     */
    @Parameter(property = "log-level-good-bye", defaultValue="INFO")
    GreetingsLogLevel logLevel;

    @Override
    public void execute() {
        System.out.println("logLevel = " + logLevel);
        getLog().info(String.format(farewell));
    }
}
