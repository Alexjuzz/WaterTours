package di.model.dto.user;

import di.model.entity.telephone.Telephone;
import lombok.Data;

@Data
public class ResponseRegistryUser {
    private Long id;
    private String name;
    private String email;
    private String telephone;
}
