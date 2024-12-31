package econo.buddybridge.post.mapper;

import econo.buddybridge.member.entity.Member;
import econo.buddybridge.post.dto.AssistanceResDto;
import econo.buddybridge.post.dto.PostAuthorDto;
import econo.buddybridge.post.dto.PostDetailDto;
import econo.buddybridge.post.dto.PostDetailInfoDto;
import econo.buddybridge.post.dto.PostListItemDto;
import econo.buddybridge.post.dto.PostReqDto;
import econo.buddybridge.post.dto.ScheduleDetailResDto;
import econo.buddybridge.post.dto.ScheduleListResDto;
import econo.buddybridge.post.entity.AssistanceTime;
import econo.buddybridge.post.entity.Post;
import econo.buddybridge.post.entity.PostStatus;
import econo.buddybridge.post.entity.Schedule;

public final class PostMapper {

    private PostMapper() {

    }

    // Request : 게시글 생성
    public static Post toEntity(PostReqDto request, Member author) {
        return Post.builder()
                .author(author)
                .title(request.title())
                .assistanceType(request.assistanceType())
                .schedule(Schedule.builder()
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .scheduleType(request.scheduleType())
                        .scheduleDetails(request.scheduleDetails())
                        .build())
                .district(request.district())
                .content(request.content())
                .postType(request.postType())
                .disabilityType(author.getDisabilityType())
                .gender(author.getGender())
                .age(author.getAge())
                .assistanceTime(AssistanceTime.builder()
                        .assistanceStartTime(request.assistanceEndTime())
                        .assistanceEndTime(request.assistanceEndTime())
                        .build())
                .build();
    }

    // Response : 단일 게시글(게시글 상세)
    public static PostDetailDto toPostDetailDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return new PostDetailDto(
                toPostAuthorDto(post),
                toPostDetailInfoDto(post, isLiked, postStatus)
        );
    }

    // Response : 게시글 리스트
    public static PostListItemDto toPostListItemDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return new PostListItemDto(
                post.getId(),
                post.getTitle(),
                post.getDistrict(),
                post.getPostType(),
                postStatus,
                post.getDisabilityType(),
                toAssistanceResDto(post),
                toScheduleListResDto(post),
                isLiked
        );
    }

    private static PostAuthorDto toPostAuthorDto(Post post) {
        return PostAuthorDto.builder()
                .memberId(post.getAuthor().getId())
                .nickname(post.getAuthor().getNickname())
                .profileImageUrl(post.getAuthor().getProfileImageUrl())
                .age(post.getAge())
                .gender(post.getGender())
                .disabilityType(post.getDisabilityType())
                .build();
    }

    private static PostDetailInfoDto toPostDetailInfoDto(Post post, Boolean isLiked, PostStatus postStatus) {
        return PostDetailInfoDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .schedule(toScheduleDetailResDto(post))
                .district(post.getDistrict())
                .content(post.getContent())
                .postType(post.getPostType())
                .createdAt(post.getCreatedAt())
                .assistance(toAssistanceResDto(post))
                .postStatus(postStatus)
                .isLiked(isLiked)
                .build();
    }

    private static AssistanceResDto toAssistanceResDto(Post post) {
        return new AssistanceResDto(
                post.getAssistanceType(),
                post.getAssistanceTime().getAssistanceStartTime(),
                post.getAssistanceTime().getAssistanceEndTime()
        );
    }

    private static ScheduleDetailResDto toScheduleDetailResDto(Post post) {
        return ScheduleDetailResDto.builder()
                .startDate(post.getSchedule().getStartDate())
                .endDate(post.getSchedule().getEndDate())
                .scheduleType(post.getSchedule().getScheduleType())
                .scheduleDetails(post.getSchedule().getScheduleDetails())
                .build();
    }

    private static ScheduleListResDto toScheduleListResDto(Post post) {
        return ScheduleListResDto.builder()
                .startDate(post.getSchedule().getStartDate())
                .endDate(post.getSchedule().getEndDate())
                .scheduleType(post.getSchedule().getScheduleType())
                .build();
    }
}
