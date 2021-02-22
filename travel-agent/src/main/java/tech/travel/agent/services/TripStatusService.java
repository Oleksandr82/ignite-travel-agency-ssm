package tech.travel.agent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.travel.agent.model.TripStatus;
import tech.travel.agent.repository.TripStatusRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripStatusService {

    private final TripStatusRepository tripStatusRepository;

    public TripStatus save(TripStatus tripStatus) {
        return tripStatusRepository.save(tripStatus);
    }

    public Optional<TripStatus> findByTravelId(UUID id) {
        return tripStatusRepository.findByTravelId(id);
    }
}
