## Building the example war files.

mvn clean install

## Scripts 

get_tomcat_7.sh  --  gets tomcat7 from Apache, unzips.

patch_contextXml.sh  --  patches the conf/context.xml file to add crossContext="true". False was the default.

copy_war_files.sh  --  copies 'a' and 'b' war files from the build above into the apache-tomcat-7.0.41/webapps folder.

cd into apache-tomcat-7.0.41/bin and do:

    ./catalina.sh run

## URLs to test with

[http://localhost:8080/a/IOutputMyThreadID](http://localhost:8080/a/IOutputMyThreadID)

Should output something like:

    A thread = 1229912610

[http://localhost:8080/b/IAlsoOutputMyThreadID](http://localhost:8080/b/IAlsoOutputMyThreadID)

Should output something like:

    B thread = 1229912610

[http://localhost:8080/a/AIncludingContentFromB](http://localhost:8080/a/AIncludingContentFromB)

Should output something like:

    A thread = 1229912610
    B thread = 1229912610

If you run it before patching (modifying) the context.xml, **as expected** it spits out:

    HTTP Status 500 -
    
    type Exception report
    
    message
    
    description The server encountered an internal error that prevented it from fulfilling this request.
    
    exception
    
    java.lang.NullPointerException
    	servletDispatcherTest.AIncludingContentFromB.doFilter(AIncludingContentFromB.java:23)
    note The full stack trace of the root cause is available in the Apache Tomcat/7.0.41 logs.

If **after** patching context.xml, it **unexpectedly** outputs:

    HTTP Status 500 - The requested resource (/b/IAlsoOutputMyThreadID) is not available
    
    type Exception report
    
    message The requested resource (/b/IAlsoOutputMyThreadID) is not available
    
    description The server encountered an internal error that prevented it from fulfilling this request.
    
    exception
    
    java.io.FileNotFoundException: The requested resource (/b/IAlsoOutputMyThreadID) is not available
    	org.apache.catalina.servlets.DefaultServlet.serveResource(DefaultServlet.java:776)
    	org.apache.catalina.servlets.DefaultServlet.doGet(DefaultServlet.java:411)
    	javax.servlet.http.HttpServlet.service(HttpServlet.java:621)
    	javax.servlet.http.HttpServlet.service(HttpServlet.java:728)
    	servletDispatcherTest.AIncludingContentFromB.doFilter(AIncludingContentFromB.java:25)
    note The full stack trace of the root cause is available in the Apache Tomcat/7.0.41 logs.