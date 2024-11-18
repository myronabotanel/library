package org.example.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.database.Constants.Rights.BUY_BOOK;
import static org.example.database.Constants.Rights.CREATE_BOOK;
import static org.example.database.Constants.Rights.DELETE_BOOK;
import static org.example.database.Constants.Rights.RETURN_BOOK;
import static org.example.database.Constants.Rights.RIGHTS;
import static org.example.database.Constants.Rights.SELL_BOOK;
import static org.example.database.Constants.Rights.UPDATE_BOOK;
import static org.example.database.Constants.Roles.ADMINISTRATOR;
import static org.example.database.Constants.Roles.CUSTOMER;
import static org.example.database.Constants.Roles.EMPLOYEE;
import static org.example.database.Constants.Roles.ROLES;
public class Constants {

    public static Map<String, List<String>> getRolesRights() { //fiecare rol are asociat o lista de drepturi
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>()); //keys
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_BOOK, DELETE_BOOK, UPDATE_BOOK, SELL_BOOK));

        rolesRights.get(CUSTOMER).addAll(Arrays.asList(SELL_BOOK, BUY_BOOK, RETURN_BOOK)); //aici le dam drepturi fiecarui user.

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "testlibrary";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String BOOK = "book";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                BOOK}; //face o lista de string uri cu ordinea tabelelor dupa creare
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
    }

    public static class Rights { //CRUD pe user
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        //CRUD pe carti. lipseste constanta de retrev: findAll. toti userii au dreptul sa vada cartile => nu l am mai pus
        public static final String CREATE_BOOK = "create_book";
        public static final String DELETE_BOOK = "delete_book";
        public static final String UPDATE_BOOK = "update_book";

        //pentru costumer
        public static final String SELL_BOOK = "sell_book";
        public static final String BUY_BOOK = "buy_book";
        public static final String RETURN_BOOK = "return_book";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_BOOK,
                DELETE_BOOK, UPDATE_BOOK, SELL_BOOK, BUY_BOOK, RETURN_BOOK};
    }
}

