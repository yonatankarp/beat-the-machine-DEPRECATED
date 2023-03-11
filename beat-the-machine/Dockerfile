FROM gradle:8-jdk17-alpine

ENV APP_BASE="/home" \
    APP_NAME="beat-the-machine" \
    SERVER_PORT="8080"

EXPOSE ${SERVER_PORT}

RUN apk update && apk upgrade && apk add curl openssl gcompat bash busybox-extras iputils

RUN mkdir -p source_code

COPY ./beat-the-machine ./source_code/beat-the-machine
COPY ./buildSrc ./source_code/buildSrc
COPY ./gradle ./source_code/gradle
COPY gradlew ./source_code/gradlew
COPY build.gradle.kts ./source_code/build.gradle.kts
COPY settings.gradle.kts ./source_code/settings.gradle.kts

RUN ./source_code/gradlew assemble --no-daemon

RUN mkdir -p ${APP_BASE}/${APP_NAME}

RUN cp ./source_code/${APP_NAME}/build/libs/${APP_NAME}*.jar ${APP_BASE}/${APP_NAME}.jar

CMD java $JAVA_OPTS \
    -jar ${APP_BASE}/${APP_NAME}.jar
