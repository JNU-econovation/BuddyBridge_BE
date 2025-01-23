package econo.buddybridge.comment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import econo.buddybridge.comment.dto.CommentReqDto;
import econo.buddybridge.comment.exception.CommentErrorCode;
import econo.buddybridge.comment.exception.CommentNotAllowedFinishedPostException;
import econo.buddybridge.comment.exception.CommentSameGenderOnlyException;
import econo.buddybridge.comment.exception.CommentSelfNotAllowedException;
import econo.buddybridge.matching.entity.Matching;
import econo.buddybridge.matching.entity.MatchingStatus;
import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Gender;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.Role;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CommentService 테스트")
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MatchingRepository matchingRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member taker;
    private Member giver;

    @BeforeEach
    void setUp() {
        taker = createRandomMember();
        giver = createRandomMember();

        memberRepository.saveAll(List.of(taker, giver));
        matchingRepository.flush();
    }

    @Test
    @DisplayName("댓글 작성 - 모집완료된 게시글은 댓글을 작성할 수 없다")
    void commentNotAllowedFinishedPost() {
        //given
        Post post = createPost(taker);
        postRepository.save(post);

        Matching pendingMatching = createPendingMatching(post);
        Matching doneMatching = createDoneMatching(post);
        matchingRepository.saveAll(List.of(pendingMatching, doneMatching));

        Long postId = post.getId();
        Long commentAuthorId = giver.getId();

        CommentReqDto commentReqDto = new CommentReqDto("댓글 내용");

        //when, then
        assertThatThrownBy(() -> commentService.createComment(commentReqDto, postId, commentAuthorId))
                .isInstanceOf(CommentNotAllowedFinishedPostException.class)
                .hasMessage(CommentErrorCode.COMMENT_NOT_ALLOWED_FINISHED_POST.getMessage());
    }

    @Test
    @DisplayName("댓글 작성 - 본인의 게시글에 댓글을 작성할 수 없다")
    void commentNotAllowedSelfComment() {
        //given
        Post post = createPost(taker);
        postRepository.save(post);

        Long postId = post.getId();
        Long commentAuthorId = taker.getId();

        //when
        CommentReqDto commentReqDto = new CommentReqDto("댓글 내용");

        //then
        assertThatThrownBy(() -> commentService.createComment(commentReqDto, postId, commentAuthorId))
                .isInstanceOf(CommentSelfNotAllowedException.class)
                .hasMessage(CommentErrorCode.COMMENT_SELF_NOT_ALLOWED.getMessage());
    }

    @Test
    @DisplayName("댓글 작성 - 이성간 댓글을 작성할 수 없다")
    void commentNotAllowedDifferentGender() {
        //given
        Member maleMember = createMaleMember();
        Member femaleMember = createFemaleMember();
        memberRepository.saveAll(List.of(maleMember, femaleMember));

        Post post = createPost(maleMember);
        postRepository.save(post);

        Long postId = post.getId();
        Long commentAuthorId = femaleMember.getId();

        //when
        CommentReqDto commentReqDto = new CommentReqDto("댓글 내용");

        //then
        assertThatThrownBy(() -> commentService.createComment(commentReqDto, postId, commentAuthorId))
                .isInstanceOf(CommentSameGenderOnlyException.class)
                .hasMessage(CommentErrorCode.COMMENT_SAME_GENDER_ONLY.getMessage());
    }

    private Matching createPendingMatching(Post post) {
        return Matching.builder()
                .taker(taker)
                .giver(giver)
                .post(post)
                .matchingStatus(MatchingStatus.PENDING)
                .build();
    }

    private Matching createDoneMatching(Post post) {
        return Matching.builder()
                .taker(taker)
                .giver(giver)
                .post(post)
                .matchingStatus(MatchingStatus.VOLUNTEERING_COMPLETED)
                .build();
    }

    private Member createMaleMember() {
        return Member.builder()
                .gender(Gender.남성)
                .role(Role.USER)
                .build();
    }

    private Member createFemaleMember() {
        return Member.builder()
                .gender(Gender.여성)
                .role(Role.USER)
                .build();
    }

    private Member createRandomMember() {
        return Member.builder()
                .email("test@email.com")
                .role(Role.USER)
                .gender(Gender.남성)
                .build();
    }

    private Post createPost(Member author) {
        return Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .author(author)
                .district(District.남구)
                .disabilityType(DisabilityType.시각장애)
                .gender(Gender.남성)
                .build();
    }
}
