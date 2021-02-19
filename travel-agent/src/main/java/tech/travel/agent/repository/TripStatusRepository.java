package tech.travel.agent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.travel.agent.model.TripStatus;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripStatusRepository extends MongoRepository<TripStatus, UUID> {

    Optional<TripStatus> findByTravelId(UUID id);
}
