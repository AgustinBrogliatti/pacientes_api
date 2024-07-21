package com.consultorioapp.pacientes_api.Dto;

public class ResponseLoginDto {
    private String token;

    public ResponseLoginDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
