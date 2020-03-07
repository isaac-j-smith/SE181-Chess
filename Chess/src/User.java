public class User {

    public String dateOfBirth;
    public String fullName;

    public User(){}

    public User(String dateOfBirth, String fullName) {
        this.dateOfBirth = dateOfBirth;
        this.fullName= fullName;
    }

    public void SetDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void SetFullName(String fullName){
        this.fullName = fullName;
    }

}
