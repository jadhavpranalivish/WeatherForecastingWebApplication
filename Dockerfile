FROM tomcat:10.1-jdk21

# Remove default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your WAR file and rename to ROOT.war
COPY WeatherForecastingProject.war /usr/local/tomcat/webapps/ROOT.war

# Make Tomcat use Render's dynamic PORT env variable
ENV PORT=8080
CMD sed -i "s/8080/${PORT}/" /usr/local/tomcat/conf/server.xml && catalina.sh run
