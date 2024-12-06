package pb.studyconnect.server.unit.stub;


import pb.studyconnect.server.api.dto.request.StudentRequest;
import pb.studyconnect.server.api.dto.response.StudentResponse;

import java.util.List;

public class StudentStub {

    public static StudentRequest getBaseStudentRequest() {
        return new StudentRequest(
                "Абоба Сигмович",
                "aboba@gmail.ru",
                "@sigma",
                List.of("CDM-16", "LLM", "OS"),
                List.of("Assembler", "vim", "linux"),
                "кафедра систем информатики",
                "Генерация аssembler кода с использованием GPT"
        );
    }

    public static StudentResponse getBaseStudentResponse() {
        return new StudentResponse(
                null,
                "Абоба Сигмович",
                "aboba@gmail.ru",
                "@sigma",
                List.of("CDM-16", "LLM", "OS"),
                List.of("Assembler", "vim", "linux"),
                "кафедра систем информатики",
                "Генерация аssembler кода с использованием GPT"
        );
    }
}
