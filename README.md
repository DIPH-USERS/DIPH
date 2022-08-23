<h2>DIPH - Data Informed Platform for Health</h2>
DIPH web application is design to support online as well as offline data capturing.

<h4>Prerequisite for Java(back-end) online and offline applications</h4>
<ul>
<li>JDK 1.8
<li>Maven 3.6.3
</ul>

<h4>Prerequisite for Angular(front-end) online and offline applications</h4>
<ul>
<li>Node 16.13.1
<li>Angular 8
<li>MySQL 8.0
</ul>

<h4>Server</h4>
Apache Tomcat 8.5

<h4>User Manual</h4>
User manual for DIPH web application can be read or downloaded from same repository. This manual describes the whole application like application working, login with specific location, users of application, application forms and structure etc.

<h3>Online DIPH Application</h3>
<ul>
<li>Execute maven command "mvn clean package" inside "DIPHONLINE" directory using cmd or terminal, it will download all the build dependencies from maven repository.
<li>After "Build Successful" message display, locate WAR file inside target directory of "DIPHONLINE".
<li>Rename the WAR file as diphonline.war and place it inside webapps directory of Tomcat server.
<li>Execute the node command "ng build --prod --base-href=/diphonline.org/" inside "app-diph" directory.
<li>After build successfully locate the bild file inside the dist directory of "app-diph".
<li>Rename the build file as "diphonline.org" and place it inside webapps directory of Tomcat server.
<li>Make sure the database is in place with correct name, username and password as provided in the application.properties file inside resources directory of "DIPHONLINE".
<li>Start the Tomcat server using startup.sh or startup.bat inside bin directory of Tomcat.
<li>After installation, this application can be used online and data in the forms of offline DIPH application can be uploaded/synced and made available online via the JSON data files.
<li>Data in the forms of this application can also be donloaded on single click of button available on application's dashboard.
</ul>

<h3>Offline DIPH Application</h3>
<ul>
<li>Execute maven command "mvn clean package" inside "DIPHOFFLINE_V2.0" directory using cmd or terminal, it will download all the build dependencies from maven repository.
<li>After "Build Successful" message display, locate WAR file inside target directory of "DIPHOFFLINE_V2.0".
<li>Rename the WAR file as diphoffline.war and place it inside webapps directory of Tomcat server.
<li>Execute the node command "ng build --prod --base-href=/diphoffline.org/" inside "app-diph-offline-v2.0" directory.
<li>After build successfully locate the bild file inside the dist directory of "app-diph-offline-v2.0".
<li>Rename the build file as "diphoffline.org" and place it inside webapps directory of Tomcat server.
<li>Make sure the database is in place with correct name, username and password as provided in the application.properties file inside resources directory of "DIPHOFFLINE_V2.0".
<li>Start the Tomcat server using startup.sh or startup.bat inside bin directory of Tomcat.
<li>After installation this application can be used offline in the areas where internet is not available and form's data an be synced with online DIPH application via JSON data files, which can be download on single click of button available on application's dashboard.
<li>Data in the forms of this application can also be made available on uploading the JSON data files.
</ul>


