package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import nz.ben.flitter.ui.render.ResponseRenderer;
import nz.ben.flitter.user.User;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by bengilbert on 2/05/15.
 */
public class ResponseRendererTest {

    private ResponseRenderer responseRenderer = new ResponseRenderer();

    @Test
    public void testRender_oneSecondAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusSeconds(1));
        assertThat(responseRenderer.render(message), is("message (1 second ago)"));
    }

    @Test
    public void testRender_twoSecondAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusSeconds(2));
        assertThat(responseRenderer.render(message), is("message (2 seconds ago)"));
    }

    @Test
    public void testRender_oneMinuteAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusMinutes(1));
        assertThat(responseRenderer.render(message), is("message (1 minute ago)"));
    }

    @Test
    public void testRender_oneHourAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusHours(1));
        assertThat(responseRenderer.render(message), is("message (1 hour ago)"));
    }

    @Test
    public void testRender_oneDayAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusDays(1));
        assertThat(responseRenderer.render(message), is("message (1 day ago)"));
    }

    @Test
    public void testRender_now() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime());
        assertThat(responseRenderer.render(message), is("message (just now)"));
    }

    @Test
    public void testRender_multipleMessages_displayInAscendingOrder() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(mock(User.class), "message 1", new DateTime().minusDays(200)));
        messages.add(new Message(mock(User.class), "message 4", new DateTime()));
        messages.add(new Message(mock(User.class), "message 2", new DateTime().minusDays(100)));
        messages.add(new Message(mock(User.class), "message 3", new DateTime().minusMillis(1)));

        assertThat(responseRenderer.render(messages), is("message 4 (just now)\nmessage 3 (just now)\nmessage 2 (100 days ago)\nmessage 1 (200 days ago)"));
    }

    @Test
    public void testRender_noMessages_displayEmptyString() throws Exception {
        List<Message> messages = new ArrayList<Message>();

        assertThat(responseRenderer.render(messages), isEmptyString());
    }

}