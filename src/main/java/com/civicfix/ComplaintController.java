
package com.civicfix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ComplaintController {

    private final ComplaintRepository complaintRepository;

    public ComplaintController(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @GetMapping("/complaints/new")
    public String showForm() {
        return "complaint-form";
    }

    @PostMapping("/complaints")
    public String submitComplaint(
            @ModelAttribute Complaint complaint,
            RedirectAttributes redirectAttributes) {

        complaint.setStatus("Pending");
        complaintRepository.save(complaint);

        redirectAttributes.addFlashAttribute(
                "message", "Your complaint was submitted successfully!");

        return "redirect:/";
    }
}
