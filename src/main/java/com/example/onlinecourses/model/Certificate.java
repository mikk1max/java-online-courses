package com.example.onlinecourses.model;

import lombok.*;

@Setter @Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private int CertificateId;
    private String CourseTitle;
    private String IssueDate;
    private int grade;
    private String CertificateType;

    public Certificate(Certificate certificate) {
        this.CertificateId = certificate.getCertificateId();
        this.CourseTitle = certificate.getCourseTitle();
        this.IssueDate = certificate.getIssueDate();
        this.grade = certificate.getGrade();
        this.CertificateType = certificate.getCertificateType();
    }

    public void resetStateCertificate(Certificate certificate) {
        certificate.setCertificateId(this.CertificateId);
        certificate.setCourseTitle(this.CourseTitle);
        certificate.setIssueDate(this.IssueDate);
        certificate.setGrade(this.grade);
        certificate.setCertificateType(this.CertificateType);
    }
}
