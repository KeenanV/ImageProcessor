package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Image;
import model.LayerCreator;
import utils.ImageUtil;

public class SimpleGUIView extends JFrame implements GUIView {

  private static final String[] IMAGE_OPS = new String[]
      {"Blur", "Sharpen", "Grayscale", "Sepia", "Create Checkerboard", "Create Rectangle"};
  private static final String[] FILE_OPS = new String[]
      {"New Image", "Save Image", "Save Project", "Load Image", "Load Project"};
  private static final String[] LAYER_OPS = new String[]
      {"Add Layer", "Move to Top", "Hide/Show", "Delete Layer"};
  private Image image;
  private final ImageIcon imageIcon;

  private final JLabel imageDisplay;
  private final JLabel colorChooserDisplay;

  private final JList<String> layerNames;
  private final JTextField width = new JTextField(5);
  private final JTextField height = new JTextField(5);
  private final JPanel newImagePanel;

  public SimpleGUIView(Image image, ActionListener controller,
      ListSelectionListener selectionListener) {
    super();
    setTitle("Image Processor");
    setSize(1000, 700);
    this.image = image;

    // main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel);

    // image panel in the center
    JPanel imagePanel = new JPanel();
    mainPanel.add(imagePanel, BorderLayout.CENTER);
    imageDisplay = new JLabel();
    imageIcon = new ImageIcon(writeImage());
    imageDisplay.setIcon(imageIcon);
    imagePanel.add(imageDisplay);

    // operations panel on the left
    JPanel operationsPanel = new JPanel();
    operationsPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.PAGE_AXIS));

    JButton[] imgOperations = new JButton[IMAGE_OPS.length];
    for (int i = 0; i < IMAGE_OPS.length; i += 1) {
      imgOperations[i] = new JButton(IMAGE_OPS[i]);
      imgOperations[i].setActionCommand(IMAGE_OPS[i]);
      imgOperations[i].addActionListener(controller);
      operationsPanel.add(imgOperations[i]);
    }
    mainPanel.add(operationsPanel, BorderLayout.LINE_START);

    // color chooser on the bottom
    JPanel colorChooserPanel = new JPanel();
    colorChooserPanel.setLayout(new FlowLayout());
    JButton colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color Chooser");
    colorChooserButton.addActionListener(controller);
    colorChooserPanel.add(colorChooserButton);
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true);
    colorChooserDisplay.setBackground(Color.WHITE);
    colorChooserPanel.add(colorChooserDisplay);
    operationsPanel.add(colorChooserPanel);

    // new image dialog panel
    newImagePanel = new JPanel();
    newImagePanel.setLayout(new GridLayout(3, 2, 5, 5));
    JLabel widthLabel = new JLabel("width");
    JLabel heightLabel = new JLabel("height");
    newImagePanel.add(widthLabel);
    newImagePanel.add(width);
    newImagePanel.add(heightLabel);
    newImagePanel.add(height);

    // file panel on the top
    JPanel filePanel = new JPanel();
    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));

    JButton[] fileOperations = new JButton[FILE_OPS.length];
    for (int i = 0; i < FILE_OPS.length; i += 1) {
      fileOperations[i] = new JButton(FILE_OPS[i]);
      fileOperations[i].setActionCommand(FILE_OPS[i]);
      fileOperations[i].addActionListener(controller);
      filePanel.add(fileOperations[i]);
    }
    mainPanel.add(filePanel, BorderLayout.PAGE_START);

    // layer panel on the right
    JPanel layersPanel = new JPanel();
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.PAGE_AXIS));

    DefaultListModel<String> layerNamesModel = new DefaultListModel<>();
    for (int i = 0; i < this.image.getNumLayers(); i += 1) {
      layerNamesModel.addElement("Layer " + i);
    }
    layerNames = new JList<>(layerNamesModel);
    layerNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    layerNames.addListSelectionListener(selectionListener);
    layersPanel.add(layerNames);

    JButton[] layerOperations = new JButton[LAYER_OPS.length];
    for (int i = 0; i < LAYER_OPS.length; i += 1) {
      layerOperations[i] = new JButton(LAYER_OPS[i]);
      layerOperations[i].setActionCommand(LAYER_OPS[i]);
      layerOperations[i].addActionListener(controller);
      layersPanel.add(layerOperations[i]);
    }
    mainPanel.add(layersPanel, BorderLayout.LINE_END);
  }

  private String writeImage() {
    String path = "/Users/keenanv/Documents/NEU/CS3500/Projects/imgproctests/";
    try {
      ImageUtil.writeFile(path + "temp.jpg", image.getTopVisibleLayer());
    } catch (IllegalArgumentException e) {
      ImageUtil.writeFile(path + "temp.jpg",
          LayerCreator.createSolidRect(image.getWidth(), image.getHeight(), 255, 255, 255));
    }

    return path + "temp.jpg";
  }

  @Override
  public void renderMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(SimpleGUIView.this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void printLayers() throws IOException {

  }

  @Override
  public void setImage(Image image) {
    this.image = image;
    writeImage();
    imageIcon.getImage().flush();
    imageDisplay.setIcon(imageIcon);
  }

  @Override
  public void refresh() {
    DefaultListModel<String> layerNamesModel = new DefaultListModel<>();
    for (int i = 0; i < this.image.getNumLayers(); i += 1) {
      layerNamesModel.addElement("Layer " + i);
    }
    layerNames.setModel(layerNamesModel);

    invalidate();
    validate();
    repaint();
  }

  @Override
  public Color chooseColor() {
    Color col = JColorChooser
        .showDialog(SimpleGUIView.this, "Choose a color", colorChooserDisplay.getBackground());
    colorChooserDisplay.setBackground(col);
    return col;
  }

  @Override
  public String openFile(boolean proj) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter;
    if (proj) {
      filter = new FileNameExtensionFilter("Project File .imgproc", "imgproc");
    } else {
      filter = new FileNameExtensionFilter("JPG,PNG,PPM", "jpg", "png", "ppm");
    }
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(SimpleGUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }

  @Override
  public void saveFile(boolean proj) {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(SimpleGUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      if (proj) {
        ImageUtil.writeMultiLayerImage(f.getAbsolutePath(), image);
      } else {
        ImageUtil.writeFile(f.getAbsolutePath(), image.getTopVisibleLayer());
      }
    }
  }

  @Override
  public String[] newImage() {
    String[] dimensions = new String[2];
    int retvalue = JOptionPane.showConfirmDialog(SimpleGUIView.this, newImagePanel, "New Image: ",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (retvalue  == JOptionPane.OK_OPTION) {
      dimensions[0] = width.getText();
      dimensions[1] = height.getText();
    }
    return dimensions;
  }
}
