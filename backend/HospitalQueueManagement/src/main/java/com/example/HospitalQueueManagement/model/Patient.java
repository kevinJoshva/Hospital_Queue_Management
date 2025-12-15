    package com.example.HospitalQueueManagement.model;
    
    
    import jakarta.persistence.*;
    
    @Entity
    @Table(name="patient_details")
    public class Patient {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @OneToOne
        @JoinColumn(name = "user_id")
        private Users users;
    
        private String fullName;
        private String mobile;
        private Integer age;
        private String gender;
        private String address;
        private String emergencyContact;
        private Boolean agreeTerms;
    
        public Long getId() {
            return id;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public Users getUsers() {
            return users;
        }
    
        public void setUsers(Users users) {
            this.users = users;
        }
    
        public String getFullName() {
            return fullName;
        }
    
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    
        public String getMobile() {
            return mobile;
        }
    
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    
        public Integer getAge() {
            return age;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        public String getGender() {
            return gender;
        }
    
        public void setGender(String gender) {
            this.gender = gender;
        }
    
        public String getAddress() {
            return address;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
    
        public String getEmergencyContact() {
            return emergencyContact;
        }
    
        public void setEmergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
        }
    
        public Boolean getAgreeTerms() {
            return agreeTerms;
        }
    
        public void setAgreeTerms(Boolean agreeTerms) {
            this.agreeTerms = agreeTerms;
        }
    }
