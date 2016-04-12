FROM quay.io/ukhomeofficedigital/openjdk8:v0.1.2

USER root

ADD uk.gov.digital.ho.proving.income.api*.jar /
ADD json/ src/main/resources/json/

ADD run.sh /
RUN /bin/bash -c 'chmod +x /run.sh'

ENTRYPOINT /run.sh
