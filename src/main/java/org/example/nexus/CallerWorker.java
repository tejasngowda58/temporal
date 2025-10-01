package org.example.nexus;


import io.temporal.client.WorkflowClient;

import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;
import io.temporal.workflow.NexusServiceOptions;
import org.example.caller.HelloCallerWorkflowImpl;

import java.util.Collections;

public class CallerWorker {
    public static final String DEFAULT_TASK_QUEUE_NAME = "my-caller-workflow-task-queue";

    public void start() {
        String apikey = "eyJhbGciOiJFUzI1NiIsImtpZCI6Ild2dHdhQSJ9.eyJhY2NvdW50X2lkIjoiY3JrMGkiLCJhdWQiOlsidGVtcG9yYWwuaW8iXSwiZXhwIjoxODIyMjA5ODE5LCJpc3MiOiJ0ZW1wb3JhbC5pbyIsImp0aSI6InBKdHlzeEFDY0FOREQzTzF5aDNkaWNnQkVCeHVScjJHIiwia2V5X2lkIjoicEp0eXN4QUNjQU5ERDNPMXloM2RpY2dCRUJ4dVJyMkciLCJzdWIiOiJjZjRhMTUzMDA3MTg0MDljOWRiOTZkZDZhMDFmNWVlNSJ9.KdCx1Note3GqEfBAv5QYA19V97Y1B0hNG-aNH-g7DGamfC4CmdB4OMc5rikITlvr0Fh557CHMjNBfJM0VdcNzw";

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
                        service, WorkflowClientOptions.newBuilder().setNamespace("test-target-001.crk0i").build());



        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(DEFAULT_TASK_QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(
                WorkflowImplementationOptions.newBuilder()
                        .setNexusServiceOptions(
                                Collections.singletonMap(
                                        "NexusService",
                                        NexusServiceOptions.newBuilder().setEndpoint("test-nexus-ep").build()))
                        .build(),
                EchoCallerWorkflowImpl.class,
                HelloCallerWorkflowImpl.class);

        factory.start();
    }
}
