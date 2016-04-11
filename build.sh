#!/usr/bin/env bash
set -e

echo "build number:: " $BUILD_NUMBER

# -----
# Build
# -----

# 0. Initial conditions
docker stop $(docker ps -aq) || true
docker rm $(docker ps -aq) || true
docker rmi -f $(docker images | grep build.uk.gov.digital.ho.proving.income.service | awk '{print $3'}) || true

# 1. Create Docker image to execute build in
echo "building docker image to execute app build"
docker build -f src/main/docker/Dockerfile.build -t build.uk.gov.digital.ho.proving.income.service .

# 2. Run docker image, build will copy jar file into the mounted volume /artifacts
echo "running docker image as named container"
docker run --name build.uk.gov.digital.ho.proving.income.service\
	-e "BUILD_NUMBER=$BUILD_NUMBER" \
	-v ${PWD}/artifacts:/artifacts \
	build.uk.gov.digital.ho.proving.income.service << COMMANDS

	echo "doing stuff!"
	pwd
	ls /artifacts

COMMANDS

# 2.1 Tidy up
docker rm $(docker ps -aq)
docker rmi $(docker images | grep build.uk.gov.digital.ho.proving.income.service | awk '{print $3'})

# 3. Build docker image to run jar from artifacts
echo "build docker image for app::"
pwd
ls
VERSION=`cat artifacts/version`
docker build -f src/main/docker/Dockerfile -t quay.io/ukhomeofficedigital/uk.gov.digital.ho.proving.income.api:$VERSION .

# 4. Push image to quay.io
docker push quay.io/ukhomeofficedigital/uk.gov.digital.ho.proving.income.api:$VERSION

if [ "$?" = "0" ]; then
	echo "build succeeded"
	exit 0
else
	echo "build failed"
	exit 1
fi
