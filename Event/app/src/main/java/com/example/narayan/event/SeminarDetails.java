package com.example.narayan.event;

class SeminarDetails {

    private String SemiName;
    //private String LastName;
    //private String EmailId;
    //private String Password;
    private String SemiDate;


   /* public void setCoordId(String coordId) {
        CoordId = coordId;
    }*/

    public void setSemiName(String SemiName) {
        SemiName = SemiName;
    }

    /*public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId.replace(".",",");
    }*/

    /*public void setPassword(String password) {
        Password = password;
    }
    */
    public void setSemiDate(String SemiDate) {
        SemiDate = SemiDate;
    }



   /* public String getCoordId() {
        return CoordId;
    }*/

    public String getSemiName() {
        return SemiName;
    }

    /*public String getLastName() {
        return LastName;
    }

    public String getEmailId() {
        return EmailId.replace(",",".");
    }*/
    /*
    public String getPassword() {
        return Password;
    }
    */
    public String getSemiDate() {
        return SemiDate;
    }
}

