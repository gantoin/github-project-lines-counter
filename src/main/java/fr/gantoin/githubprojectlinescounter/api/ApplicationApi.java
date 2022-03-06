package fr.gantoin.githubprojectlinescounter.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import fr.gantoin.githubprojectlinescounter.domain.Project;
import fr.gantoin.githubprojectlinescounter.service.ProjectMapper;
import fr.gantoin.githubprojectlinescounter.support.InvalidURLException;

@RequiredArgsConstructor
@Controller
public class ApplicationApi {

    private final ProjectMapper projectMapper;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/question")
    public String question(Model model, @RequestParam("url") String url) throws IOException {
        Project project = new Project();
        project.setUrl(url);
        project = projectMapper.mapProjectName(project, url);
        StringBuilder result = new StringBuilder();
        URL apiURL = new URL("https://api.github.com/repos/" + project.getUser() + "/" + project.getName() + "/languages");
        HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new InvalidURLException();
        }

        project = projectMapper.map(project, result.toString());

        model.addAttribute("list", project.getLanguages());
        model.addAttribute("total", project.getTotalLines());
        return "result";
    }

}
