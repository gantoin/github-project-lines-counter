package fr.gantoin.githubprojectlinescounter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Language {
    private String name;
    private int lines;
}
