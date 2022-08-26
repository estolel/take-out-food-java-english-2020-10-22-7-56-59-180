import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemService;
    private SalesPromotionRepository salesPromoService;

    public App(ItemRepository itemService, SalesPromotionRepository salesPromoService) {
        this.itemService = itemService;
        this.salesPromoService = salesPromoService;
    }

    public String bestCharge(List<String> inputs) {
        App app = new App(new ItemService(), new SalesPromoService());
        String receipt = "";
        int promoIndex;

        AtomicReference<Double> cartTotal = new AtomicReference<>(0.0);
        Map cart = getCart(inputs);
        inputs = (List<String>) cart.keySet().stream().collect(Collectors.toList());

        final List<String> finalInputList = generateFinalInputList(inputs);
        final Map finalCart = generateFinalCart(cart, finalInputList);
        final Map promos = getApplicablePromos(finalInputList);
        calculateCurrentCartTotal(cartTotal, finalCart);

        if(promos.size()>0){
            promoIndex = calculateOptimalPromo(cartTotal.get(),
                    Arrays.asList(
                        cartTotal.get()-6.0,
                        calculateHalfPricePromo(finalCart, promos)
                    )).get();
        }else{
            if(cartTotal.get()>=30){
                promoIndex = 0;
            }
        }

        receipt+= Constants.HEADER + "\n";
        Map items = new HashMap();
        finalCart.keySet().stream().forEach(cartItem -> {
            String itemName = itemService.findAll().stream().filter(item -> {
                return cartItem.equals(item.getId());
            }).findFirst().get().getName();
            items.put(itemName, finalCart.get(cartItem));
        });

        System.out.println(cartTotal);

        return receipt;
    }

    private AtomicInteger calculateOptimalPromo(Double currentTotal, List<Double> discountedTotals){
        Double saved = currentTotal;
        AtomicInteger promoId = new AtomicInteger();
        discountedTotals.forEach(discountedTotal -> {
            if(currentTotal-discountedTotal < saved) promoId.set(discountedTotals.indexOf(discountedTotal));
        });
        return promoId;
    }

    private Double calculateHalfPricePromo( Map finalCart, Map promos) {

        AtomicReference<Double> newCartTotal = new AtomicReference<>(0.0);
        finalCart.keySet().stream().forEach(cartItem ->{
            itemService.findAll().stream().forEach(item -> {
                if(item.getId().equals(cartItem)) newCartTotal.updateAndGet(value ->
                        promos.keySet().contains(item.getId())?
                        value + (item.getPrice() * Double.parseDouble((String) finalCart.get(item.getId()))/2) :
                        value + (item.getPrice() * Double.parseDouble((String) finalCart.get(item.getId())))
                );
            });
        });
        return newCartTotal.get();
    }
    private void calculateCurrentCartTotal(AtomicReference<Double> cartTotal, Map finalCart) {
        finalCart.keySet().stream().forEach(cartItem ->{
            itemService.findAll().stream().forEach(item -> {
                if(item.getId().equals(cartItem)) cartTotal.updateAndGet(value ->
                        value + (item.getPrice() * Double.parseDouble((String) finalCart.get(item.getId())))
                );
            });
        });
    }

    private Map getApplicablePromos(List<String> finalInputList) {
        Map promos = new HashMap();
        finalInputList.forEach(input -> {
            salesPromoService.findAll().stream().forEach(promo -> {
                if(promo.getRelatedItems().contains(input)){
                    promos.put(input,promo);
                }
            });
        });
        return promos;
    }

    private static Map getCart(List<String> inputs) {
        Map cart = new HashMap();
        inputs.forEach(input->{
            List<String> splitInput = Arrays.asList(input.split(" x "));
            cart.put(splitInput.get(0),splitInput.get(1));
        });
        return cart;
    }

    private Map generateFinalCart(Map cart, List<String> finalInputList) {
        Map finalCart = new HashMap();
        finalInputList.forEach(input -> {
            finalCart.put(input, cart.get(input));
        });
        return finalCart;
    }

    private List<String> generateFinalInputList(List<String> inputs) {
        return inputs.stream().filter(input -> {
            return itemService.findAll().stream().anyMatch(item -> {
                return item.getId().equals(input);
            });
        }).collect(Collectors.toList());
    }


}