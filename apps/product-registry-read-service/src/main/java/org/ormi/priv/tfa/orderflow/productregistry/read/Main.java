package org.ormi.priv.tfa.orderflow.productregistry.read;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Main entry point for the Product READ service.
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
            ProductRegistryReadApplication.class,
            (exitCode, exception) -> {},
            args);
    }
/**
     * Quarkus app
     */
    public static class ProductRegistryReadApplication implements QuarkusApplication {
 /**
         * Runs the app
         * 
         * @param args cli
         * @return exit code 0
         * @throws Exception idk
         */
        @Override
        public int run(String... args) throws Exception {
            Quarkus.waitForExit();
            return 0;
        }
    }
}
