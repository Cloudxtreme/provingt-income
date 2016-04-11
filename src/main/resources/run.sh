#!/usr/bin/env bash

java -Dcom.sun.management.jmxremote.local.only=false -Djava.security.egd=file:/dev/./urandom -jar /uk.gov.digital.ho.proving.income.api-*.jar