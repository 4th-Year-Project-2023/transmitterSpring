package org.example.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
public class PublisherStartup implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PublisherStartup.class);


    private PublisherClient publisherClient;

    @Autowired
    public void PublisherStartup(PublisherClient publisherClient)
    {
        this.publisherClient = publisherClient;
    }


    @Override
    public void run(String... args) throws Exception {

        try {

            Thread.currentThread().setName("demo_major_project");

            log.info("Starting the demo_major_project thread.");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(publisherClient);
            executorService.awaitTermination(1l, TimeUnit.SECONDS);
            executorService.shutdown();
            log.info("Ending the demo_major_project thread.");

        } catch (Exception e) {
            System.out.println("Could not confiure application" + e.getMessage());
            System.exit(1);
        }
    }
}
