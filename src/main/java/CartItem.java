import java.util.ArrayList;
import java.util.List;

public class CartItem extends Item{
    public void setPossiblePromos(List<SalesPromotion> possiblePromos) {
        this.possiblePromos = possiblePromos;
    }

    List<SalesPromotion> possiblePromos = new ArrayList<>();
    int amount;

    public CartItem(String id, String name, double price, List<SalesPromotion> possiblePromos, int amount) {
        super(id, name, price);
        this.possiblePromos = possiblePromos;
        this.amount = amount;
    }


}
