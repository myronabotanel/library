package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import org.example.database.Constants;
import org.example.launcher.EmployeeComponentFactory;
import org.example.launcher.LoginComponentFactory;
import org.example.mapper.UserMapper;
import org.example.model.Order;
import org.example.model.Right;
import org.example.model.validator.Notification;
import org.example.service.admin.AdminService;
import org.example.service.order.OrderService;
import org.example.service.user.AuthenticationService;
import org.example.view.AdminView;
import org.example.view.model.UserDTO;
import org.example.view.model.builder.UserDTOBuilder;
import org.example.model.Role;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;







import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController
{
    private final AdminView adminView;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    public AdminController(AdminView adminView, AdminService adminService, AuthenticationService authenticationService, OrderService orderService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.authenticationService = authenticationService;
        this.orderService = orderService;

        // Adăugăm listener pentru butoanele din AdminView
        this.adminView.addSaveButtonListener(new SaveButtonListener());
        this.adminView.addViewBooksButtonListener(new ViewBooksButtonListener());
        this.adminView.addSelectionTableListener(new SelectionTableListener());
        this.adminView.addDeleteButtonListener(new DeleteButtonListener());
        this.adminView.addGenerateReportListener(new GenerateReportListener());
        this.adminView.addUpgradeButtonListener(new UpgradeListener());
    }

    private void refreshTable() {
        List<UserDTO> allUsers = adminService.findAll()
                .stream()
                .map(user -> new UserDTOBuilder()
                        .setUsername(user.getUsername())
                        .setRole(user.getRoles().stream()
                                .map(Role::getRole)
                                .collect(Collectors.joining(", ")))
                        .build())
                .toList();
        adminView.getUsersObservableList().setAll(allUsers);
        adminView.getUserTableView().refresh();
    }


    private class SaveButtonListener implements EventHandler<ActionEvent>{
        public void handle (ActionEvent event){
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            Notification<Boolean> registerNotification = authenticationService.registerEmployee(username, password);

            if (registerNotification.hasErrors()) {
                adminView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                adminView.setActionTargetText("Register successful!");
            }
            refreshTable();
        }
    }

    private class ViewBooksButtonListener implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
        }

    }

    private class SelectionTableListener implements ChangeListener<UserDTO>{
        @Override
        public void changed(ObservableValue<? extends UserDTO>observable, UserDTO oldValue, UserDTO newValue){
            if(newValue != null)
            {
                System.out.println("Selectet user: " + newValue.getUsername());
            }else{
                System.out.println("No user selected!");
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            UserDTO userDTO = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();
            if(userDTO != null)
            {
                boolean detetionSuccessfull = adminService.delete(UserMapper.convertUserDTOToUser(userDTO));
                if(detetionSuccessfull){
                    adminView.removeUserFromObservableList(userDTO);
                } else{
                    adminView.displayAlertMessage("Deletion not succesfull", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            }else{
                //nu a fost selectat nici un item
                adminView.displayAlertMessage("Deletion not successfull!", "Deletion process", "you need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class GenerateReportListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            try {
                List<Order> orders = orderService.getOrdersFromLastMonth(); // Get orders from last month

                if (orders.isEmpty()) {
                    adminView.displayAlertMessage("No data available", "Report Generation", "No orders found for the last month.");
                    return;
                }

                // Generate the PDF report
                generatePdfReport(orders);

                adminView.displayAlertMessage("Report Generated", "PDF Report", "The sales report for the last month has been generated.");
            } catch (Exception e) {
                e.printStackTrace();
                adminView.displayAlertMessage("Error", "Report Generation", "An error occurred while generating the report.");
            }
        }

        private void generatePdfReport(List<Order> orders) throws Exception {
            // Crează PdfWriter pentru fișierul PDF
            String fileName = "Sales_Report_Last_Month.pdf";
            PdfWriter writer = new PdfWriter(fileName);

            // Crează PdfDocument folosind PdfWriter
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Crează un Document care va conține conținutul PDF-ului
            Document document = new Document(pdfDocument);

            // Încarcă fontul Helvetica Bold din fonturile încorporate
            PdfFont helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Adaugă un titlu la raport folosind fontul încărcat
            document.add(new Paragraph("Sales Report for Last Month")
                    .setFont(helveticaBold)
                    .setFontSize(18));
                    //.setTextAlignment(TextAlignment.CENTER));

            // Crează un tabel pentru datele din raport (5 coloane)
            Table table = new Table(5);
            table.addCell("Order ID");
            table.addCell("Book Title");
            table.addCell("Seller Name");
            table.addCell("Quantity");
            table.addCell("Total Price");

            // Adaugă fiecare comandă în tabel
            for (Order order : orders) {
                table.addCell(String.valueOf(order.getId()));
                table.addCell(order.getBookTitle());
                table.addCell(order.getSellerName());
                table.addCell(String.valueOf(order.getQuantity()));
                table.addCell(String.valueOf(order.getTotalPrice()));
            }

            // Adaugă tabelul în document
            document.add(table);

            // Închide documentul (salvează PDF-ul)
            document.close();
        }


    }

    private class UpgradeListener  implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            UserDTO userDTO = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();
            if (userDTO == null) {
                adminView.displayAlertMessage("Error", "No user selected", "Please select a user to upgrade.");
                return;
            }
            // Verificam daca utilizatorul este EMPLOYEE
            if (!"EMPLOYEE".equalsIgnoreCase(userDTO.getRole())) {
                adminView.displayAlertMessage("Error", "Cannot upgrade user", "Only employees can be upgraded to admin.");
                return;
            }

            // Actualizam rolul utilizatorului
            userDTO.setRole("admin");

            //lista drepturi admin
            List<Right> adminRights = new ArrayList<>();
            for (String rightName : Constants.Rights.RIGHTS) {
                adminRights.add(new Right(null, rightName));
            }

            // Cream rolul 'ADMIN' cu toate drepturile
            Role role = new Role(1L, "administrator", adminRights);
            boolean updateSuccessful = adminService.updateUserRole(userDTO.getUsername(), role);
            // schimbam în lista observabila
            if (updateSuccessful) {
                // Actualizam si interfata grafica
                adminView.getUserTableView().refresh();
                adminView.displayAlertMessage("Success", "User upgraded", "The selected user is now an admin.");
            } else {
                adminView.displayAlertMessage("Error", "Database Update Failed", "An error occurred while updating the user in the database.");
            }
        }
    }


}
