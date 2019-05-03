package dik.jhipster.collmate.repository;

import dik.jhipster.collmate.domain.Cd;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Cd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CdRepository extends MongoRepository<Cd, String> {

}
