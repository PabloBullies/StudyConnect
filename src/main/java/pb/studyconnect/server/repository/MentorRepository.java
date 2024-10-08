package pb.studyconnect.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pb.studyconnect.server.model.Mentor;

@Repository
public interface MentorRepository extends MongoRepository<Mentor, String> {
}
