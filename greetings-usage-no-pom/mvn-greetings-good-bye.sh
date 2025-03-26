#!/bin/sh

mvn io.github.krm-demo:greetings-maven-plugin:say-good-bye \
  -Dfarewell="See you soon!" \
  -Dlog-level-good-bye=WARN
