import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import model.SimpleImage;
import org.junit.Test;
import view.SimpleTextView;
import view.TextView;

/**
 * Tests to be run on the view to ensure messages are rendered properly.
 */
public class TextViewTests {

  private final StringWriter output = new StringWriter();
  private TextView view = new SimpleTextView(output, new SimpleImage(100, 100));

  @Test
  public void viewRenderMessageTest() throws IOException {
    view.renderMessage("test");
    assertEquals("test", output.toString());
  }
}
