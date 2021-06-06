package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String content;
    private Long missionId;
    private List<TagRequest> tags;

    public Post toEntity(Member member) {
        List<Tag> tagEntities = tags.stream()
                .map(TagRequest::toEntity)
                .collect(Collectors.toList());

        return new Post(member, title, content, missionId, tagEntities);
    }
}