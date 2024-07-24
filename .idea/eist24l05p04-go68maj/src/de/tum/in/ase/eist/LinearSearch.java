package de.tum.in.ase.eist;

import java.util.List;
import java.util.Objects;

public class LinearSearch implements SearchStrategy {
    public int performSearch(List<Chapter> chapters, String string) {
        return linearSearch(chapters, string);
    }

    private int linearSearch(List<Chapter> chapters, String string) {
        for (Chapter chapter : chapters) {
            if (Objects.equals(chapter.getName(), string)) {
                return chapter.getPageNumber();
            }
        }
        return -1;
    }

}
