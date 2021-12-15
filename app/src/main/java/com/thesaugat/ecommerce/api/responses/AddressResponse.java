package com.thesaugat.ecommerce.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {

@SerializedName("adresses")
@Expose
private List<Adress> adresses = null;
@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;

public List<Adress> getAdresses() {
return adresses;
}

public void setAdresses(List<Adress> adresses) {
this.adresses = adresses;
}

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}
}
