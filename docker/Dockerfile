FROM tomcat:8.5-1
RUN echo "Asia/shanghai" > /etc/timezone;
RUN rm -rf webapps
COPY ROOT.war  webapps/
RUN unzip -q webapps/ROOT.war -d webapps/ROOT
COPY build/catalina.properties conf/
COPY entrypoint.sh .
ENTRYPOINT ["sh","./entrypoint.sh"]