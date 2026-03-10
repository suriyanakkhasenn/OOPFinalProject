package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class MainController{
	
	@FXML private Label totalCarsLabel;

	@FXML private Label activeRentalsLabel;
	
    @FXML
    private TextField txtUsername; // ต้องตรงกับ fx:id ใน Scene Builder

    @FXML
    private PasswordField txtPassword; // ต้องเป็น PasswordField ถึงจะขึ้นเป็นจุดดำๆ

    @FXML private TableView<Car> tableView;
    
    @FXML private TableColumn<Car, String> colId, colModel, colPlate, colType, colPrice, colStatus, colCustomer;
    
    @FXML
    void onLoginClick(ActionEvent event) {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        try {
            if (user.equals("admin") && pass.equals("123")) {
                // ถ้าเป็น Admin ไปหน้าจัดการระบบ
                switchScene(event, "AdminDashboard.fxml", "ระบบจัดการรถเช่า - Admin");
                
            } else if (user.equals("user") && pass.equals("123")) {
                // ถ้าเป็น User ไปหน้าจองรถ
                switchScene(event, "UserBooking.fxml", "บริการเช่ารถ - Customer");
                
            } else {
                // ถ้ารหัสผิด แสดง Alert
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง!");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // สร้างฟังก์ชันเสริมเพื่อช่วยสลับหน้าจอ (จะได้ไม่ต้องเขียนซ้ำ)
    private void switchScene(ActionEvent event, String fxmlFile, String title) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
    
 // 1. หน้า Dashboard
    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/admin/AdminDashboard.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
 // 2. หน้า Inventory
    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
        // โหลดหน้า Inventory.fxml
        Parent inventoryView = FXMLLoader.load(getClass().getResource("/application/admin/AdminInventory.fxml"));
        Scene inventoryScene = new Scene(inventoryView);
        
        // ดึงหน้าต่างปัจจุบัน (Stage) ออกมาแล้วเปลี่ยนหน้า
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(inventoryScene);
        window.show();
    }
 // 3. หน้า Booking/Rental
    @FXML
    public void switchToBookingRental(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/admin/AdminBookingRental.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // 4. หน้า Customers
    @FXML
    public void switchToCustomers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/admin/AdminCustomers.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // 5. หน้า Reports
    @FXML
    public void switchToReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/application/admin/AdminReports.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public void initialize() {
        // เช็คก่อนว่าในหน้านี้มี tableView หรือไม่ (ถ้าเป็นหน้า Login มันจะเป็น null)
        if (tableView != null) { 
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
            colPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colStatus.setCellFactory(column -> {
                return new TableCell<Car, String>() { // ตรวจสอบว่า Car ตัวใหญ่ตรงกับชื่อ Class ของคุณ
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle(""); 
                        } else {
                            setText(item);
                            // เช็คเงื่อนไขสี
                            if (item.equalsIgnoreCase("Available")) {
                                setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                            } else if (item.equalsIgnoreCase("Rented")) {
                                setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                            } else if (item.equalsIgnoreCase("Maintenance")) {
                                setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold;");
                            } else {
                                setStyle("-fx-text-fill: white;");
                            }
                        }
                    }
                }; // ต้องปิดด้วย }; และวงเล็บปิดของ setCellFactory
            });
            colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));

            ObservableList<Car> carList = FXCollections.observableArrayList(
                new Car("1", "Toyota Camry", "กข-123", "Sedan", "1,500", "Rented", "Suthema Pasa"),
                new Car("2", "Honda Civic", "ชล-999", "Sedan", "1,200", "Rented", "Suriya Nakkhasen"),
                new Car("3", "Tesla Model 3", "EV-001", "Electric", "3,000", "Available", "Phumiphat Hem"),
                new Car("4", "Mater", "ขก-56", "Tow Truck", "2,000", "Available", "Nadthakorn srichon")
            );
            tableView.setItems(carList);
         // นับจำนวนรายการใน carList แล้วเอาไปใส่ใน Label
            if (totalCarsLabel != null) {
                int count = carList.size(); 
                totalCarsLabel.setText(String.valueOf(count));
            }
         // นับจำนวนรถที่มี Status เป็น "Rented" เท่านั้น
            if (activeRentalsLabel != null) {
            long rentedCount = carList.stream()
                                      .filter(car -> "Rented".equalsIgnoreCase(car.getStatus()))
                                      .count();

            // แสดงผลบนหน้าจอ
            activeRentalsLabel.setText(String.valueOf(rentedCount));
            }
            updateDashboard(carList);
        }
    }
    private void updateDashboard(ObservableList<Car> list) {
        // 1. จำนวนรถทั้งหมด (ใช้สีขาวหรือสีม่วงอ่อนตามธีม Dashboard)
        if (totalCarsLabel != null) {
            totalCarsLabel.setText(String.valueOf(list.size()));
            totalCarsLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); 
        }

        // 2. จำนวนรถที่ถูกเช่า (Active Rentals)
        if (activeRentalsLabel != null) {
            long rentedCount = list.stream()
                .filter(car -> "Rented".equalsIgnoreCase(car.getStatus()))
                .count();
            
            activeRentalsLabel.setText(String.valueOf(rentedCount));
            
            // [ปรับสีที่นี่] ใช้สีแดง (#e74c3c) ให้เหมือนกับคำว่า Rented ในตาราง
            activeRentalsLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }
}