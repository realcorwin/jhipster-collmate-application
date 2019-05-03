package dik.jhipster.collmate.repository;

import dik.jhipster.collmate.domain.Dvd;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Dvd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DvdRepository extends MongoRepository<Dvd, String> {

}
