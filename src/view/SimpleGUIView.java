package view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Image;

public class SimpleGUIView extends JFrame implements GUIView {

  private Image image;

  private JPanel mainPanel;
  private JPanel imagePanel;
  private JPanel operationsPanel;
  private JPanel colorChooserPanel;
  private JPanel filePanel;
  private JPanel layersPanel;
  private JPanel singleLayerPanel;

  private JLabel imageDisplay;
  private JLabel colorChooserDisplay;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private JButton[] operations;

  public SimpleGUIView(Image image) {
    this.image = image;
  }

  @Override
  public void setImage(Image image) {

  }

  @Override
  public void refresh() {

  }

  @Override
  public Color chooseColor() {

    return null;
  }

  @Override
  public void execOperation(int op) {

  }

  @Override
  public String openFile(boolean proj) {

    return null;
  }

  @Override
  public void saveFile(boolean proj) {

  }
}
