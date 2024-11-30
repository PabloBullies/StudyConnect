package pb.studyconnect.server.unit.stub;


import pb.studyconnect.server.api.dto.request.StudentRequest;

import java.util.List;

public class StudentRequestStub {

    public static StudentRequest getBaseStudent() {
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
}
