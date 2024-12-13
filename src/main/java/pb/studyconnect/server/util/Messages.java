package pb.studyconnect.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Messages {

    public static final String NAME_MUST_NOT_BE_EMPTY = "Name must not be empty";
    public static final String EMAIL_IS_NOT_IN_FORMAT = "Email must be in format: 'nickname@domain'";
    public static final String TG_NICKNAME_IS_NOT_IN_FORMAT = "Telegram nickname must be in format: '@nickname'";
    public static final String TG_NICKNAME_MINIMUM_LENGTH = "Minimum telegram nickname length is 6 characters";
    public static final String SCIENTIFIC_INTERESTS_MINIMUM_LENGTH = "Minimum scientific interests length is 3";
    public static final String DEPARTMENT_MUST_NOT_BE_EMPTY = "Department must not be empty";

    public static final String NOT_FOUND_STUDENT_WITH_ID = "Not found student with id: {0}";
    public static final String NOT_FOUND_MENTOR_WITH_ID = "Not found mentor with id: {0}";
    public static final String NOT_FOUND_MATCHING_WITH_STUDENT_ID_AND_MENTOR_ID = "Not found matching with student id {0} and mentor id {1}";
}
