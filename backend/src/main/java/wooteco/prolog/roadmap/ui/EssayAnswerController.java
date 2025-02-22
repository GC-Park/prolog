package wooteco.prolog.roadmap.ui;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.roadmap.application.EssayAnswerService;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;

@RestController
@RequestMapping
public class EssayAnswerController {

    private final EssayAnswerService essayAnswerService;
    private final QuizService quizService;

    @Autowired
    public EssayAnswerController(EssayAnswerService essayAnswerService,
                                 QuizService quizService) {
        this.essayAnswerService = essayAnswerService;
        this.quizService = quizService;
    }

    @PostMapping("/essay-answers")
    public ResponseEntity<Long> create(@RequestBody EssayAnswerRequest request,
                                       @AuthMemberPrincipal LoginMember member) {

        return ResponseEntity.ok(essayAnswerService.createEssayAnswer(request, member.getId()));
    }

    @GetMapping("/essay-answers/{essayAnswerId}")
    public ResponseEntity<EssayAnswerResponse> findById(@PathVariable Long essayAnswerId) {
        EssayAnswer essayAnswer = essayAnswerService.getById(essayAnswerId);
        EssayAnswerResponse response = EssayAnswerResponse.of(essayAnswer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/essay-answers/{essayAnswerId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long essayAnswerId,
                                           @AuthMemberPrincipal LoginMember member) {
        essayAnswerService.deleteEssayAnswer(essayAnswerId, member.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> findQuizById(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.findById(quizId));
    }

    @GetMapping("/quizzes/{quizId}/essay-answers")
    public ResponseEntity<List<EssayAnswerResponse>> findAnswersByQuizId(
        @PathVariable Long quizId) {

        List<EssayAnswer> essayAnswers = essayAnswerService.findByQuizId(quizId);
        List<EssayAnswerResponse> responses = essayAnswers.stream().map(EssayAnswerResponse::of)
            .collect(toList());

        return ResponseEntity.ok(responses);
    }

}
