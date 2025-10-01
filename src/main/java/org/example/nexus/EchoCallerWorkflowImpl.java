package org.example.nexus;

// @@@SNIPSTART samples-java-nexus-caller-echo-workflow

import io.temporal.workflow.NexusOperationOptions;
import io.temporal.workflow.NexusServiceOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class EchoCallerWorkflowImpl implements EchoCallerWorkflow {
    EchoService echoService =
            Workflow.newNexusServiceStub(
                    EchoService.class,
                    NexusServiceOptions.newBuilder()
                            .setOperationOptions(
                                    NexusOperationOptions.newBuilder()
                                            .setScheduleToCloseTimeout(Duration.ofSeconds(10))
                                            .build())
                            .setEndpoint("test-nexus-ep")
                            .build());

    @Override
    public String echo(String message) {
        return echoService.echo(new EchoService.EchoInput(message)).getMessage();
    }
}
// @@@SNIPEND