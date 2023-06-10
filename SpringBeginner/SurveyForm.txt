package com.example.demo.app.survey;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SurveyForm{

    @Min(0)
    @Max(150)
    private int age;
    
    @Min(1)
    @Max(5)
    private int satisfaction;

	@NotNull
	@Size(min = 1, max = 200, message="Please input 200 characters or less")
    private String comment;

}