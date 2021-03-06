package ru.job4j.crudServlets.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.job4j.crudServlets.model.Client;
import ru.job4j.crudServlets.model.service.Validate;
import ru.job4j.crudServlets.model.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SigninController extends HttpServlet {
    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/enter.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Client client = new Client();
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        String line;
        try (BufferedReader reader = req.getReader()) {
            line = reader.readLine();
            sb.append(line);
            client = mapper.readValue(sb.toString(), Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String login = client.getLogin();
        String password = client.getPassword();
        String answer = null;
        int id = service.isCredential(login, password);
        if (id >= 0)
        {
            String role = service.findRoleByLogin(login);
            session.setAttribute("login", login);
            session.setAttribute("role", role);
            String idStr = String.valueOf(id);
            answer = new JSONObject()
                    .put("role", role)
                    .put("id", idStr).toString();

        } else {

            answer = new JSONObject()
                    .put("role", "error").toString();

        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(answer);
        writer.flush();
    }




}
