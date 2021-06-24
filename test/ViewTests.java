import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import model.SimpleImage;
import org.junit.Test;
import view.SimpleTextView;
import view.View;

/**
 * Tests to be run on the view to ensure messages are rendered properly.
 */
public class ViewTests {

  private final StringWriter output = new StringWriter();
  private View view = new SimpleTextView(output, new SimpleImage(100, 100));

  @Test
  public void viewRenderMessageTest() throws IOException {
    view.renderMessage("test");
    assertEquals("test", output.toString());
  }

  /*
  @Test
  public void viewPrintLayersTest() {

  }

  @Test
  public void viewSetImageTest() {

  }
  */
}
