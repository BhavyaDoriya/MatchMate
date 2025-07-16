package user;

public class UserManager {
    int getUserAge(int year,int month,int date)
    {

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
    boolean verifyBirthDate(User u)
    {
        String bd=u.getBirth_date();
        if(bd.length()!=10)
        {
            System.out.println("Please Enter Birthdate in YYYY-MM-DD format!");
            return false;
        }
        else
        {
            if(bd.charAt(4)!='-'||bd.charAt(7)!='-')
            {
                System.out.println("Please Enter Birthdate in YYYY-MM-DD format!");
                return false;
            }
            else
            {
                for(int i=0;i<bd.length();i++)
                {
                    if(Character.isLetter(bd.charAt(i)))
                    {
                        return false;
                    }
                }
                int year=Integer.parseInt(bd.substring(0,4));
                int month=Integer.parseInt(bd.substring(5,7));
                int date=Integer.parseInt(bd.substring(8,10));
                int age=getUserAge(year,month,date);
                if(age!=-1&&age<122)
                {
                    u.setAge(age);
                    return true;
                }
                else
                {
                    System.out.println("Invalid birth-date");
                    return false;
                }
            }
        }
    }
}
