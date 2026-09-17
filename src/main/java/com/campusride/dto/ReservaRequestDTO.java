package com.campusride.dto;

import jakarta.validation.constraints.NotBlank;


public class ReservaRequestDTO {

    @NotBlank(message = "passageiroNome e obrigatorio")
    private String passageiroNome;

    public ReservaRequestDTO() {
    }

    public String getPassageiroNome() {
        return passageiroNome;
    }

    public void setPassageiroNome(String passageiroNome) {
        this.passageiroNome = passageiroNome;
    }
}
