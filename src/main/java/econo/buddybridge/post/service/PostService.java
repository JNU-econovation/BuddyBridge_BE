package econo.buddybridge.post.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.PostResDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.Schedule;
import econo.buddybridge.post.entity.ScheduleType;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    
    // 검증 과정 필요성 고려
    @Transactional // 포스트 생성
    public Long createPost(PostReqDto postReqDto) {
        Post post = postReqToPost(postReqDto);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true) // 단일 포스트
    public PostResDto findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("포스트가 존재하지 않습니다."));
        return postToPostRes(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResDto> getAllPosts(Pageable pageable){
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostResDto::new);
    }


    @Transactional // 포스트 수정
    public PostResDto updatePost(Long id,PostReqDto postReqDto){
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("포스트가 존재하지 않습니다."));
        Member author = getAuthor(postReqDto);

        post.updatePost(author,postReqDto);

        postRepository.save(post);
        return postToPostRes(post);
    }

    @Transactional // 포스트 삭제
    public void deletePost(Long id){
        if(!postRepository.existsById(id)){
            throw new IllegalArgumentException("포스트가 존재하지 않습니다.");
        }
        postRepository.deleteById(id);
    }

    // Post를 사용하여 PostResDto 생성
    public PostResDto postToPostRes(Post post){
        return PostResDto.builder()
                .id(post.getId())
                .author(post.getAuthor())
                .title(post.getTitle())
                .assistanceType(post.getAssistanceType())
                .startTime(post.getSchedule().getStartTime())
                .endTime(post.getSchedule().getEndTime())
                .scheduleType(post.getSchedule().getScheduleType())
                .scheduleDetails(post.getSchedule().getScheduleDetails())
                .district(post.getDistrict())
                .content(post.getContent())
                .postType(post.getPostType())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .postStatus(post.getPostStatus())
                .build();
    }

    // PostReqDto를 바탕으로 Post생성
    public Post postReqToPost(PostReqDto postReqDto){
        Member author = getAuthor(postReqDto);

        LocalDateTime startTime = postReqDto.startTime();
        LocalDateTime endTime = postReqDto.endTime();
        ScheduleType scheduleType = postReqDto.scheduleType();
        String scheduleDetails = postReqDto.scheduleDetails();

        Schedule schedule = new Schedule(startTime,endTime,scheduleType,scheduleDetails);

        return Post.builder()
                .author(author)
                .title(postReqDto.title())
                .assistanceType(postReqDto.assistanceType())
                .schedule(schedule)
                .district(postReqDto.district())
                .content(postReqDto.content())
                .postType(postReqDto.postType())
                .build();
    }

    // PostReqDto로 들어온 멤버ID로 검색 후 Member객체 반환
    public Member getAuthor(PostReqDto postReqDto){
        return memberRepository.findById(postReqDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
