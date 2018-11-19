package ru.job4j.cinema.controller;
import org.json.JSONObject;
import ru.job4j.cinema.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.model.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HallControlller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean result;
        String answer = null;
        System.out.println("я тут");
        Person person = null;
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        String line;
        try (BufferedReader reader = req.getReader()) {
            line = reader.readLine();
            sb.append(line);
            person = mapper.readValue(sb.toString(), Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("PERSON: " + person);
        result = ValidateService.getInstance().reservePlace(person.getIdPlace(), person.getUsername(), person.getPhone());
        answer = new JSONObject()
                .put("success", result).toString();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        System.out.println("Успешность: " + answer);
        writer.append(answer);
        writer.flush();

    }


}
