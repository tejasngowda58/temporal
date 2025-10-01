package org.example.nexus;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.example.caller.HelloCallerWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallerStarter {
    private static final Logger logger = LoggerFactory.getLogger(CallerStarter.class);

    @GetMapping("/nexus")
    public String nexusStarter() {
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


        WorkflowOptions workflowOptions =
                WorkflowOptions.newBuilder().setTaskQueue(CallerWorker.DEFAULT_TASK_QUEUE_NAME).build();

        EchoCallerWorkflow echoWorkflow =
                client.newWorkflowStub(EchoCallerWorkflow.class, workflowOptions);
        WorkflowExecution execution = WorkflowClient.start(echoWorkflow::echo, "Nexus Echo");
        logger.info(
                "Started EchoCallerWorkflow workflowId: {} runId: {}",
                execution.getWorkflowId(),
                execution.getRunId());
        logger.info("Workflow result: {}", echoWorkflow.echo("Nexus Echo"));


        HelloCallerWorkflow helloWorkflow =
                client.newWorkflowStub(HelloCallerWorkflow.class, workflowOptions);
        WorkflowExecution execution1 = WorkflowClient.start(helloWorkflow::hello, "Nexus", EchoService.Language.FR);
        logger.info(
                "Started HelloCallerWorkflow workflowId: {} runId: {}",
                execution1.getWorkflowId(),
                execution1.getRunId());
        logger.info("Workflow result: {}", helloWorkflow.hello("Nexus", EchoService.Language.FR));

        return "Called nexus service";
    }
}
