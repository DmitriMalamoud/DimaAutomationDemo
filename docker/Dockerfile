FROM jenkins/jenkins:lts-jdk21

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false" \
                ALLURE_VERSION=2.35.1 \
                CASC_JENKINS_CONFIG="/var/jenkins_home/casc_configs/jenkins.yaml"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

COPY --chown=jenkins:jenkins jenkins.yml /var/jenkins_home/casc_configs/jenkins.yaml