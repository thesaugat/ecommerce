package com.thesaugat.ecommerce.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String messgae;
@SerializedName("name")
@Expose
private String name;
@SerializedName("email")
@Expose
private String email;
@SerializedName("apiKey")
@Expose
private String apiKey;
@SerializedName("createdAt")
@Expose
private String createdAt;

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessgae() {
return messgae;
}

public void setMessgae(String messgae) {
this.messgae = messgae;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getApiKey() {
return apiKey;
}

public void setApiKey(String apiKey) {
this.apiKey = apiKey;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

}
