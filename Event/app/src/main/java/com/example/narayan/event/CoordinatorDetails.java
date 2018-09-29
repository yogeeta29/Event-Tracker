package com.example.narayan.event;

class CoordinatorDetails {
    //private String CoordId;



    private String FirstName;
    private String LastName;
    private String EmailId;
    //private String Password;
    private String DateCreated;


   /* public void setCoordId(String coordId) {
        CoordId = coordId;
    }*/

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId.replace(".",",");
    }

    /*public void setPassword(String password) {
        Password = password;
    }
    */
    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }



   /* public String getCoordId() {
        return CoordId;
    }*/

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmailId() {
        return EmailId.replace(",",".");
    }
    /*
    public String getPassword() {
        return Password;
    }
    */
    public String getDateCreated() {
        return DateCreated;
    }
}


