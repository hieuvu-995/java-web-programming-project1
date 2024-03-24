package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credentials {
    private int credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userid;
}