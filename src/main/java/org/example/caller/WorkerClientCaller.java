package org.example.caller;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.example.HelloWorldWorker;
import org.example.workflow.HelloWorkFlow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerClientCaller {

    @GetMapping("/hello")
    public String helloWorldWF(){
        String apikey = "";
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

        HelloWorkFlow helloWorkFlow= client.newWorkflowStub(
                HelloWorkFlow.class,
                WorkflowOptions.newBuilder().setTaskQueue("HelloWorldTaskQueue").build()
        );
        String hello = helloWorkFlow.hello();
        System.out.println(hello);
        return "Called hello workFlow";
    }
}
