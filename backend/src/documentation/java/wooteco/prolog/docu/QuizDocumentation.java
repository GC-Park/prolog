package wooteco.prolog.docu;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;
import wooteco.prolog.roadmap.ui.QuizController;

@WebMvcTest(controllers = QuizController.class)
public class QuizDocumentation extends NewDocumentation {

    @MockBean
    private QuizService quizService;

    @Test
    void 퀴즈_생성() {
        given(quizService.createQuiz(any(), any())).willReturn(1L);

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(QUIZ_REQUEST)
            .when().post("/sessions/{sessionId}/keywords/{keywordId}/quizs", 1L, 1L)
            .then().log().all().apply(document("quiz/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void Keyword별_Quiz_목록_조회() {
        given(quizService.findQuizzesByKeywordId(any())).willReturn(QUIZZES_RESPONSE);

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/sessions/{sessionId}/keywords/{keywordId}/quizs", 1L, 1L)
            .then().log().all().apply(document("quiz/delete"))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 퀴즈_수정() {
        doNothing().when(quizService).updateQuiz(any(), any());

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(QUIZ_EDIT_REQUEST)
            .when().put("/sessions/{sessionId}/keywords/{keywordId}/quizs/{quizId}", 1L, 1L, 1L)
            .then().log().all().apply(document("quiz/update"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void 퀴즈_삭제() {
        doNothing().when(quizService).deleteQuiz(any());

        given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/sessions/{sessionId}/keywords/{keywordId}/quizs/{quizId}", 1L, 1L, 1L)
            .then().log().all().apply(document("quiz/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }


    private static final QuizRequest QUIZ_REQUEST = new QuizRequest(
        "수달이 루키를 위해 만든 퀴즈"
    );

    private static final QuizRequest QUIZ_EDIT_REQUEST = new QuizRequest(
        "수달이 수정해서 토미를 위해 만든 퀴즈"
    );

    private static final QuizzesResponse QUIZZES_RESPONSE = new QuizzesResponse(1L,
        Arrays.asList(new QuizResponse(1L, "브라운을 위해 낸 퀴즈"), new QuizResponse(1L, "포코를 위해 낸 퀴즈")));

}
