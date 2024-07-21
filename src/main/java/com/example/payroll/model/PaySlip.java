package com.example.payroll.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaySlip {
    @Id
    private String paydate;
    private String empid;
    private String empemail;
    private String emp_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date pay;
    private Double amount;

    public void setPaydate(Date pay) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.paydate = formatter.format(pay);
    }
}
