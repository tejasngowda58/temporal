package org.example.nexus;

import io.nexusrpc.handler.OperationHandler;
import io.nexusrpc.handler.OperationImpl;
import io.nexusrpc.handler.ServiceImpl;
import io.temporal.client.WorkflowOptions;
import io.temporal.nexus.Nexus;
import io.temporal.nexus.WorkflowRunOperation;

@ServiceImpl(service = EchoService.class)
public class EchoServiceImpl {

    @OperationImpl
    public OperationHandler<EchoService.EchoInput, EchoService.EchoOutput> echo() {
        return OperationHandler.sync(
                (ctx, details, input) -> new EchoService.EchoOutput(input.getMessage() + ". Thank You!!")
        );
    }

    @OperationImpl
    public OperationHandler<EchoService.HelloInput, EchoService.HelloOutput> hello() {
        // Use the WorkflowRunOperation.fromWorkflowMethod constructor, which is the easiest
        // way to expose a workflow as an operation. To expose a workflow with a different input
        // parameters then the operation or from an untyped stub, use the
        // WorkflowRunOperation.fromWorkflowHandler constructor and the appropriate constructor method
        // on WorkflowHandle.
        return WorkflowRunOperation.fromWorkflowMethod(
                (ctx, details, input) ->
                        Nexus.getOperationContext()
                                .getWorkflowClient()
                                .newWorkflowStub(
                                        HelloHandlerWorkflow.class,
                                        // Workflow IDs should typically be business meaningful IDs and are used to
                                        // dedupe workflow starts.
                                        // For this example, we're using the request ID allocated by Temporal when
                                        // the
                                        // caller workflow schedules
                                        // the operation, this ID is guaranteed to be stable across retries of this
                                        // operation.
                                        //
                                        // Task queue defaults to the task queue this operation is handled on.
                                        WorkflowOptions.newBuilder().setWorkflowId(details.getRequestId()).build())
                                ::hello);
    }
}
