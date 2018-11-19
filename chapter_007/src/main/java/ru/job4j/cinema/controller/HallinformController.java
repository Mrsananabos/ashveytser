package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import org.json.JSONObject;
import ru.job4j.cinema.model.CinemaSize;
import ru.job4j.cinema.model.Site;
import ru.job4j.cinema.model.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class HallinformController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CinemaSize cinemaSize = ValidateService.getInstance().getCinemaSize();
        String answer =  answer = new JSONObject()
                .put("row", cinemaSize.getRow())
                .put("place", cinemaSize.getPlace()).toString();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(answer);
        writer.flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Collection<Site> result = new ArrayList<>();
        result = ValidateService.getInstance().getAll();
        String json = toString();
        json = gson.toJson(result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(json);
        writer.flush();
    }
}


