package protocol;

/**
 * Class used to store server-client communication protocols
 * @author Bespoke Burger
 *
 */
public final class Protocol {
    /**Indicates the request resulted in an error
     * @see FAILURE
     * @see SUCCESS*/
    public static final short ERROR = -1;
    /**Indicates the request failed
     * @see ERROR
     * @see SUCCESS*/
    public static final short FAILURE = 0;
    /**Indicates the request was successful
     * @see ERROR
     * @see FAILURE*/
    public static final short SUCCESS = 1;
    

    /**Used as a first communication from clients to indicate the client is the store or the web server*/
    public static final String REGISTER_AS = "RGSTR";
    /**Used in conjunction with REGISTER_AS to indicate the client is the web server
     * @see REGISTER_AS*/
    public static final String WEBSITE = "WEB";
    /**Used in conjunction with REGISTER_AS to indicate the client is the store
     * @see REGISTER_AS*/
    public static final String STORE = "STORE";
    
    
    /**The primary delimiter used to separate tokens*/
    public static final String DELIM = ",";
    /**<p>[FROM WEB SERVER ONLY]</p>
     * Indicates the client is sending a new order.<br>
     * Followed by the following string format: orderNumber,customerName,ingredientName,num,ingredientName,num etc*/
    public static final String NEW_ORDER = "ORDER";
    /**<p>[USED BY STORE CLIENTS ONLY]</p>
     * Used to inform of a change of order status<br>
     * Followed by the following string format: orderNumber,customerName,status*/
    public static final String CHANGE_STATUS = "STATUS";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to inform of an increase in quantity for a specific ingredient<br>
     * Followed by the following string format: ingredientName,byAmount*/
    public static final String INCREASE_QUANTITY = "INCR";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to inform of an decrease in quantity for a specific ingredient<br>
     * Followed by the following string format: ingredientName,byAmount*/
    public static final String DECREASE_QUANTITY = "DECR";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to inform of a change to the minimum acceptable quantity for a specific ingredient<br>
     * Followed by the following string format: ingredientName,threshold*/
    public static final String SET_THRESHOLD = "THRESH";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to inform of an update of the customer price for a specific ingredient<br>
     * Followed by the following string format: ingredientName,price*/
    public static final String UPDATE_PRICE = "PRICE";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to add a new ingredient to the system<br>
     * Followed by the following string format: ingredientName,number,minThreshold,price,category*/
    public static final String ADD_INGREDIENT = "ADD_ING";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to remove an ingredient from the system<br>
     * Followed by the following string format: ingredientName*/
    public static final String REMOVE_INGREDIENT = "REM_ING";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to add a new category to the system<br>
     * Followed by the following string format: categoryName,orderNum*/
    public static final String ADD_CATEGORY = "ADD_CAT";
    /**<p>[FROM STORE CLIENTS ONLY]</p>
     * Used to remove a category from the system<br>
     * Followed by the following string format: categoryName*/
    public static final String REMOVE_CATEGORY = "REM_CAT";
    /**Used to request all categories that exist in the system*/
    public static final String REQUEST_CATEGORIES = "REQ_CAT";
    /**<p>[FROM JAVA SERVER ONLY]</p>
     * Used to send all categories that exist in the system<br>
     * Followed by the following string format: category1,category2,category2<br>
     * Note that the order of categories is the order in which they are to be displayed*/
    public static final String SENDING_CATEGORIES = "SEND_CAT";
    /**Used to request all ingredients that exist in the system*/
    public static final String REQUEST_INGREDIENTS = "REQ_INGR";
    /**<p>[FROM JAVA SERVER ONLY]</p>
     * Used to send all ingredients that exist in the system<br>
     * Followed by the following string format: ingredientName,num,minThreshold,price,category,ingredientName,num,minThreshold,price,category etc*/
    public static final String SENDING_INGREDIENTS = "SEND_INGR";
}
