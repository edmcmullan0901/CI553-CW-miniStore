package dbAccess;

/**
 * Implements Read access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

import catalogue.Product;
import debug.DEBUG;
import middle.StockException;
import middle.StockReader;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.sql.*;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

// mySQL
//    no spaces after SQL statement ;

/**
  * Implements read only access to the stock database.
  */
public class StockR implements StockReader
{
  private Connection theCon    = null;      // Connection to database
  private Statement  theStmt   = null;      // Statement object

  /**
   * Connects to database
   * Uses a factory method to help setup the connection
   * @throws StockException if problem
   */
  public StockR()
         throws StockException
  {
    try
    {
      DBAccess dbDriver = (new DBAccessFactory()).getNewDBAccess();
      dbDriver.loadDriver();

      theCon  = DriverManager.getConnection
                  ( dbDriver.urlOfDatabase(),
                    dbDriver.username(),
                    dbDriver.password() );

      theStmt = theCon.createStatement();
      theCon.setAutoCommit( true );
    }
    catch ( SQLException e )
    {
      throw new StockException( "SQL problem:" + e.getMessage() );
    }
    catch ( Exception e )
    {
      throw new StockException("Can not load database driver.");
    }
  }


  /**
   * Returns a statement object that is used to process SQL statements
   * @return A statement object used to access the database
   */

  protected Statement getStatementObject()
  {
    return theStmt;
  }

  /**
   * Returns a connection object that is used to process
   * requests to the DataBase
   * @return a connection object
   */

  protected Connection getConnectionObject()
  {
    return theCon;
  }

  /**
   * Checks if the product exits in the stock list
   * @param pNum The product number
   * @return true if exists otherwise false
   */
  public synchronized boolean exists( String pNum )
         throws StockException
  {

    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
        "select price from ProductTable " +
        "  where  ProductTable.productNo = '" + pNum + "'"
      );
      boolean res = rs.next();
      DEBUG.trace( "DB StockR: exists(%s) -> %s",
                    pNum, ( res ? "T" : "F" ) );
      return res;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL exists: " + e.getMessage() );
    }
  }

  /**
   * Returns details about the product in the stock list.
   *  Assumed to exist in database.
   * @param pNum The product number
   * @return Details in an instance of a Product
   */
  public synchronized Product getDetails( String pNum )
         throws StockException
  {
    try
    {
      Product dt = new Product( "0", "", 0.00, 0, "" );
      ResultSet rs = getStatementObject().executeQuery(
        "select  ProductTable.productNo, description, price, stockLevel, picture " +
        "  from ProductTable, StockTable " +
        "  where  ProductTable.productNo = '" + pNum + "' " +
        "  and    StockTable.productNo   = '" + pNum + "'"
      );
      if ( rs.next() )
      {
        dt.setProductNum(rs.getString("productNo"));
        dt.setDescription(rs.getString( "description" ) );
        dt.setPrice( rs.getDouble( "price" ) );
        dt.setQuantity( rs.getInt( "stockLevel" ) );
        dt.setPicture(rs.getString("picture"));
      }
      rs.close();
      return dt;
    } catch ( SQLException e )
    {
      throw new StockException( "SQL getDetails: " + e.getMessage() );
    }
  }


  public synchronized ImageIcon getImage( String pNum )
         throws StockException
  {
    String filename = "placeholder.jpg";
    try
    {
      ResultSet rs   = getStatementObject().executeQuery(
              "SELECT picture FROM ProductTable WHERE productNo = '" + pNum + "'"
      );
      if (rs.next()) {
        filename = rs.getString("picture" );
        if (filename == null || filename.isEmpty()) {
          DEBUG.trace("No picture specified for product: %s", pNum);
          filename = "placeholder.jpg";
        }
      } else {
        DEBUG.trace("No record found for product: %s", pNum);
      }
      rs.close();
    } catch ( SQLException e )
    {
      DEBUG.error( "getImage()\n%s\n", e.getMessage() );
      throw new StockException( "SQL getImage: " + e.getMessage() );
    }

    java.io.File file = new java.io.File(filename);
    if (!file.exists() || file.isDirectory()) {
      DEBUG.error("Image file not found: %s. Using placeholder image.", filename);
      return new ImageIcon("placeholder.jpg");
    }

    return new ImageIcon( filename );
  }
  @Override
  public List<Product> getProductNumbers() throws StockException
  {
    List<Product> productList = new ArrayList<>();
    try
    {
      // Execute SQL to get all product numbers from the ProductTable
      ResultSet rs = getStatementObject().executeQuery(
              "SELECT p.productNo, p.description, p.price, s.stockLevel, p.picture " +
                  "FROM ProductTable AS p " +
                  "JOIN StockTable s ON p.productNo = s.productNo"
      );

      // Iterate over the result set and collect all product numbers
      while (rs.next())
      {
        String productNo = rs.getString( "productNo" );
        String description = rs.getString( "description" );
        double price = rs.getDouble( "price" );
        int stockLevel = rs.getInt( "stockLevel" );
        String picture = rs.getString("picture");

        DEBUG.trace("Fetched product: %s, %s, %f, %d, %s", productNo, description, price, stockLevel, picture);

        Product product = new Product(productNo, description, price, stockLevel, picture);
        productList.add(product);
      }
      rs.close();
    } catch (SQLException e)
    {
      throw new StockException("SQL getProductNumbers: " + e.getMessage());
    }
    return productList;
  }


}
