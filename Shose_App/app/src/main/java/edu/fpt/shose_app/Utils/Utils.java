package edu.fpt.shose_app.Utils;

import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Model.Cart;
import edu.fpt.shose_app.Model.User;

public class Utils {
    public static final String BASE_URL_API = "https://shoseapp.click/api/";
    public static User Users_Utils = new User();
    public static List<Cart> cartLists =new ArrayList<>();
    public static List<Cart> buyCartLits = new ArrayList<>();

    public static String ID_Received = "100";
    public static final String SEND_ID = "idsend";
    public static final String RECEIVEDID_ID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
