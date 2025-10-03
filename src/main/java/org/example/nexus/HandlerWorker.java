package org.example.nexus;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class HandlerWorker {
    public static final String HANDLER_TASK_QUEUE_NAME = "test-nexus-queue";

    public void start() {
        String apikey = "";
        // Initialize client connection
        WorkflowServiceStubs service =
                WorkflowServiceStubs.newServiceStubs(
                        WorkflowServiceStubsOptions.newBuilder()
                                .addApiKey(
                                        () ->
                                                apikey)
                                .setTarget("asia-south1.gcp.api.temporal.io:7233")
                                .setEnableHttps(true)
                                .build());

        WorkflowClient client =
                WorkflowClient.newInstance(
                        service, WorkflowClientOptions.newBuilder().setNamespace("test-ns-001.crk0i").build());


        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(HANDLER_TASK_QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(HelloHandlerWorkflowImpl.class);
        worker.registerNexusServiceImplementation(new EchoServiceImpl());

        factory.start();
    }
}
