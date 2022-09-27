package org.example.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rider {

    private String id;
    private String name;
    private String contactNumber;
    private Location location;

    public void setId(){
        this.id = this.contactNumber;
    }
}
