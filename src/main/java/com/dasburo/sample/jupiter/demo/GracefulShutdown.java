package com.dasburo.sample.jupiter.demo;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Implementation for the shutdown of the application context.
 */

public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final int TIMEOUT = 30;

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdown.class);

    private Connector connector;

    @Override
    public void customize(Connector connector) {
        LOG.info("register");
        this.connector = connector;
    }

    /**
     * gracefully shutting down Spring Boot application to avoid following issues: a "downtime
     * period", your server returning “503 Service Unavailable” responses a "failed request", if the
     * request was in progress at the time of restart This method tries to shutdown thread pool and
     * log error after trying 2 times
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (this.connector == null) {
            LOG.error("Graceful shutdown not possible, to do missing Connector");
            return;
        }

        LOG.info("Graceful shutdown started");
        this.connector.pause();
        final Executor executor = this.connector.getProtocolHandler().getExecutor();

        if (executor instanceof ThreadPoolExecutor) {
            try {
                final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;

                LOG.info("Graceful shutdown initiated...");
                threadPoolExecutor.shutdown();
                final long start = System.currentTimeMillis();
                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    LOG.warn(
                            "...Tomcat thread pool did not shut down gracefully within 30 seconds. Proceeding with forceful shutdown");

                    threadPoolExecutor.shutdownNow();

                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        LOG.error("...Tomcat thread pool did not terminate");
                    }
                } else {
                    LOG.info("...Graceful shutdown succeeded after {}ms", System.currentTimeMillis() - start);
                }
            } catch (InterruptedException ex) {
                LOG.warn("...Graceful shutdown was interrupted");
                Thread.currentThread().interrupt();
            }
        } else {
            LOG.error("Graceful shutdown is a nop: executor is not an instance of ThreadPoolExecutor");
        }
    }
}
