FROM eclipse-temurin:17-jre-alpine

ENV APP_BASE="/home" \
    APP_NAME="ai-guess-game" \
    SERVER_PORT="80"

EXPOSE ${SERVER_PORT}

RUN apk update && apk upgrade && apk add curl openssl gcompat bash busybox-extras iputils

RUN mkdir -p ${APP_BASE}/${APP_NAME}

COPY ${APP_NAME}/build/libs/${APP_NAME}*.jar ${APP_BASE}/${APP_NAME}.jar

CMD java $JAVA_OPTS \
    -jar ${APP_BASE}/${APP_NAME}.jar
