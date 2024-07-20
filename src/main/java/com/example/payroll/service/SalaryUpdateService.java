package com.example.payroll.service;

import com.example.payroll.model.SalaryUpdate;
import com.example.payroll.model.User;
import com.example.payroll.repository.SalaryUpdateRequestRepository;
import com.example.payroll.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryUpdateService {
    @Autowired
    private SalaryUpdateRequestRepository salaryUpdateRequestRepository;

    @Autowired
    private UserRepo userRepository;

    public SalaryUpdate createUpdateRequest(String user_email, Double proposedSalary) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String manager = authentication.getName();
        Optional<User> user = userRepository.findByEmpemailAndManager(user_email,manager);
        SalaryUpdate updateRequest = new SalaryUpdate();
        updateRequest.setId(user.get().getEmp_id());
        updateRequest.setEmpemail(user.get().getEmpemail());
        updateRequest.setEmpname(user.get().getEmp_name());
        updateRequest.setDepartment(user.get().getDepartment());
        updateRequest.setProposedSalary(proposedSalary);
        updateRequest.setApproved(false);
        return salaryUpdateRequestRepository.save(updateRequest);
    }


    public List<SalaryUpdate> getSalaryRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User admin = userRepository.findByEmpemail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found with email: " + email));
        return salaryUpdateRequestRepository.findByDepartmentAndIsApproved(admin.getDepartment(), false);
    }

    public SalaryUpdate processSalaryRequest(String empEmail, boolean isAccepted) {
        SalaryUpdate request = salaryUpdateRequestRepository.findByEmpemail(empEmail)
                .orElseThrow(() -> new RuntimeException("Appraisal request not found"));

        if (request.getEmpemail() == null) {
            throw new IllegalStateException("No ID found for the salary update request. Cannot update.");
        }

        if (isAccepted) {
            return approveAppraisal(request);
        } else {
            return declineAppraisal(request);
        }
    }

    private SalaryUpdate approveAppraisal(SalaryUpdate request) {
        request.setApproved(true);
        updateAssociatedUsers(request);
        return salaryUpdateRequestRepository.save(request);
    }

    private SalaryUpdate declineAppraisal(SalaryUpdate request) {
        salaryUpdateRequestRepository.delete(request);
        return request;
    }


    private void updateAssociatedUsers(SalaryUpdate request) {
        User user = userRepository.findByEmpemail(request.getEmpemail())
                .orElseThrow(() -> new RuntimeException("User not found for resignation"));
        user.setSalary(request.getProposedSalary());
        userRepository.save(user);
    }
}
