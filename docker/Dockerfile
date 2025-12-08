FROM jenkins/jenkins:lts-jdk21

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false" \
                ALLURE_VERSION=2.35.1 \
                MAVEN_VERSION=3.9.11 \
                CASC_JENKINS_CONFIG="/var/jenkins_home/casc_configs/jenkins.yaml"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

COPY --chown=jenkins:jenkins jenkins.yml /var/jenkins_home/casc_configs/jenkins.yaml

USER root
RUN apt-get update && apt-get install -y wget unzip tar && \
    rm -rf /var/lib/apt/lists/*

RUN wget -qO /tmp/maven.tar.gz \
        https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf /tmp/maven.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin/mvn && \
    rm /tmp/maven.tar.gz

RUN wget -qO /tmp/allure.zip https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.zip && \
    unzip /tmp/allure.zip -d /opt && \
    mv /opt/allure-* /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/local/bin/allure && \
    rm /tmp/allure.zip

USER jenkins