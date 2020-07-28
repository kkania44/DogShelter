package pl.kania.shelter.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

    private Integer id;
    private String username;
    private String password;

}
