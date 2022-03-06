package fr.gantoin.githubprojectlinescounter.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Project {
    private String user;
    private String name;
    private String url;
    private List<Language> languages = new ArrayList<>();
    private int totalLines = 0;
}
