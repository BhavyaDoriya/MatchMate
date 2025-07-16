package user;

import java.sql.Blob;

public class User {
    private String first_name;
    private String last_name;
    private String birth_date;
    private String gender_preference;
    private Blob profile_picture=null;
    private String bio;
    private String mobile_number;
    private String email;
    private String user_name;
    private String password;
    private String gender;
    private String food_preference;
    private int age;
    private String height;

    public User(String first_name, String last_name, String birth_date,
                String gender_preference, Blob profile_picture, String bio,
                String mobile_number, String email, String user_name,
                String password, String gender, String food_preference,
                int age, String height) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.gender_preference = gender_preference;
        this.profile_picture = profile_picture;
        this.bio = bio;
        this.mobile_number = mobile_number;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
        this.gender = gender;
        this.food_preference = food_preference;
        this.age = age;
        this.height = height;
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

    public String getGender_preference() {
        return gender_preference;
    }

    public void setGender_preference(String gender_preference) {
        this.gender_preference = gender_preference;
    }

    public Blob getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(Blob profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFood_preference() {
        return food_preference;
    }

    public void setFood_preference(String food_preference) {
        this.food_preference = food_preference;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
