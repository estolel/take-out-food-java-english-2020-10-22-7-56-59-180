import java.util.List;

public class SalesPromoService implements SalesPromotionRepository {
    @Override
    public List<SalesPromotion> findAll() {
        return Constants.ALL_SALES_PROMOTIONS;
    }
}
