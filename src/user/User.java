package user;

import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
public class User {
    private String first_name;
    private String last_name;
    private String birth_date;
    private int age;
    private String gender;
    private String gender_preference;
    private int height;
    private String mobile_number;
    private String email;
    private String city ;
    private String state ;
    private String qualification;
    private String dietary_choice;
    private String bio;
    private InputStream image_stream;
    private String username;
    private String password;

    public User(){}
    public User(String first_name, String last_name, String birth_date, int age,
                String gender, String gender_preference, int height,
                String mobile_number, String email, String city, String state,
                String qualification, String dietary_choice, String bio,
                InputStream image_stream, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.age = age;
        this.gender = gender;
        this.gender_preference = gender_preference;
        this.height = height;
        this.mobile_number = mobile_number;
        this.email = email;
        this.city = city;
        this.state = state;
        this.qualification = qualification;
        this.dietary_choice = dietary_choice;
        this.bio = bio;
        this.image_stream = image_stream;
        this.username = username;
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender_preference() {
        return gender_preference;
    }

    public void setGender_preference(String gender_preference) {
        this.gender_preference = gender_preference;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDietary_choice() {
        return dietary_choice;
    }

    public void setDietary_choice(String dietary_choice) {
        this.dietary_choice = dietary_choice;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public InputStream getImage_stream() {
        return image_stream;
    }

    public void setImage_stream(InputStream image_stream) {
        this.image_stream = image_stream;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public static int getUserAge(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        LocalDate currentDate = LocalDate.now();

        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

}
