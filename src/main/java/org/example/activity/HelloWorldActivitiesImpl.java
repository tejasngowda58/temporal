package org.example.activity;

public class HelloWorldActivitiesImpl implements HelloWorldActivities{
    @Override
    public String composeGreeting(String name) {
        return "Hello from activity";
    }
}
