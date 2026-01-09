package org.ormi.priv.tfa.orderflow.store;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main entry point for the Product Registry service.
 * Starts the Quarkus subsystems
 */
@QuarkusMain
public class Main {
    /**
     * Main starts app
     * 
     * @param args cli
     */
    public static void main(String... args) {
        Quarkus.run(
            ProductRegistryDomainApplication.class,
            (exitCode, exception) -> {},
            args);
    }
    /**
     * Quarkus app
     */
    public static class ProductRegistryDomainApplication implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            Quarkus.waitForExit();
            return 0;
        }
    }
}
