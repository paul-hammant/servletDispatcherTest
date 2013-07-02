## Building the example war files.

mvn clean install

## Scripts 

Gets tomcat7 from Apache & unzips.

    ./get_tomcat_7.sh

Patches the conf/context.xml file to add crossContext="true" (false is the default):

    ./patch_contextXml.sh

Copy 'a' and 'b' war files from the maven build above into the apache-tomcat-7.0.41/webapps folder:

    ./copy_war_files.sh

Then to run Tomcat:

    cd apache-tomcat-7.0.41/bin
	./catalina.sh run

## Single Context tests

Filter invocation [http://localhost:8080/a/AFilterThatOutputsTheRequestsThreadID](http://localhost:8080/a/AFilterThatOutputsTheRequestsThreadID):

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 320938030

Filter invocation [http://localhost:8080/b/AnotherFilterThatOutputsTheRequestsThreadID](http://localhost:8080/b/AnotherFilterThatOutputsTheRequestsThreadID):

Outputs something like:

    /b/AnotherFilterThatOutputsTheRequestsThreadID: thread = 2065966604

Servlet invocation [http://localhost:8080/b/AServletThatOutputsTheRequestsThreadID](http://localhost:8080/b/AServletThatOutputsTheRequestsThreadID):

Outputs something like:

    /b/AServletThatOutputsTheRequestsThreadID: thread = 2065966604

## Cross context tests

Including content from a static resource works fine [http://localhost:8080/a/AIncludingContentFromB?bURL=foo.txt](http://localhost:8080/a/AIncludingContentFromB?bURL=foo.txt) :

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1856114434
    /b/foo.txt: contents of static file

Including content from a servlet works fine [http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID](http://localhost:8080/a/AIncludingContentFromB?bURL=AServletThatOutputsTheRequestsThreadID):

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1062493000
    /b/AServletThatOutputsTheRequestsThreadID: thread = 1062493000

**Note you have to set context.xml to have a line like so (the patch_contextXml.sh script does this for you)**

Filter invocation in 'a' including content from a filter in 'b' [http://localhost:8080/a/AIncludingContentFromB?bURL=AnotherFilterThatOutputsTheRequestsThreadID](http://localhost:8080/a/AIncludingContentFromB?bURL=AnotherFilterThatOutputsTheRequestsThreadID)

Outputs something like:

    /a/AFilterThatOutputsTheRequestsThreadID: thread = 1062493000
    /b/AnotherFilterThatOutputsTheRequestsThreadID: thread = 1062493000
