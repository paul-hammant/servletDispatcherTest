## Building the example war files.

mvn clean install

## Scripts 

Gets tomcat7 from Apache & unzip:

    ./get_tomcat_7.sh

Patches the conf/context.xml file to add crossContext="true" (false is the default):

    ./patch_contextXml.sh

Copy 'a' and 'b' war files from the maven build above into the apache-tomcat-7.0.41/webapps folder:

    ./copy_war_files.sh

Then to run Tomcat:

    cd apache-tomcat-7.0.41/bin
	./catalina.sh run

## Single Context tests

1) Filter invocation [http://localhost:8080/a/AFilterThatOutputsTheRequestsThreadID](http://localhost:8080/a/AFilterThatOutputsTheRequestsThreadID):

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 320938030

2) Filter invocation [http://localhost:8080/b/AnotherFilterThatOutputsTheRequestsThreadID](http://localhost:8080/b/AnotherFilterThatOutputsTheRequestsThreadID):

Outputs something like:

    /b/AnotherFilterThatOutputsTheRequestsThreadID: thread = 2065966604

3) Servlet invocation [http://localhost:8080/b/AServletThatOutputsTheRequestsThreadID](http://localhost:8080/b/AServletThatOutputsTheRequestsThreadID):

Outputs something like:

    /b/AServletThatOutputsTheRequestsThreadID: thread = 2065966604

4) Static Resource [http://localhost:8080/b/foo.txt](http://localhost:8080/b/foo.txt):

Outputs something like:

    /b/foo.txt: contents of static file

## Cross context tests

1) Including content from a static resource works fine [http://localhost:8080/a/AIncludingContentFromB?bURL=foo.txt](http://localhost:8080/a/AIncludingContentFromB?bURL=foo.txt) :

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1856114434
    /b/foo.txt: contents of static file

2) Including content from a servlet works fine [http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID](http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID):

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1062493000
    /b/AServletThatOutputsTheRequestsThreadID: thread = 1062493000

**Note you have to set context.xml to have a line like so (the patch_contextXml.sh script does this for you)**

3) Filter invocation in 'a' including content from a filter in 'b' [http://localhost:8080/a/AIncludingContentFromB?bURL=AnotherFilterThatOutputsTheRequestsThreadID](http://localhost:8080/a/AIncludingContentFromB?bURL=AnotherFilterThatOutputsTheRequestsThreadID)

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1062493000
    /b/AnotherFilterThatOutputsTheRequestsThreadID: thread = 1062493000
	

4) Including content from a servlet (AS WELL AS EXTRACTING & WRAPPING IT) works fine [http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID&extractPayload=true](http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID&extractPayload=true)

Output looks like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1834288185
    Extracted from temp response, and rewritten: [ /b/AServletThatOutputsTheRequestsThreadID: thread = 1834288185 ]
	
## Important Source Files

1. [a/pom.xml](a/pom.xml)
2. [a/src/main/java/servletDispatcherTest/AFilterThatOutputsTheRequestsThreadID.java](a/src/main/java/servletDispatcherTest/AFilterThatOutputsTheRequestsThreadID.java) // this one bridges the two web-apps.
3. [a/src/main/java/servletDispatcherTest/AIncludingContentFromB.java](a/src/main/java/servletDispatcherTest/AIncludingContentFromB.java)
4. [a/src/main/webapp/WEB-INF/web.xml](a/src/main/webapp/WEB-INF/web.xml)
5. [b/pom.xml](b/pom.xml)
6. [b/src/main/java/servletDispatcherTest/AnotherFilterThatOutputsTheRequestsThreadID.java](b/src/main/java/servletDispatcherTest/AnotherFilterThatOutputsTheRequestsThreadID.java)
7. [b/src/main/java/servletDispatcherTest/AServletThatOutputsTheRequestsThreadID.java](b/src/main/java/servletDispatcherTest/AServletThatOutputsTheRequestsThreadID.java)
8. [b/src/main/webapp/foo.txt](b/src/main/webapp/foo.txt)
9. [b/src/main/webapp/WEB-INF/web.xml](b/src/main/webapp/WEB-INF/web.xml)
	
## Summary	

The important take away is that the servlet container uses the same thread for handing invocation in both contexts.  
You won't lower your concurrent request capacity by coding something like this, for a single Tomcat. This works in Tomcat 6 & 7 (maybe others). It also works in Jetty 9 (maybe others)
