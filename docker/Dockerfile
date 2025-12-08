FROM jenkins/jenkins:lts-jdk21

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false" \
                ALLURE_VERSION=2.35.1 \
                CASC_JENKINS_CONFIG="/var/jenkins_home/casc_configs/jenkins.yaml"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

COPY --chown=jenkins:jenkins jenkins.yml /var/jenkins_home/casc_configs/jenkins.yaml

USER root
RUN apt-get update && apt-get install -y wget && \
    wget -qO /tmp/allure.zip https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.zip && \
    unzip /tmp/allure.zip -d /opt && \
    mv /opt/allure-* /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/local/bin/allure && \
    rm /tmp/allure.zip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*
USER jenkins