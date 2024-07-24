package src.eist;

public class EventFacade {

    EventClient eventClient;
    TokenGeneratorClient tokenGeneratorClient;

    public EventFacade() {
        eventClient = new EventClient();
        tokenGeneratorClient = new TokenGeneratorClient();
    }

    public void registerEvent(Employee employee) {
        try {
            if (employee.getRole() == Role.MANAGER) {
                String token = tokenGeneratorClient.generateToken(employee);
                employee.setToken(token);
                try {
                    if (eventClient.registerEvent(employee).equals("Registration is successful")) {
                        employee.setRegistered(true);
                    } else {
                        System.out.println("Registration failed");
                    }
                } catch (Exception e) {
                    System.err.println("Registration failed with error: " + e.getMessage());
                }
            } else {
                System.out.println("Only managers can register the event!");
            }
        } catch (Exception e) {
            System.err.println("Token generation failed with error: " + e.getMessage());
        }
    }

    public void sendPreferences(Employee employee, Event event) {
        if (employee.isRegistered()) {
            employee.setEventPreferences(event);
            eventClient.recordPreferences(employee);
        } else {
            System.out.println("Employee should register to the event first!");
        }

    }

    public EventClient getEventClient() {
        return eventClient;
    }

    public void setEventClient(EventClient eventClient) {
        this.eventClient = eventClient;
    }

    public TokenGeneratorClient getTokenGeneratorClient() {
        return tokenGeneratorClient;
    }

    public void setTokenGeneratorClient(TokenGeneratorClient tokenGeneratorClient) {
        this.tokenGeneratorClient = tokenGeneratorClient;
    }
}
