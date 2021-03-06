package ru.job4j.carMarket.controller;

import com.google.gson.Gson;
import ru.job4j.carMarket.model.service.Validate;
import ru.job4j.carMarket.model.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarPartsInfoController extends HttpServlet {
    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=windows-1251");
        Gson gson = new Gson();
        List result;
        String json;
        String part = req.getParameter("part");
        if (part.equals("model")) {
            String idMark = req.getParameter("mark");
            result = service.findModelsByMark(idMark);
        } else {
            result = service.findPartsCarByKey(part);
        }
        json = gson.toJson(result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(json);
        writer.flush();
    }

}
