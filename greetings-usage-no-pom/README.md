# Usage of `greetings-maven-pligin` without maven project

If maven-plugin is installed locally in local maven repository
it's always possible to execute it from any folder at your file-system,
but you have to specify full maven-coordinates of that plugin.
As for configuration parameters they could be provided as a Java system-properties,
whose exact names could be taken via either `maven-help-plugin`
and `helpmojo` of the maven-pugin we are trying to use:


