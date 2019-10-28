package com.github.calve.web;

import com.github.calve.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RootController {

    private ExcelService excelService;

    @Autowired
    public RootController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/outgoing/")
    public String getOutgoing() {
        return "outgoing";
    }

    @GetMapping("/requests/")
    public String getRequests() {
        return "requests";
    }

    @GetMapping("/complaints/")
    public String getComplaints() {
        return "complaints";
    }

    @GetMapping("/generics/")
    public String getGenerics(Model model) {
        return "generics";
    }

    @GetMapping("/info/")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/foreigners/")
    public String getForeigners() {
        return "foreigners";
    }

    @GetMapping("/applications/")
    public String getApplications() {
        return "applications";
    }

    @GetMapping("/executors/")
    public String getExecutors() {
        return "executors";
    }

    @GetMapping(value = "/excel/")
    public void getExcelRepresentation(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment; filename=EXCEL_DB.xls");
            response.getOutputStream().write(excelService.getExcelRepresentation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
