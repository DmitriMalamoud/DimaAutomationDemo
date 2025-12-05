package org.demo.testinfra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "llm")
@Component
public class LlmConfig {

    private ApiConfig api;
    private PromptConfig prompt;

    public ApiConfig getApi() {
        return api;
    }

    public void setApi(ApiConfig api) {
        this.api = api;
    }

    public PromptConfig getPrompt() {
        return prompt;
    }

    public void setPrompt(PromptConfig prompt) {
        this.prompt = prompt;
    }

    public static LlmConfig getConfig(){
        ApplicationContext context = ApplicationContextProvider.getContext();
        return context.getBean(LlmConfig.class);
    }

    public static class ApiConfig {
        private String scheme;
        private String host;
        private int port;
        private String model;
        private String endpoint;
        private int timeoutSec;
        private int numPredict;

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public int getTimeoutSec() {
            return timeoutSec;
        }

        public void setTimeoutSec(int timeoutSec) {
            this.timeoutSec = timeoutSec;
        }

        public int getNumPredict() {
            return numPredict;
        }

        public void setNumPredict(int numPredict) {
            this.numPredict = numPredict;
        }
    }

    public static class PromptConfig {
        private String defaultPath;

        public String getDefaultPath() {
            return defaultPath;
        }

        public void setDefaultPath(String defaultPath) {
            this.defaultPath = defaultPath;
        }
    }
}
