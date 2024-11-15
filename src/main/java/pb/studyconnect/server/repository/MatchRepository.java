package pb.studyconnect.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pb.studyconnect.server.model.DiplomaTopic;
import pb.studyconnect.server.model.Match;

import java.util.Optional;

public interface MatchRepository extends MongoRepository<Match, String> {

    Optional<Match> findFirstByMentorIdAndStudentId(String mentorId, String studentId);
}
