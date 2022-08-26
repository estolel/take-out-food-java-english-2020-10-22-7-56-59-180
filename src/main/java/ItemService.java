import java.util.List;

public class ItemService implements ItemRepository {

    @Override
    public List<Item> findAll() {
        return Constants.ALL_ITEMS;
    }
}
