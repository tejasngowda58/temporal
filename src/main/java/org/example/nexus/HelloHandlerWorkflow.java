package org.example.nexus;

import io.nexusrpc.Service;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloHandlerWorkflow {
    @WorkflowMethod
    EchoService.HelloOutput hello(EchoService.HelloInput input);
}
