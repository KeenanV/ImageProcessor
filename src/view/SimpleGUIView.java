package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ColorTransformation;
import model.ColorTransformation.ColorTransformationMatrix;
import model.Filter;
import model.Filter.FilterMatrix;
import model.Image;
import model.Layer;
import model.LayerCommand;
import model.SimpleImage;

public class SimpleGUIView extends JFrame implements GUIView, ActionListener,
    ListSelectionListener {

  private static final String[] IMAGE_OPS = new String[]
      {"Blur", "Sharpen", "Grayscale", "Sepia"};
  private static final String[] FILE_OPS = new String[]
      {"Save Image", "Save Project", "Load Image", "Load Project"};
  private static final String[] LAYER_OPS = new String[]
      {"Add Layer", "Move to Top", "Hide/Show", "Delete Layer"};
  private Image image;

  private JPanel mainPanel;
  private JPanel imagePanel;
  private JPanel operationsPanel;
  private JPanel colorChooserPanel;
  private JPanel filePanel;
  private JPanel layersPanel;

  private JLabel imageDisplay;
  private JLabel colorChooserDisplay;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private JButton[] imgOperations;
  private JButton[] fileOperations;
  private JButton[] layerOperations;

  private JList<String> layerNames;

  public SimpleGUIView(Image image) {
    this.image = image;

    // main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // image panel in the center
    imagePanel = new JPanel();
    mainPanel.add(imagePanel, BorderLayout.CENTER);
    imageDisplay = new JLabel();
    //imageDisplay.setIcon(new ImageIcon(writeImage(image));

    // operations panel on the left
    operationsPanel = new JPanel();
    operationsPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.PAGE_AXIS));

    imgOperations = new JButton[IMAGE_OPS.length];
    for (int i = 0; i < IMAGE_OPS.length; i += 1) {
      imgOperations[i] = new JButton(IMAGE_OPS[i]);
      imgOperations[i].setActionCommand(IMAGE_OPS[i]);
      imgOperations[i].addActionListener(this);
      operationsPanel.add(imgOperations[i]);
    }
    mainPanel.add(operationsPanel, BorderLayout.LINE_START);

    // color chooser on the bottom
    colorChooserPanel = new JPanel();
    colorChooserPanel.setLayout(new FlowLayout());
    JButton colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color chooser");
    colorChooserButton.addActionListener(this);
    colorChooserPanel.add(colorChooserButton);
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true);
    colorChooserDisplay.setBackground(Color.WHITE);
    colorChooserPanel.add(colorChooserDisplay);

    // file panel on the top
    filePanel = new JPanel();
    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));

    fileOperations = new JButton[FILE_OPS.length];
    for (int i = 0; i < FILE_OPS.length; i += 1) {
      fileOperations[i] = new JButton(FILE_OPS[i]);
      fileOperations[i].setActionCommand(FILE_OPS[i]);
      fileOperations[i].addActionListener(this);
      filePanel.add(fileOperations[i]);
    }
    mainPanel.add(filePanel, BorderLayout.PAGE_START);

    // layer panel on the right
    layersPanel = new JPanel();
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layersPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.PAGE_AXIS));

    DefaultListModel<String> layerNamesModel = new DefaultListModel<>();
    for (int i = 0; i < image.getNumLayers(); i += 1) {
      layerNamesModel.addElement("Layer " + i);
    }
    layerNames = new JList<>(layerNamesModel);
    layerNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    layerNames.addListSelectionListener(this);
    layersPanel.add(layerNames);

    layerOperations = new JButton[LAYER_OPS.length];
    for (int i = 0; i < LAYER_OPS.length; i += 1) {
      layerOperations[i] = new JButton(LAYER_OPS[i]);
      layerOperations[i].setActionCommand(LAYER_OPS[i]);
      layerOperations[i].addActionListener(this);
      layersPanel .add(layerOperations[i]);
    }
    mainPanel.add(layersPanel, BorderLayout.LINE_END);
  }

  @Override
  public void setImage(Image image) {

  }

  @Override
  public void refresh() {
    setImage(image);
  }

  @Override
  public Color chooseColor() {

    return null;
  }

  @Override
  public String openFile(boolean proj) {

    return null;
  }

  @Override
  public void saveFile(boolean proj) {

  }
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Blur":
        setImage(applyToLayer(image, layerNames.getSelectedIndex(),
            new Filter(FilterMatrix.BLUR)));
        break;
      case "Sharpen":
        setImage(applyToLayer(image, layerNames.getSelectedIndex(),
            new Filter(FilterMatrix.SHARPEN)));
        break;
      case "Grayscale":
        setImage(applyToLayer(image, layerNames.getSelectedIndex(),
            new ColorTransformation(ColorTransformationMatrix.GRAYSCALE)));
        break;
      case "Sepia":
        setImage(applyToLayer(image, layerNames.getSelectedIndex(),
            new ColorTransformation(ColorTransformationMatrix.SEPIA)));
        break;
      case "Move to Top":
        image.addLayer(image.removeLayer(layerNames.getSelectedIndex()));
        break;
      case "Hide/Show":
        Layer layer = image.getLayer(layerNames.getSelectedIndex());
        layer.setVisible(!layer.getVisible());
        break;
      case "Delete Layer":
        image.removeLayer(layerNames.getSelectedIndex());
        break;
    }
    refresh();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // do nothing
  }

  private Image applyToLayer(Image image, int index, LayerCommand command) {
    if (image == null || index >= image.getNumLayers() || index < 0 || command == null) {
      throw new IllegalArgumentException();
    }

    Image newImage = new SimpleImage(image.getWidth(), image.getHeight());
    for (int i = 0; i < image.getNumLayers(); i += 1) {
      if (i != index) {
        newImage.addLayer(image.getLayer(i));
      } else {
        newImage.addLayer(command.start(image.getLayer(i)));
      }
    }

    return newImage;
  }
}
