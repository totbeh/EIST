package de.tum.in.ase.eist;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CapabilityManager {
    private final HashMap<Application,Set<Capability>> capabilityMap;

    /*
    TODO 2.1
        Add a new variable capabilityMap as depicted in the UML diagram.
        Don't forget to initialize the variable in the constructor.
     */

    public CapabilityManager(HashMap<Application, Set<Capability>> capabilityMap) {
        this.capabilityMap = capabilityMap;
    }

    /*
    TODO 2.3.1
        Adjust the implementation of the method grantCapability(Application, Capability),
        that it adds a capability of an application, to the capabilityMap, if it does not exist yet.
     */
    public void grantCapability(Application application, Capability capability) {
        if (capability == null || application == null) {
            return;
        }
        Set<Capability> capabilitySet = capabilityMap.getOrDefault(application, new HashSet<>());
        capabilitySet.add(capability);
        capabilityMap.put(application, capabilitySet);

    }

    /*
    TODO 2.3.2
        Adjust the implementation of the method revokeCapability(Application, Capability),
        that it removes a capability of an application of the capabilityMap, if it exists.
     */
    public void revokeCapability(Application application, Capability capability) {
        if (!capabilityMap.getOrDefault(application,new HashSet<>()).contains(capability)){
            return;
        }
        Set<Capability> capabilitySet = capabilityMap.get(application);
        capabilitySet.remove(capability);
        capabilityMap.put(application,capabilitySet);
    }

    /*
    TODO 2.2
        Adjust the method hasCapability(Application, File, Permission), which is
        supposed to check, if a requested capability exits in the capabilityMap.
        A capability is the permission of an application for a certain file.
        Hint: Check the Capability class for useful methods.
     */
    public boolean hasCapability(Application application, File file, Permission permission) {
        Set<Capability> capabilities = capabilityMap.get(application);
        if (capabilities == null){
            return false;
        }
        Set<Permission> permissions = new HashSet<>();
        permissions.add(permission);
        Capability capability = new Capability(file,permissions);
        for (Capability capability1:capabilities) {
            if (capability1.equals(capability)){
                return true;
            }
        }
        return false;
    }

    /*
    TODO 2.3.3
        Adjust the implementation of the method delegateCapability(Application,
        Application, Capability), that it revokes a certain capability from the
        "old" application and grants it for the "new" application.
     */
    public void  delegateCapability(Application oldApplication, Application newApplication, Capability capability) {
        revokeCapability(oldApplication,capability);
        grantCapability(newApplication,capability);
    }
}
