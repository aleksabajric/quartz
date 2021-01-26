package com.demo.quartz.domain.tokendata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenData {

    private Long userId;

    private String name;

    private Long companyId;

    private String email;

    public TokenData(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
