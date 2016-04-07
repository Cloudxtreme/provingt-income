#FROM frolvlad/alpine-oraclejdk8:slim
#FROM quay.io/ukhomeofficedigital/kb8or:v0.6.11-rc3
FROM quay.io/ukhomeofficedigital/openjdk8:v0.1.2

VOLUME /tmp

USER root

ADD uk.gov.digital.ho.proving.income.api-3.0-SNAPSHOT.jar /
ADD src/main/resources/json/ src/main/resources/json/

ENTRYPOINT [ \
	"java", \
	"-Dcom.sun.management.jmxremote.local.only=false", \
	"-Djava.security.egd=file:/dev/./urandom","-jar", \
	"/uk.gov.digital.ho.proving.income.api-3.0-SNAPSHOT.jar"]
