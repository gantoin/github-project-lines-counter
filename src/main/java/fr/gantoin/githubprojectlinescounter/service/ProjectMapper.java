package fr.gantoin.githubprojectlinescounter.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import fr.gantoin.githubprojectlinescounter.domain.Language;
import fr.gantoin.githubprojectlinescounter.domain.Project;
import fr.gantoin.githubprojectlinescounter.support.InvalidURLException;

@Service
public class ProjectMapper {

    public Project mapProjectName(Project project, String url) {
        try {
            project.setUser(url.substring(url.lastIndexOf(".com/") + 5, url.lastIndexOf("/")));
            project.setName(url.substring(url.lastIndexOf("/") + 1));
        } catch (StringIndexOutOfBoundsException exception) {
            throw new InvalidURLException();
        }
        if (project.getName().length() == 0 || project.getUser().length() == 0) {
            throw new InvalidURLException();
        }
        return project;
    }

    public Project map(Project project, String apiResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append(apiResponse);
        sb.delete(0, sb.indexOf("{") + 1);
        sb.delete(sb.lastIndexOf("}"), sb.length());
        String[] strings = sb.toString().split(",");
        Arrays.stream(strings).forEach(s -> {
            Language language = new Language(s.split(":")[0].replace("\"", ""), Integer.parseInt(s.split(":")[1].replace("\"", "")));
            project.getLanguages().add(language);
            project.setTotalLines(project.getTotalLines() + language.getLines());
        });
        return project;
    }
}
