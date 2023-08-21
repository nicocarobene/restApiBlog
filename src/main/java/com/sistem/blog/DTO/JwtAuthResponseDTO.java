package com.sistem.blog.DTO;

public class JwtAuthResponseDTO {
    private String AccessToken;
    private String tipeOfToken= "Bearer";


    public JwtAuthResponseDTO(String accessToken) {
        AccessToken = accessToken;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getTipeOfToken() {
        return tipeOfToken;
    }

    public void setTipeOfToken(String tipeOfToken) {
        this.tipeOfToken = tipeOfToken;
    }
}
