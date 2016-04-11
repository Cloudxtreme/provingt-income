FROM quay.io/ukhomeofficedigital/openjdk8:v0.1.2

VOLUME /tmp

USER root

ADD artifacts/uk.gov.digital.ho.proving.income.api*.jar /
ADD src/main/resources/json/ src/main/resources/json/

ADD src/main/resources/run.sh /
RUN /bin/bash -c 'chmod +x /run.sh'

ENTRYPOINT /run.sh
