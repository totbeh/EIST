package src.eist;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // create EventFacade and start the application
        EventFacade eventFacade = new EventFacade();

        int opt = 0;
        int employeeId = 0;

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Select an option:\n1. Register Event\n2. Send Preferences\n-1. Exit");
            opt = scanner.nextInt();

            if(opt == 1) {
                System.out.println("Enter employee name:");
                String name = scanner.next();

                System.out.println("Enter employee role: (1. SENIOR, 2. MANAGER, 3. JUNIOR, 4. INTERN)");
                int roleInt = scanner.nextInt();

                Role role;

                if (roleInt == 1) {
                    role = Role.SENIOR;
                }
                else if (roleInt == 2) {
                    role = Role.MANAGER;
                }
                else if (roleInt == 3) {
                    role = Role.JUNIOR;
                }
                else if (roleInt == 4) {
                    role = Role.INTERN;
                }
                else {
                    System.out.println("Invalid role, try again!");
                    continue;
                }

                Employee employee = new Employee(name, employeeId, role);

                eventFacade.registerEvent(employee);

                employeeId++;
            }
            else if (opt == 2) {
                System.out.println("Enter employee name:");
                String name = scanner.next();
                
                System.out.println("Enter employee role: (1. SENIOR, 2. MANAGER, 3. JUNIOR, 4. INTERN)");
                int roleInt = scanner.nextInt();

                Role role;

                if (roleInt == 1) {
                    role = Role.SENIOR;
                }
                else if (roleInt == 2) {
                    role = Role.MANAGER;
                }
                else if (roleInt == 3) {
                    role = Role.JUNIOR;
                }
                else if (roleInt == 4) {
                    role = Role.INTERN;
                }
                else {
                    System.out.println("Invalid role, try again!");
                    continue;
                }

                Employee employee = new Employee(name, employeeId, role);

                System.out.println("Enter event name:");
                String eventName = scanner.next();

                System.out.println("Enter event date:");
                String eventDate = scanner.next();

                Event event = new Event();

                eventFacade.sendPreferences(employee, event);

                employeeId++;
            }
            else if (opt == -1) {
                System.out.println("Exiting...");
                break;
            }
            else {
                System.out.println("Invalid option, try again!");
                continue;
            }
        } while (opt != -1);
    }
}