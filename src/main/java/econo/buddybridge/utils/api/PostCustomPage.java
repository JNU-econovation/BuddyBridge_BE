package econo.buddybridge.utils.api;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostCustomPage<T> {
    private List<T> content;
    private boolean last;
    private long totalElements;

    public PostCustomPage(List<T> content, boolean last, long totalElements){
        this.content = content;
        this.last = last;
        this.totalElements = totalElements;
    }
}
