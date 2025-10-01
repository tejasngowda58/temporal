package org.example.nexus;

public class EchoHandlerWorkflowImpl implements EchoHandlerWorkflow{
    @Override
    public EchoService.EchoOutput echo(EchoService.EchoInput input) {
        String inputData = "Hello Mr." + input +". Welcome to Temporal Nexus";
        return new EchoService.EchoOutput(inputData);
    }
}
