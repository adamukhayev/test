package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class UserDto {

    private Long userId;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Date createDate;

}
