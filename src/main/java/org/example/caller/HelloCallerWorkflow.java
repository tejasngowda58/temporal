package org.example.caller;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.example.nexus.EchoService;

@WorkflowInterface
public interface HelloCallerWorkflow {
    @WorkflowMethod
    String hello(String input, EchoService.Language language);
}
