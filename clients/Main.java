package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import clients.login.LoginClient;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import middle.StockException;

import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface)  as a single application.
 * Good for testing the system using a single application.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @author  Shine University of Brighton
 * @version year-2024
 */

public class Main

{

  private boolean backDoorStarted = false;
  private boolean packingStarted = false;
  private boolean cashierStarted = false;
  private boolean customerStarted = false;
  private MiddleFactory mlf;
  private JFrame window;

  public static void main (String args[]) throws StockException {
    new Main().begin();
  }


  /**
   * Starts the system (Non distributed)
   */
  public void begin() throws StockException {

    mlf = new LocalMiddleFactory();
    System.out.println("Main mlf initialized: " + mlf);
    System.out.println("Main instance before passing: " + this); // debugging
    LoginClient.getInstance(this, mlf);
    System.out.println("Main instance before passing: " + this); // debugging




    JFrame frame = new JFrame("MiniStore"); //Main frame where all clients will be viewed
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);



  }


  //Methods for checking if clients are started
  public boolean isCashierStarted() {
    return cashierStarted;
  }
  public boolean isCustomerStarted() {
    return customerStarted;
  }
  public boolean isBackDoorStarted() {
    return backDoorStarted;
  }
  public boolean isPackingStarted() {
    return packingStarted;
  }
  //marks clients as started
  public void setCashierStarted(boolean cashierStarted) {
    this.cashierStarted = cashierStarted;
  }
  public void setCustomerStarted(boolean customerStarted) {
    this.customerStarted = customerStarted;
  }
  public void setPackingStarted(boolean packingStarted) {
    this.packingStarted = packingStarted;
  }
  public void setBackDoorStarted(boolean backDoorStarted) {
    this.backDoorStarted = backDoorStarted;
  }
  /**
   * start the Customer client, -search product
   * @param mlf A factory to create objects to access the stock list
   */
  public void startCustomerGUI_MVC(MiddleFactory mlf )
  {
    System.out.println("Starting customer gui");

    if (customerStarted) {
        if (window != null) {
          window.setVisible(true);
        }
        return;


    }
    customerStarted = true;
    JFrame  window = new JFrame();
    window.setTitle( "Customer Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();

    CustomerModel model      = new CustomerModel(mlf);
    CustomerView view        = new CustomerView( window, mlf, pos.width, pos.height, window, this);
    CustomerController cont  = new CustomerController( model, view );
    view.setController( cont );

    model.addObserver( view );
    view.populateProductButtons();
    window.setVisible(true);
  }

  /**
   * start the cashier client - customer check stock, buy product
   * @param mlf A factory to create objects to access the stock list
   */
  public void startCashierGUI_MVC(MiddleFactory mlf )
  {
    if (cashierStarted) return;
    cashierStarted = true;
    JFrame  window = new JFrame();
    window.setTitle( "Cashier Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();

    CashierModel model      = new CashierModel(mlf);
    CashierView view        = new CashierView( window, mlf, pos.width, pos.height );
    CashierController cont  = new CashierController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
    model.askForUpdate();            // Initial display
  }

  /**
   * start the Packing client - for warehouse staff to pack the bought order for customer, one order at a time
   * @param mlf A factory to create objects to access the stock list
   */

  public void startPackingGUI_MVC(MiddleFactory mlf)
  {
    if (packingStarted) return;
    packingStarted = true;
    JFrame  window = new JFrame();

    window.setTitle( "Packing Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();

    PackingModel model      = new PackingModel(mlf);
    PackingView view        = new PackingView( window, mlf, pos.width, pos.height );
    PackingController cont  = new PackingController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }

  /**
   * start the BackDoor client - store staff to check and update stock
   * @param mlf A factory to create objects to access the stock list
   */
  public void startBackDoorGUI_MVC(MiddleFactory mlf )
  {

    if (backDoorStarted) return;
    backDoorStarted = true;

    JFrame  window = new JFrame();

    window.setTitle( "BackDoor Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();

    BackDoorModel model      = new BackDoorModel(mlf);
    BackDoorView view        = new BackDoorView( window, mlf, pos.width, pos.height );
    BackDoorController cont  = new BackDoorController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }

  public MiddleFactory getMlf() {
    return mlf;
  }

}
