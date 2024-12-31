package econo.buddybridge.post.service;

import econo.buddybridge.matching.repository.MatchingRepository;
import econo.buddybridge.member.entity.DisabilityType;
import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.entity.MemberRole;
import econo.buddybridge.member.service.MemberService;
import econo.buddybridge.post.dto.CompletedVolunteerPostPage;
import econo.buddybridge.post.dto.PostCustomPage;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.dto.PostEnumResDto;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostStatus;
import econo.buddybridge.post.dto.PostUpdateReqDto;
import econo.buddybridge.post.entity.AssistanceType;
import econo.buddybridge.post.entity.District;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostType;
import econo.buddybridge.post.event.PostDeleteEvent;
import econo.buddybridge.post.exception.PostDeleteNotAllowedException;
import econo.buddybridge.post.exception.PostNotFoundException;
import econo.buddybridge.post.exception.PostUpdateNotAllowedException;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static econo.buddybridge.post.mapper.PostMapper.toEntity;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final MatchingRepository matchingRepository;
    private final ApplicationEventPublisher publisher;

    // 존재하는 포스트인지 확인
    @Transactional(readOnly = true)
    public Post findPostByIdOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public Post findPostByIdWithAuthorOrThrow(Long postId) {
        return postRepository.findByIdWithAuthor(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true) // 단일 게시글 조회
    public PostDetailDto findPost(Long memberId, Long postId) {
        return postRepository.findByMemberIdAndPostId(memberId, postId);
    }

    @Transactional(readOnly = true) // 내가 작성한 게시글 조회
    public PostCustomPage getPostsMyPage(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return postRepository.findPostsMyPage(memberId, page - 1, size, sort, postType);
    }

    @Transactional(readOnly = true) // 전체 게시글 조회
    public PostCustomPage getPosts(Long memberId, Integer page, Integer size, String sort, PostType postType, PostStatus postStatus,
                                   List<DisabilityType> disabilityType, List<AssistanceType> assistanceType) {
        return postRepository.findPosts(memberId, page - 1, size, sort, postType, postStatus, disabilityType, assistanceType);
    }

    @Transactional(readOnly = true) // 찜한 게시글 조회
    public PostCustomPage getPostsLikes(Long memberId, Integer page, Integer size, String sort, PostType postType) {
        return postRepository.findPostsByLikes(memberId, page - 1, size, sort, postType);
    }

    @Transactional(readOnly = true) // 매칭 상태가 DONE 이후인 봉사 게시글 조회
    public CompletedVolunteerPostPage getCompletedVolunteerPosts(Long memberId, Integer page, Integer size, String sort, MemberRole memberRole, Boolean isCompleted) {
        Member author = memberService.findMemberByIdOrThrow(memberId);
        return matchingRepository.findCompletedVolunteerPosts(author, page - 1, size, sort, memberRole, isCompleted);
    }

    // 검증 과정 필요성 고려
    @Transactional // 게시글 생성
    public Long createPost(PostReqDto postReqDto, Long memberId) {
        Member member = memberService.findMemberByIdOrThrow(memberId);

        Post post = toEntity(postReqDto, member);
        return postRepository.save(post).getId();
    }

    // 게시글 작성을 위한 열거형 타입의 값들을 가져오는 메서드
    public PostEnumResDto getPostEnums() {
        return PostEnumResDto.builder()
                .assistanceTypes(List.of(AssistanceType.values()))
                .disabilityTypes(List.of(DisabilityType.values()))
                .districts(List.of(District.values()))
                .build();
    }

    @Transactional // 게시글 수정
    public Long updatePost(Long postId, PostUpdateReqDto postUpdateReqDto, Long memberId) {
        Post post = findPostByIdWithAuthorOrThrow(postId);
        Member author = memberService.findMemberByIdOrThrow(memberId);

        if (!post.getAuthor().equals(author)) {
            throw PostUpdateNotAllowedException.EXCEPTION;
        }

        post.updatePost(postUpdateReqDto);

        return post.getId();
    }

    @Transactional // 게시글 삭제
    public void deletePost(Long postId, Long memberId) {
        Post post = findPostByIdWithAuthorOrThrow(postId);
        Member author = memberService.findMemberByIdOrThrow(memberId);

        if (!post.getAuthor().equals(author)) {
            throw PostDeleteNotAllowedException.EXCEPTION;
        }

        publisher.publishEvent(PostDeleteEvent.from(post));
    }
}
