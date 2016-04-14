#!/usr/bin/env bash
set -e
[ -n "$DEBUG" ] && set -x

setup() {
	echo "build number:: " ${BUILD_NUMBER}

	VERSION_NUMBER=1.0
	ARTIFACT="uk.gov.digital.ho.proving.income.api-${VERSION_NUMBER}.jar"

	if [ -n "$BUILD_NUMBER" ]; then
		ARTIFACT="uk.gov.digital.ho.proving.income.api-${VERSION_NUMBER}.${BUILD_NUMBER}.jar"
	fi

	echo "Artifact being built: " ${ARTIFACT}
}

cleanup() {
	CONTAINERS=$(docker ps -aq)

	if [ ! -z "$CONTAINERS" -a "$CONTAINERS" != " " ]; then
		docker stop $CONTAINERS
		docker rm $CONTAINERS
	fi

	IMAGES=$(docker images | grep build.uk.gov.digital.ho.proving.income.service | awk '{print $3'})

	if [ ! -z "$IMAGES" -a "$IMAGES" != " " ]; then
		docker rmi -f $IMAGES
	fi
}

createDockerImageToExecuteBuildIn() {
	echo "building docker image to execute app build"
	docker build -f src/main/docker/Dockerfile.build -t build.uk.gov.digital.ho.proving.income.service .
}

executeBuild() {
	echo "running docker image as named container"
	docker run --name build.uk.gov.digital.ho.proving.income.service\
		-e "VERSION_NUMBER=${VERSION_NUMBER}" \
		-e "BUILD_NUMBER=${BUILD_NUMBER}" \
		build.uk.gov.digital.ho.proving.income.service
}

collectAssetsToCreateDockerAppExecutionImage () {
	mkdir build || true
	mkdir build/docker || true
	docker cp build.uk.gov.digital.ho.proving.income.service:/code/build/build/libs/$ARTIFACT build/docker/
	cp src/main/resources/run.sh build/docker/
	cp -R src/main/resources/json build/docker/
	cp src/main/docker/Dockerfile build/docker/
}

buildDockerAppExecutionImage() {
	echo "build docker image for app::"
	cd build/docker
	TAG="quay.io/ukhomeofficedigital/uk.gov.digital.ho.proving.income.api:${VERSION_NUMBER}.${BUILD_NUMBER}"
	echo "building " ${TAG}
	docker build -t ${TAG} .
}

pushImageToRepo() {
	docker push ${TAG}
}

# -----
# Build
# -----

setup
cleanup
createDockerImageToExecuteBuildIn
executeBuild
collectAssetsToCreateDockerAppExecutionImage
cleanup
buildDockerAppExecutionImage
pushImageToRepo
cleanup
