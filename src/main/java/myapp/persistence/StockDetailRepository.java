package myapp.persistence;

import myapp.StockDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDetailRepository extends CrudRepository<StockDetail, Long> {
    StockDetail findBySymbol(String symbol);
}