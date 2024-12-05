package org.example.launcher;

import javafx.stage.Stage;
import org.example.controller.AdminController;
import org.example.database.DatabaseConnectionFactory;
import org.example.mapper.UserMapper;
import org.example.repository.OrderRepository;
import org.example.repository.OrderRepositoryMySQL;
import org.example.repository.security.RightsRolesRepository;
import org.example.repository.security.RightsRolesRepositoryMySQL;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryMySQL;
import org.example.service.admin.AdminService;
import org.example.service.admin.AdminServiceImplementation;
import org.example.service.order.OrderService;
import org.example.service.order.OrderServiceImpl;
import org.example.service.user.AuthenticationService;
import org.example.service.user.AuthenticationServiceImpl;
import org.example.view.AdminView;
import org.example.view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    private final AdminView adminView;
    private final AdminController adminController;
    private final AdminService adminService;
    private final OrderService orderService;
    private final AuthenticationService authenticationService;
    private static AdminComponentFactory instance;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final OrderRepository orderRepository;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage stage) {
        if (instance == null) {
            instance = new AdminComponentFactory(componentsForTest, stage);
        }
        return instance;
    }

    private AdminComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.adminService = new AdminServiceImplementation(userRepository, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        List<UserDTO> userDTOs = UserMapper.convertUserListToUserDTOList(this.adminService.findAll());
        this.adminView = new AdminView(stage, userDTOs);
        this.orderRepository = new OrderRepositoryMySQL(connection);
        this.orderService = new OrderServiceImpl(orderRepository);
        this.adminController = new AdminController(adminView, adminService, authenticationService, orderService);
    }


}
