
package com.example.alphabook.api.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationResult {

    @SerializedName("Registration")
    @Expose
    private List<Registration> registration;

    public List<Registration> getRegistration() {
        return registration;
    }

    public void setRegistration(List<Registration> registration) {
        this.registration = registration;
    }



}
