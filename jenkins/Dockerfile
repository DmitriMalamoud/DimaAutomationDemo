FROM jenkins/jenkins:lts-jdk21

USER root

RUN apt-get update \
 && apt-get install -y maven \
 && rm -rf /var/lib/apt/lists/*

ENV ALLURE_VERSION=2.35.1
RUN mkdir -p /opt && \
    curl -Lo /opt/allure-${ALLURE_VERSION}.zip \
        https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.zip && \
    unzip /opt/allure-${ALLURE_VERSION}.zip -d /opt && \
    mv /opt/allure-commandline-${ALLURE_VERSION} /opt/allure-${ALLURE_VERSION} && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/local/bin/allure && \
    rm /opt/allure-${ALLURE_VERSION}.zip

RUN chown -R jenkins:jenkins /opt/allure-${ALLURE_VERSION}

USER jenkins

USER jenkins

COPY disable-csp.groovy /usr/share/jenkins/ref/init.groovy.d/disable-csp.groovy

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt