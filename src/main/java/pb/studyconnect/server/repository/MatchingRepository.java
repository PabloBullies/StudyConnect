package pb.studyconnect.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pb.studyconnect.server.model.Matching;

import java.util.Optional;

public interface MatchingRepository extends MongoRepository<Matching, String> {

    Optional<Matching> findFirstByMentorIdAndStudentId(String mentorId, String studentId);
}
