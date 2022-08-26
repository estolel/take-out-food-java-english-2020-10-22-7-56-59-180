public class CartItem extends Item{
    String promo;
    public CartItem(String id, String name, double price, String promo) {
        super(id, name, price);
        this.promo = promo;
    }
}
