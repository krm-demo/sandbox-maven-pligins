package org.krmdemo.sandbox;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * The simplest MOJO that says "Hello!" to {@link #helloTo} in maven-log and in generated output file.
 */
@Mojo(name = "say-hello", requiresProject = false, defaultPhase = LifecyclePhase.NONE)
public class SayHelloMojo extends AbstractMojo {

    /**
     * The name (the target person of greetings) to say "Hello!" to
     */
    @Parameter(property = "hello-to", defaultValue="World")
    String helloTo;

    /**
     * The log-level of "Hello!" message which corresponds
     * to enumeration {@link GreetingsLogLevel}
     */
    @Parameter(property = "log-level-hello", defaultValue="INFO")
    GreetingsLogLevel logLevel;

    /**
     * the current maven-project and will be injected by maven itself.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    /**
     * This parameter can't be configured in the {@code pom.xml}.<hr/>
     * It represents the current maven-session and will be injected by maven itself.
     */
    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    MavenSession session;

    /**
     * This parameter can't be configured in the {@code pom.xml}.
     * It represents the execution of current mojo inside the maven-project.
     * The mojo could be invoked multiple times and with different parameters.
     */
    @Parameter(defaultValue = "${mojoExecution}", readonly = true, required = false)
    MojoExecution mojoEx;

    @Override
    @SuppressWarnings("unchecked")
    public void execute() {
        System.out.println("logLevel = " + logLevel);
        getLog().info(String.format("Hello, %s!", helloTo));
        if (!session.getRequest().isProjectPresent()) {
            getLog().warn("not inside the maven-project");
        } else {
            getLog().info(String.format("inside the maven-project '%s'", project.getName()));
        }
        System.out.printf("session.currentProject = '%s';%n", session.getCurrentProject());
        System.out.printf("session.topLevelProject = '%s';%n", session.getTopLevelProject());
        System.out.printf("allProjects.size = %d;%n", session.getAllProjects().size());
        System.out.printf("projects.size = %d;%n", session.getProjects().size());

        System.out.println("session.systemProps:\n" + dumpProps(session.getSystemProperties()));
        System.out.println("session.userProps:\n" + dumpProps(session.getUserProperties()));

        System.out.println("plugin context:");
        getPluginContext().forEach((key, value) -> {
            System.out.printf("- '%s': %s --> %s;%n", key, value.getClass(), value);
        });
    }

    private String dumpProps(Properties props) {
        return dumpMap(toSortedMap(props));
    }

    private String dumpMap(Map<?,?> mapToDump) {
        return mapToDump.entrySet().stream()
            .map(e -> String.format("- %s: \"%s\"", e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n",
                String.format("## ---- %3d items: ---- ##\n", mapToDump.size()),
                            "\n## ---------------------##"));
    }

    private SortedMap<String, String> toSortedMap(Properties props) {
        return props.entrySet().stream().collect(toMap(
            this::propName, this::propValueEscaped, (x, y) -> y, TreeMap::new));
    }

    private String propName(Map.Entry<Object,Object> propEntry) {
        return "" + propEntry.getKey();
    }

    private String propValueEscaped(Map.Entry<Object,Object> propEntry) {
        return StringEscapeUtils.escapeJava("" + propEntry.getValue());
    }
}
