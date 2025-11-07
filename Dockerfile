FROM tomcat:10.1-jdk21

# Remove the default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your WAR file into Tomcat and rename to ROOT.war
COPY WeatherForecastingProject.war /usr/local/tomcat/webapps/ROOT.war

# Render required PORT
EXPOSE 10000

# Replace port in Tomcat config (8080 → 10000)
RUN sed -i 's/8080/10000/g' /usr/local/tomcat/conf/server.xml

CMD ["catalina.sh", "run"]
