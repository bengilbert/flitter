package nz.ben.flitter.ui;

import nz.ben.flitter.message.Message;
import nz.ben.flitter.ui.render.MessageRenderer;
import nz.ben.flitter.user.User;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by bengilbert on 2/05/15.
 */
public class MessageRendererTest {

    private MessageRenderer messageRenderer = new MessageRenderer();

    @Test
    public void testRender_oneSecondAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusSeconds(1));
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (1 second ago)"));
    }

    @Test
    public void testRender_twoSecondAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusSeconds(2));
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (2 seconds ago)"));
    }

    @Test
    public void testRender_oneMinuteAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusMinutes(1));
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (1 minute ago)"));
    }

    @Test
    public void testRender_oneHourAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusHours(1));
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (1 hour ago)"));
    }

    @Test
    public void testRender_oneDayAgo() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime().minusDays(1));
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (1 day ago)"));
    }

    @Test
    public void testRender_now() throws Exception {
        Message message = new Message(mock(User.class), "message", new DateTime());
        Collection<Message> messages = Collections.singletonList(message);

        assertThat(messageRenderer.render(messages), is("message (just now)"));
    }


}