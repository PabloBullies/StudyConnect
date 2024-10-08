package pb.studyconnect.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pb.studyconnect.server.model.DiplomaTopic;

@Repository
public interface DiplomaTopicRepository extends MongoRepository<DiplomaTopic, String> {
}
