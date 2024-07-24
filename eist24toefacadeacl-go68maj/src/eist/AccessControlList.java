package eist;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccessControlList {
    private Map<String, Set<String>> accessControlMap;


    public AccessControlList() {
        accessControlMap = new HashMap<>();
    }

    //TODO: fill accessControlMap instance with the given user and permission information.
    public void grantAccess(String user, String permission) {
        Set<String> manga = accessControlMap.getOrDefault(user,null);
        if (manga == null){
            Set<String> newSet = new HashSet<>();
            newSet.add(permission);
            accessControlMap.put(user,newSet);
            return;
        }
        manga.add(permission);

    }

    //TODO: check if accessControlMap instance has given user and permission. Return result in a boolean.
    public boolean hasAccess(String user, String permission) {
        Set<String> manga = accessControlMap.getOrDefault(user,null);
        if (manga == null){
            return false;
        }
        return manga.contains(permission);
    }


    @Override
    public String toString() {
        return accessControlMap.toString();
    }

    public Map<String, Set<String>> getAccessControlMap() {
        return accessControlMap;
    }

    public void setAccessControlMap(Map<String, Set<String>> accessControlMap) {
        this.accessControlMap = accessControlMap;
    }

    public static void main(String[] args) {
        AccessControlList accessControlList = new AccessControlList();
        accessControlList.grantAccess("tamer","read");
        accessControlList.grantAccess("tamer","write");
        accessControlList.grantAccess("mohsen","manga");
        System.out.println(accessControlList.hasAccess("tamer","manga"));
        System.out.println(accessControlList.hasAccess("tamer","read"));
    }
}