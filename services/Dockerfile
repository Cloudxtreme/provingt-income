FROM quay.io/ukhomeofficedigital/kb8or:v0.6.12
WORKDIR /var/lib/app_deploy
ADD ./ ./
RUN ls
RUN ls /var/lib/app_deploy/tmp/
RUN cat /var/lib/app_deploy/tmp/kubecfg
ENTRYPOINT ["./scripts/deploy"]
