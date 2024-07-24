package de.tum.in.ase.eist;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OnlineCourseTest {

    // TODO 3: Test setOnlineCourseUrl()
    @Test
    void testSetOnlineCourseUrlWithValidUrl() throws MalformedURLException {
        OnlineCourse onlineCourse = new OnlineCourse("FPV");
        URL validUrl = new URL("https://artemis.cit.tum.de/courses/276/exercises/11613");
        onlineCourse.setUrl("https://artemis.cit.tum.de/courses/276/exercises/11613");
        assert Objects.equals(validUrl, onlineCourse.getUrl());


    }

    @Test
    void testSetOnlineCourseUrlWithInvalidUrl() {
        OnlineCourse onlineCourse = new OnlineCourse("FPV");
        String invalidUrl = "hes11613";
        assertThrows(Exception.class, () -> {
            onlineCourse.setUrl(invalidUrl);
        });
    }
}
