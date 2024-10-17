package pb.studyconnect.server.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Study Connect Master",
                description = "Service for match student and mentor",
                version = "1.0.0",
                license = @License(name="PabloBullers Inc.")
        )
)
public class SwaggerConfiguration {
}
