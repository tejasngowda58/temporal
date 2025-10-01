package org.example.nexus;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface EchoHandlerWorkflow {

    @WorkflowMethod
    EchoService.EchoOutput echo(EchoService.EchoInput input);
}
