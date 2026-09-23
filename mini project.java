import java.sql.*;
import java.util.Scanner;
public class OnlineFoodOrder {
    static final String URL = "jdbc:mysql://localhost:3306/foodonorder";
    static final String USER = "root";
    static final String PASSWORD = "test@123";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                int choice;
                do {
                    
                    System.out.println("\n====================================");
                    System.out.println(" ONLINE FOOD ORDER SYSTEM");
                    System.out.println("====================================");
                    System.out.println("1. Place Order");
                    System.out.println("2. View Orders");
                    System.out.println("3. Update Order");
                    System.out.println("4. Cancel Order");
                    System.out.println("5. Search Order");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    
                    choice = sc.nextInt();
                    
                    switch (choice) {
                        
                        case 1:
                            
                            System.out.print("Enter Order ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();
                            
                            System.out.print("Enter Customer Name: ");
                            String name = sc.nextLine();
                            
                            System.out.print("Enter Food Item: ");
                            String food = sc.nextLine();
                            
                            System.out.print("Enter Quantity: ");
                            int qty = sc.nextInt();
                            
                            System.out.print("Enter Total Price: ");
                            double price = sc.nextDouble();
                            
                            String insert = "INSERT INTO onorder VALUES(?,?,?,?,?)";
                            
                            PreparedStatement ps = con.prepareStatement(insert);
                            
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setString(3, food);
                            ps.setInt(4, qty);
                            ps.setDouble(5, price);
                            
                            int row = ps.executeUpdate();
                            
                            if (row > 0)
                                System.out.println("Order Placed Successfully.");
                            else
                                System.out.println("Order Failed.");
                            
                            break;
                            
                        case 2:
                            
                            Statement st = con.createStatement();
                            
                            ResultSet rs = st.executeQuery("SELECT * FROM onorder");
                            
                            System.out.println("\n---------------------------------------------------------");
                            System.out.println("ID\tCustomer\tFood\t\tQty\tPrice");
                            System.out.println("---------------------------------------------------------");
                            
                            while (rs.next()) {
                                
                                System.out.println(
                                        rs.getInt("order_id") + "\t" +
                                                rs.getString("customer_name") + "\t" +
                                                rs.getString("food_item") + "\t" +
                                                rs.getInt("quantity") + "\t" +
                                                rs.getDouble("total_price"));
                                
                            }
                            
                            break;
                            
                        case 3:
                            
                            System.out.print("Enter Order ID: ");
                            int updateId = sc.nextInt();
                            
                            System.out.print("Enter New Quantity: ");
                            int newQty = sc.nextInt();
                            
                            System.out.print("Enter New Total Price: ");
                            double newPrice = sc.nextDouble();
                            
                            String update = "UPDATE onorder SET quantity=?, total_price=? WHERE order_id=?";
                            
                            PreparedStatement ps2 = con.prepareStatement(update);
                            
                            ps2.setInt(1, newQty);
                            ps2.setDouble(2, newPrice);
                            ps2.setInt(3, updateId);
                            
                            int updateRow = ps2.executeUpdate();
                            
                            if (updateRow > 0)
                                System.out.println("Order Updated Successfully.");
                            else
                                System.out.println("Order Not Found.");
                            
                            break;
                            
                        case 4:
                            
                            System.out.print("Enter Order ID: ");
                            int deleteId = sc.nextInt();
                            
                            String delete = "DELETE FROM onorder WHERE order_id=?";
                            
                            PreparedStatement ps3 = con.prepareStatement(delete);
                            
                            ps3.setInt(1, deleteId);
                            
                            int deleteRow = ps3.executeUpdate();
                            
                            if (deleteRow > 0)
                                System.out.println("Order Cancelled Successfully.");
                            else
                                System.out.println("Order Not Found.");
                            
                            break;
                            
                        case 5:
                            
                            System.out.print("Enter Order ID to Search: ");
                            int searchId = sc.nextInt();
                            
                            String search = "SELECT * FROM onorder WHERE order_id=?";
                            
                            PreparedStatement ps4 = con.prepareStatement(search);
                            
                            ps4.setInt(1, searchId);
                            
                            ResultSet rs1 = ps4.executeQuery();
                            
                            if (rs1.next()) {
                                
                                System.out.println("\n========== ORDER DETAILS ==========");
                                System.out.println("Order ID      : " + rs1.getInt("order_id"));
                                System.out.println("Customer Name : " + rs1.getString("customer_name"));
                                System.out.println("Food Item     : " + rs1.getString("food_item"));
                                System.out.println("Quantity      : " + rs1.getInt("quantity"));
                                System.out.println("Total Price   : " + rs1.getDouble("total_price"));
                                
                            } else {
                                
                                System.out.println("Order Not Found.");
                                
                            }
                            
                            break;
                            
                        case 6:
                            
                            System.out.println("Thank You...");
                            break;
                            
                        default:
                            System.out.println("Invalid Choice.");
                            
                    }
           
                } while (choice != 6);
            }
            sc.close();

        } catch (ClassNotFoundException e) {

            System.out.println("MySQL JDBC Driver Not Found.");

        } catch (SQLException e) {

            System.out.println("Database Error : " + e.getMessage());

        } catch (Exception e) {

            System.out.println(e);

        }

    }
}
