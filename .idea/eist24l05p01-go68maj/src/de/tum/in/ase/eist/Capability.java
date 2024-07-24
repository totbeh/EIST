package de.tum.in.ase.eist;

import java.io.File;
import java.util.Objects;
import java.util.Set;
public class Capability {
    private final File file;
    private final Set<Permission> permissions;

    public Capability(File file, Set<Permission> permissions) {
        this.file = file;
        this.permissions = permissions;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capability capability = (Capability) o;
        return Objects.equals(file, capability.file) && Objects.equals(permissions, capability.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, permissions);
    }

    public File getFile() {
        return file;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
