package org.wb.reactive.web.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.wb.reactive.web.domain.enity.Profile;
@Repository
public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
