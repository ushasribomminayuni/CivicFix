
package com.civicfix;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

        private final ComplaintRepository complaintRepository;

        public AdminController(ComplaintRepository complaintRepository) {
                this.complaintRepository = complaintRepository;
        }

        // Admin dashboard with search and filters
        @GetMapping("/admin")
        public String dashboard(
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String locality,
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) String category,
                        Model model) {

                List<Complaint> allComplaints = complaintRepository.findAll();

                Stream<Complaint> complaintStream = allComplaints.stream();

                // Search complaints and resident details
                if (hasText(search)) {
                        String keyword = search.trim().toLowerCase();

                        complaintStream = complaintStream.filter(complaint -> contains(complaint.getId(), keyword)
                                        || contains(complaint.getResidentName(), keyword)
                                        || contains(complaint.getMobileNumber(), keyword)
                                        || contains(complaint.getHouseNumber(), keyword)
                                        || contains(complaint.getStreet(), keyword)
                                        || contains(complaint.getLocality(), keyword)
                                        || contains(complaint.getWard(), keyword)
                                        || contains(complaint.getPinCode(), keyword)
                                        || contains(complaint.getTitle(), keyword)
                                        || contains(complaint.getCategory(), keyword)
                                        || contains(complaint.getDescription(), keyword)
                                        || contains(complaint.getLocation(), keyword)
                                        || contains(complaint.getStatus(), keyword));
                }

                // Filter by area
                if (hasText(locality)) {
                        String area = locality.trim().toLowerCase();

                        complaintStream = complaintStream.filter(complaint -> contains(complaint.getLocality(), area));
                }

                // Filter by status
                if (hasText(status)) {
                        complaintStream = complaintStream
                                        .filter(complaint -> status.equalsIgnoreCase(complaint.getStatus()));
                }

                // Filter by category
                if (hasText(category)) {
                        complaintStream = complaintStream
                                        .filter(complaint -> category.equalsIgnoreCase(complaint.getCategory()));
                }

                List<Complaint> filteredComplaints = complaintStream
                                .collect(Collectors.toList());

                model.addAttribute("complaints", filteredComplaints);
                model.addAttribute("search", search);
                model.addAttribute("locality", locality);
                model.addAttribute("status", status);
                model.addAttribute("category", category);

                // Counts match the currently filtered complaints
                model.addAttribute("totalCount", filteredComplaints.size());
                model.addAttribute("pendingCount",
                                countByStatus(filteredComplaints, "Pending"));
                model.addAttribute("inProgressCount",
                                countByStatus(filteredComplaints, "In Progress"));
                model.addAttribute("resolvedCount",
                                countByStatus(filteredComplaints, "Resolved"));

                return "admin";
        }

        // Update complaint status
        @PostMapping("/admin/update-status/{id}")
        public String updateStatus(
                        @PathVariable Long id,
                        @RequestParam String status,
                        RedirectAttributes redirectAttributes) {

                if (!List.of("Pending", "In Progress", "Resolved")
                                .contains(status)) {
                        redirectAttributes.addFlashAttribute(
                                        "message", "Invalid complaint status!");

                        return "redirect:/admin";
                }

                Complaint complaint = complaintRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid complaint ID"));

                complaint.setStatus(status);
                complaintRepository.saveAndFlush(complaint);

                redirectAttributes.addFlashAttribute(
                                "message", "Complaint status updated successfully!");

                // Reload the dashboard from the database
                return "redirect:/admin";
        }

        // Delete complaint
        @PostMapping("/admin/delete/{id}")
        public String deleteComplaint(
                        @PathVariable Long id,
                        RedirectAttributes redirectAttributes) {

                Complaint complaint = complaintRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid complaint ID"));

                complaintRepository.delete(complaint);
                complaintRepository.flush();

                redirectAttributes.addFlashAttribute(
                                "message", "Complaint deleted successfully!");

                return "redirect:/admin";
        }

        private boolean hasText(String value) {
                return value != null && !value.trim().isEmpty();
        }

        private boolean contains(Object value, String keyword) {
                return value != null
                                && value.toString().toLowerCase().contains(keyword);
        }

        private long countByStatus(
                        List<Complaint> complaints, String status) {

                return complaints.stream()
                                .filter(complaint -> status.equalsIgnoreCase(complaint.getStatus()))
                                .count();
        }
}
