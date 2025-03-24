package org.krmdemo.sandbox;


import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "say-good-bye", requiresProject = false, defaultPhase = LifecyclePhase.NONE)
public class SayGoodByeMojo {
}
