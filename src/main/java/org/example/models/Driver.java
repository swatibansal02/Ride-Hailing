package org.example.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Driver {

    private String id;
    private String name;
    private String contactNumber;
    private Cab cab;

    public void setId(){
        this.id = this.contactNumber;
    }
}
