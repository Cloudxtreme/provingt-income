#!/usr/bin/env bash
set -e

echo "build number:: " $BUILD_NUMBER

VERSION_NUMBER=1.0
ARTIFACT="uk.gov.digital.ho.proving.income.api-${VERSION_NUMBER}.jar"

if [ -n "$BUILD_NUMBER" ]; then
	ARTIFACT="uk.gov.digital.ho.proving.income.api-${VERSION_NUMBER}.${BUILD_NUMBER}.jar"
fi

echo "Artifact being built: " ${ARTIFACT}

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
	-e "VERSION_NUMBER=$VERSION_NUMBER" \
	-e "BUILD_NUMBER=$BUILD_NUMBER" \
	build.uk.gov.digital.ho.proving.income.service

# 2.1 Setup files for docker context
mkdir build || true
mkdir build/docker || true
docker cp build.uk.gov.digital.ho.proving.income.service:/code/build/build/libs/$ARTIFACT build/docker/
cp src/main/resources/run.sh build/docker/
cp -R src/main/resources/json build/docker/
cp src/main/docker/Dockerfile build/docker/

# 2.2 Tidy up
docker rm $(docker ps -aq)
docker rmi $(docker images | grep build.uk.gov.digital.ho.proving.income.service | awk '{print $3'})

# 3. Build docker image to run jar from artifacts
echo "build docker image for app::"
cd build/docker
TAG="quay.io/ukhomeofficedigital/uk.gov.digital.ho.proving.income.api:${VERSION_NUMBER}"
docker build -t ${TAG} .

# 4. Push image to quay.io
docker push ${TAG}

if [ "$?" = "0" ]; then
	echo "build succeeded"
	exit 0
else
	echo "build failed"
	exit 1
fi
