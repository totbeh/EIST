package de.tum.in.ase.eist;

import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Objects;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@ExtendWith(EasyMockExtension.class)
class DiscussionTest {

    @TestSubject
    private Discussion discussion;
    @Mock
    private Course courseMock;
    @Mock
    private Comment commentMock;

    // TODO implement the tests
    @Test
    void testComment() {
        int beforeSize = discussion.getNumberOfComments();
        expect(commentMock.save()).andReturn(true);
        replay(commentMock);
        assert discussion.addComment(commentMock);
        assert discussion.getNumberOfComments() == beforeSize + 1;

    }

    @Test
    void testCommentIfSavingFails() {
        int beforeSize = discussion.getNumberOfComments();
        expect(commentMock.save()).andReturn(false);
        replay(commentMock);
        assert !discussion.addComment(commentMock);
        assert discussion.getNumberOfComments() == beforeSize;
    }

    @Test
    void testStartCourseDiscussion() {
        Person person = new Student("alo", "alo", LocalDate.MIN, "alo", "alo");
        expect(courseMock.isDiscussionAllowed(person)).andReturn(true);
        replay(courseMock);
        assert discussion.startCourseDiscussion(courseMock, person, "alo");
        assert Objects.equals(discussion.getCourse(), courseMock);
        assert Objects.equals(discussion.getTopic(), "alo");
    }

}
