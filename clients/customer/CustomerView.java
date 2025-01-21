package clients.customer;

import catalogue.Product;
import clients.Main;
import clients.Picture;
import clients.login.LoginClient;
import middle.MiddleFactory;
import middle.StockReader;
import clients.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class CustomerView implements Observer
{
  class Name {  // Names of buttons
    public static final String CHECK = "Check";
    public static final String CLEAR = "Clear";
  }

  private static final int H = 300;  // Height of window pixels
  private static final int W = 400;  // Width  of window pixels

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextField theInput = new JTextField();
  private final JTextArea theOutput = new JTextArea();
  private final JScrollPane theSP = new JScrollPane();
  private final JButton backButton;
  private JFrame parentFrame;

  private final Picture thePicture = new Picture(80, 80);
  private StockReader theStock = null;
  private CustomerController cont = null;

  private JPanel buttonPanel;  // Panel to hold product buttons
  private JTextArea productInfoField;

  private Main mainApp;
  private MiddleFactory mlf;
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen
   * @param y     y-cordinate of position of window on screen
   */
  public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y, JFrame parentFrame, Main mainApp)
  {
    this.parentFrame = parentFrame;
    this.mainApp = mainApp;
    this.mlf = mf;
    try {
      theStock = mf.makeStockReader(); // Database Access
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane();  // Content Pane
    cp.setLayout(null);  // Layout management


    // Remove unnecessary components like buttons, labels, and text fields
    // Create the button panel that will hold dynamic product buttons
    buttonPanel = new JPanel();
    buttonPanel.setBounds(110, 100, 270, 500);  // Panel area
    buttonPanel.setLayout(new GridLayout(0, 2)); // Layout to stack buttons vertically
    cp.add(buttonPanel);

    productInfoField = new JTextArea();
    productInfoField.setEditable(false);
    productInfoField.setBounds(400, 350, 100, 100);
    cp.add(productInfoField);

    pageTitle.setText("Catalogue");
    pageTitle.setBounds(200, 10, 200, 100);
    pageTitle.setFont(new Font("Arial", Font.BOLD, 30));
    pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
    cp.add(pageTitle);

    backButton = new JButton("Back");
    backButton.setBounds(500,200,100,50);
    backButton.addActionListener(e -> logout());
    cp.add(backButton);


    if (rpc instanceof JFrame) {
      JFrame frame = (JFrame) rpc;
      frame.setSize(800,600);
    }
    // Populate dynamic buttons (only the product buttons)
    populateProductButtons();

    cp.revalidate();
    cp.repaint();

    if (rpc instanceof JFrame) {
      ((JFrame) rpc).setVisible(true);  // Make the window visible
    }
  }

  /**
   * Populates the product buttons in the view.
   */
  public void populateProductButtons()
  {
    try {
      java.util.List<Product> productNumbers = theStock.getProductNumbers();
      buttonPanel.removeAll();  // Clear any previously added buttons
      for (Product product : productNumbers) {
        String imagePath = product.getPicture();

        File imageFile = new File(imagePath);
        ImageIcon productImage = null;
        if (imageFile.exists()) {
          productImage = new ImageIcon(imagePath);
        } else {
          productImage = new ImageIcon("images/placeholder.jpg");
          System.out.println("No image found");
        }

        JButton productButton = new JButton(product.getDescription(), productImage);
        productButton.addActionListener(e -> {
          SoundPlayer.playSound("audio/button_press.wav");
          showProductDetails(product);
                });
        buttonPanel.add(productButton);

        productButton.setPreferredSize(new Dimension(400,100));
        productButton.setText("");

        System.out.println("DEBUG : Added button for product: " + product.getDescription());
      }

      buttonPanel.revalidate(); // Refresh the button panel
      buttonPanel.repaint();    // Repaint the panel

    } catch (Exception e) {
      System.out.println("Error fetching product buttons: " + e.getMessage());
    }
  }

  public void showProductDetails(Product product) {
    String productDetails =  String.format("Item: %s\nNumber: %s\nPrice: %.2f",
            product.getDescription(), product.getProductNum(), product.getPrice());
    productInfoField.setText(productDetails);
  }

  private void logout() {
    System.out.println("Logout triggered");
    if (mainApp == null || mainApp.getMlf() == null) {
      throw new IllegalStateException("MiddleFactory (mlf) is not initialized");
    }
    // Close the current customer view window
    if (parentFrame != null) {
      // Hide the customer view window
      parentFrame.setVisible(false);
      System.out.println("customer frame hidden");
    }

    LoginClient loginClient = LoginClient.getInstance(mainApp, mlf);
    loginClient.getView().setVisible(true);

  }
  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */
  public void setController(CustomerController c)
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args
   */
  public void update(Observable modelC, Object arg)
  {
    CustomerModel model = (CustomerModel) modelC;
    String message = (String) arg;
    theAction.setText(message);
    ImageIcon image = model.getPicture();  // Image of product
    if (image == null)
    {
      thePicture.clear();  // Clear picture
    } else {
      thePicture.set(image);  // Display picture
    }
    theOutput.setText(model.getBasket().getDetails());
    theInput.requestFocus();  // Focus is here
  }

  }


