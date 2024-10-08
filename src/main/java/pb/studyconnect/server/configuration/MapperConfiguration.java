package pb.studyconnect.server.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pb.studyconnect.server.util.DiplomaTopicMapper;
import pb.studyconnect.server.util.MentorMapper;
import pb.studyconnect.server.util.StudentMapper;

@Configuration
public class MapperConfiguration {

    @Bean
    public MentorMapper mentorMapper() {
        return Mappers.getMapper(MentorMapper.class);
    }

    @Bean
    public StudentMapper studentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }

    @Bean
    public DiplomaTopicMapper diplomaTopicMapper() {
        return Mappers.getMapper(DiplomaTopicMapper.class);
    }
}
