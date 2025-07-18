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
    private String dietary_choice;
    private int age;
    private String height;

    public User(String first_name, String last_name, String birth_date,
                String dietary_choice, Blob profile_picture, String bio,
                String mobile_number, String email, String user_name,
                String password, String gender, String food_preference, String height) {
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
        this.dietary_choice = dietary_choice;
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
        return dietary_choice;
    }

    public void setFood_preference(String food_preference) {
        this.dietary_choice = food_preference;
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

    static int getUserAge(String bd)
    {
        int year=Integer.parseInt(bd.substring(0,4));
        int month=Integer.parseInt(bd.substring(5,7));
        int date=Integer.parseInt(bd.substring(8,10));
        if(year>2004||year<1925)
        {
            return -1;
        } else if (month>12||month<1) {
            return -1;
        }
        int possible_date=-1;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                possible_date=31;
                break;
            case 2:
                if((year%4==0&&year%100!=0)||(year%400==0))
                {
                    possible_date=29;
                }
                else {
                    possible_date=28;
                }
                break;
            default:
                possible_date=30;
                break;
        }
        if(date<1||date>possible_date)
        {
            return -1;
        }
        int fetched_current_year=2025;//Will be fetched from db later
        int fetched_current_month=7;
        int fetched_current_date=16;
        int age=-1;
        if(fetched_current_month-month==0)
        {
            if(fetched_current_date-date<0)
            {
                age=fetched_current_year-year-1;
            }
            else
            {
                age=fetched_current_year-year;
            }
        }
        else if(fetched_current_month-month<0)
        {
            age=fetched_current_year-year-1;
        }
        else
        {
            age=fetched_current_year-year;
        }
        return age;

    }

}
