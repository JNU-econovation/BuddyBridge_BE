package econo.buddybridge.utils.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String message;
    private String status;
}

