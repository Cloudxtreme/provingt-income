#FROM frolvlad/alpine-oraclejdk8:slim
#FROM quay.io/ukhomeofficedigital/kb8or:v0.6.11-rc3
FROM quay.io/ukhomeofficedigital/openjdk8:v0.1.2

VOLUME /tmp

USER root

ADD uk.gov.digital.ho.proving.income.api-0.1.0.jar /

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/uk.gov.digital.ho.proving.income.api-0.1.0.jar"]
#ENTRYPOINT ["java", "-version"]
