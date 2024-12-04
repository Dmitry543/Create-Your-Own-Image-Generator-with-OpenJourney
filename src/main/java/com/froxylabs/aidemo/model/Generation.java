package com.froxylabs.aidemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class Generation {

    private Long id;
    private String prompt;
    private String negativePrompt;
    private String image;

}
