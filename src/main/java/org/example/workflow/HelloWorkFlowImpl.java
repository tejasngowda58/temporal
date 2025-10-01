package org.example.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.example.activity.HelloWorldActivities;

import java.time.Duration;

public class HelloWorkFlowImpl implements HelloWorkFlow{

    private final HelloWorldActivities activities =
            Workflow.newActivityStub(HelloWorldActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(5))
                            .build());

    @Override
    public String hello() {
        return activities.composeGreeting("Hello");
    }
}
