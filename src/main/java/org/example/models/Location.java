package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Location {

    //shard locations by making regional groups
    private String gridId;//region
    private Double x;
    private Double y;
}
