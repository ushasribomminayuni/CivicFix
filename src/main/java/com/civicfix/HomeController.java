
package com.civicfix;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ComplaintRepository complaintRepository;

    public HomeController(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("complaints", complaintRepository.findAll());
        return "index";
    }

}
