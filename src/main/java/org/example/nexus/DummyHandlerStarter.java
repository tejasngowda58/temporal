package org.example.nexus;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import org.example.workflow.HelloWorkFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyHandlerStarter {
    private static final Logger logger = LoggerFactory.getLogger(DummyHandlerStarter.class);

    @GetMapping("/handler")
    public String handlerWorkFlowStarter(){
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
        WorkflowOptions workflowOptions =
                WorkflowOptions.newBuilder().setTaskQueue(HandlerWorker.HANDLER_TASK_QUEUE_NAME).build();

        EchoHandlerWorkflow echoHandlerWorkflow =
                client.newWorkflowStub(EchoHandlerWorkflow.class, workflowOptions);

        WorkflowExecution execution = WorkflowClient.start(echoHandlerWorkflow::echo, new EchoService.EchoInput("Ram"));
        EchoService.EchoOutput ram = echoHandlerWorkflow.echo(new EchoService.EchoInput("Ram"));
        System.out.println(ram);
        logger.info(
                "Started EchoCallerWorkflow workflowId: {} runId: {}",
                execution.getWorkflowId(),
                execution.getRunId());
        logger.info("Workflow result: {}", echoHandlerWorkflow.echo(new EchoService.EchoInput("Nexus Echo")));

        return "Called normal handler workFlow";
    }
}
