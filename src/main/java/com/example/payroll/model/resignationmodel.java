package com.example.payroll.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "resignationRequests")
public class resignationmodel {
    @Id
    private String id;
    private String empemail;
    private String empname;
    private String manager;
    private String department;
    private Date requestDate;
    private boolean isAccepted;
    private Date acceptedDate;
    private Date date31DaysAfterAcceptance;

}
