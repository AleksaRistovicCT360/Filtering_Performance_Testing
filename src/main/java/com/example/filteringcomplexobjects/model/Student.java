package com.example.filteringcomplexobjects.model;

import com.example.filteringcomplexobjects.enums.SchoolClass;
import lombok.*;

@Getter
@Setter
public class Student {

    private String name;
    private Integer grade;
    private Integer year;
    private SchoolClass schoolClass;

}
