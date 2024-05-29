package econo.buddybridge.post.service;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.member.repository.MemberRepository;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    
    // 검증 과정 필요성 고려
    @Transactional
    public Long createPost(PostReqDto postReqDto) {
        Member author = memberRepository.findById(postReqDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = Post.builder()
                .author(author)
                .title(postReqDto.title())
                .assistanceType(postReqDto.assistanceType())
                .durationPeriod(postReqDto.durationPeriod())
                .scheduleType(postReqDto.scheduleType())
                .scheduleDetail(postReqDto.scheduleDetail())
                .district(postReqDto.district())
                .content(postReqDto.content())
                .postType(postReqDto.postType())
                .build();
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("포스트가 존재하지 않습니다."));
    }

    @Transactional
    public List<Post> getAllPosts(){ // 정렬 생각해두기
        return postRepository.findAll();
    }

}
