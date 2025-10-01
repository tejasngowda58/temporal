package org.example.nexus;

import io.temporal.failure.ApplicationFailure;

public class HelloHandlerWorkflowImpl implements HelloHandlerWorkflow{
    @Override
    public EchoService.HelloOutput hello(EchoService.HelloInput input) {
        switch (input.getLanguage()) {
            case EN:
                return new EchoService.HelloOutput("Hello " + input.getName() + " 👋");
            case FR:
                return new EchoService.HelloOutput("Bonjour " + input.getName() + " 👋");
            case DE:
                return new EchoService.HelloOutput("Hallo " + input.getName() + " 👋");
            case ES:
                return new EchoService.HelloOutput("¡Hola! " + input.getName() + " 👋");
            case TR:
                return new EchoService.HelloOutput("Merhaba " + input.getName() + " 👋");
        }
        throw ApplicationFailure.newFailure(
                "Unsupported language: " + input.getLanguage(), "UNSUPPORTED_LANGUAGE");
    }
}
