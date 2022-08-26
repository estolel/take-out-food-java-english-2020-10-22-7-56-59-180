import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String HEADER = "============= Order details =============";
    public static final String FOOTER = "===================================";
    public static final String SEPARATOR = "-----------------------------------";
    public static final String PROMO_USED = "Promotion used:";
    public static final String DISCOUNTED_PRICE = "Discounted total:";
    public static final String YUAN = "yuan";
    public static final String ITEM_COUNT = " x ";
    public static final String TOTAL = "Total: ";
    public static final String SAVED = " saving ";


    public static final List<Item> ALL_ITEMS = Arrays.asList(
            new Item("ITEM0001", "Braised chicken", 18.00),
            new Item("ITEM0013", "Chinese hamburger", 6.00),
            new Item("ITEM0022", "Cold noodles", 8.00),
            new Item("ITEM0030", "coca-cola", 2.00)
    );


    public static final List<SalesPromotion> ALL_SALES_PROMOTIONS = Arrays.asList(
            new SalesPromotion("BUY_30_SAVE_6_YUAN", "Deduct 6 yuan when the order reaches 30 yuan", Arrays.asList()),
            new SalesPromotion("50%_DISCOUNT_ON_SPECIFIED_ITEMS", "Half price for certain dishes", Arrays.asList(
                    "ITEM0001", "ITEM0022"
            ))
    );
}

