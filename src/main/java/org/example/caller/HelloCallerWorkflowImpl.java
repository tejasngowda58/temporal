package org.example.caller;


import io.temporal.workflow.NexusOperationHandle;
import io.temporal.workflow.NexusOperationOptions;
import io.temporal.workflow.NexusServiceOptions;
import io.temporal.workflow.Workflow;
import org.example.nexus.EchoService;

import java.time.Duration;

public class HelloCallerWorkflowImpl implements HelloCallerWorkflow {

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
    public String hello(String input, EchoService.Language language) {
        NexusOperationHandle<EchoService.HelloOutput> handle = Workflow.startNexusOperation(
                echoService::hello, new EchoService.HelloInput(input, language));

        handle.getExecution().get();
        return handle.getResult().get().getMessage();
    }
}
