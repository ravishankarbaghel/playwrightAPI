package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenData {

    private String username;
    private String password;
    private String token;
}
