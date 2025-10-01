package org.example.nexus;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface EchoCallerWorkflow {
    @WorkflowMethod
    String echo(String message);
}
