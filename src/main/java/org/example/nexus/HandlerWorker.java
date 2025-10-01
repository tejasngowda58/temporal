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
        String apikey = "eyJhbGciOiJFUzI1NiIsImtpZCI6Ild2dHdhQSJ9.eyJhY2NvdW50X2lkIjoiY3JrMGkiLCJhdWQiOlsidGVtcG9yYWwuaW8iXSwiZXhwIjoxODIxOTc5NjMwLCJpc3MiOiJ0ZW1wb3JhbC5pbyIsImp0aSI6ImRDUHlLeEFSc3kyZ1FNZjJubG9ocWlpVGpVWGVKN0toIiwia2V5X2lkIjoiZENQeUt4QVJzeTJnUU1mMm5sb2hxaWlUalVYZUo3S2giLCJzdWIiOiJjZjRhMTUzMDA3MTg0MDljOWRiOTZkZDZhMDFmNWVlNSJ9.q6ofwU9Yp9VWMli3HhA8xm1k0FTGZZ60yPm5HyRoNUbPbolwDFkF4uJCtonrKYSy20FFWSVodQ322057xz_lZw";
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
