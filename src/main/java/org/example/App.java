package org.example;

import org.example.nexus.CallerStarter;
import org.example.nexus.CallerWorker;
import org.example.nexus.HandlerWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
//        HelloWorldWorker helloWorldWorker = new HelloWorldWorker();
//        helloWorldWorker.startWorker();

        HandlerWorker handlerWorker = new HandlerWorker();
        CallerWorker callerWorker = new CallerWorker();

        handlerWorker.start();
        callerWorker.start();

    }
}
