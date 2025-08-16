package match;
import ExceptionHandling.GoBackException;
import ExceptionHandling.RegistrationCancelledException;
import ds.*;
import org.w3c.dom.Node;
import util.InputUtils;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class MatchDisplay {
    MatchEngine matchEngine=new MatchEngine();
    public  void displayMatches() throws GoBackException
    {
        String choiceFilter= InputUtils.promptUntilValid("Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:",input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
        CustomLinkedList.Node temp=null;
        if(choiceFilter.equalsIgnoreCase("NoFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
        {
            matchEngine.findMatches();
            temp=matchEngine.LL.first;
        }
        else if(choiceFilter.equalsIgnoreCase("Age"))
        {

                int startAgeOfRange=takeAgeRangeInput();
                matchEngine.findMatchesByAge(startAgeOfRange);
                temp=matchEngine.matchesByAge.first;

        }
        else if(choiceFilter.equalsIgnoreCase("City"))
        {
                String city = InputUtils.promptUntilValid(
                        "Enter city: ",
                        s -> !s.isEmpty(),
                        () -> new GoBackException("User chose to go back!")
                );
                matchEngine.findMatchesByCity(city);
                temp=matchEngine.matchesByCity.first;
        }
        else
        {
                int startAgeOfRange=takeAgeRangeInput();
                String city = InputUtils.promptUntilValid(
                        "Enter city: ",
                        s -> !s.isEmpty(),
                        () -> new GoBackException("User chose to go back!")
                );
                matchEngine.findMatchesByCityANDAge(startAgeOfRange,city);
                temp=matchEngine.matchesByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println("No matches found!");
        }
        else
        {
                while (true)
                {
                    System.out.println("Contact information is only visible when user likes you back!");
                    System.out.println();
                    System.out.println();
                    System.out.println("# Profile - "+count);
                    System.out.println("Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name());
                    System.out.println("Birth Date          : "+temp.data.getBirth_date());
                    System.out.println("Age                 : "+temp.data.getAge()+" years");
                    System.out.println("Height              : "+temp.data.getHeight()+" cm");
                    System.out.println("Gender              : "+temp.data.getGender());
                    System.out.println("Gender Preferences  : "+temp.data.getGender_preference());
                    System.out.println("Dietary Preferences : "+temp.data.getDietary_choice());
                    System.out.println("City                : "+temp.data.getCity());
                    System.out.println("State               : "+temp.data.getState());
                    System.out.println("Bio                 : "+temp.data.getBio());
                    System.out.println();

                    while(true)
                    {
                        System.out.println("Press [O]pen Profile Picture/[S]kip");
                        String choice=sc.nextLine();
                        if(choice.equalsIgnoreCase("O"))
                        {
                            InputStream fis=temp.data.getImage_stream();
                            if(fis==null)
                            {
                                System.out.println("No image provided by user");
                                break;
                            }
                            else
                            {
                                File dir = new File("C://profile_images");
                                if (!dir.exists()) {
                                    dir.mkdirs(); // create the folder if it doesn't exist
                                }

                                File imageFile=new File(dir,temp.data.getUsername()+".jpg");
                                try
                                {
                                    FileOutputStream fos=new FileOutputStream(imageFile);
                                    byte buffer[]=new byte[1024];
                                    int bytesRead=fis.read(buffer);
                                    while(bytesRead!=-1)
                                    {
                                        fos.write(buffer,0,bytesRead);
                                        bytesRead=fis.read(buffer);
                                    }
                                    Desktop.getDesktop().open(imageFile);
                                    break;
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(choice.equalsIgnoreCase("S"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Enter valid input!");
                        }
                    }

                    System.out.println();

                    System.out.println("[P]revious/[N]ext/[B]ack/[L]ike Profile");

                    String choice=sc.nextLine();

                    if(choice.equalsIgnoreCase("P"))
                    {
                        if(temp.prev==null)
                        {
                            System.out.println("No Previous User Data!");
                            System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                            String choice1= sc.nextLine();
                            if(choice1.equalsIgnoreCase("B"))
                            {
                                break;
                            }
                            else
                            {
                                System.out.println("BAck to current match");
                            }
                        }
                        else{
                            temp=temp.prev;
                            count--;
                        }

                    } else if (choice.equalsIgnoreCase("N")) {
                        if(temp.next==null)
                        {
                            System.out.println("No Next User Data!");
                            System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                            String choice1= sc.nextLine();
                            if(choice1.equalsIgnoreCase("B"))
                            {
                                break;
                            }
                            else
                            {
                                System.out.println("Back to current match");
                            }
                        }
                        else{
                            temp=temp.next;
                            count++;
                        }
                    }
                    else if(choice.equalsIgnoreCase("B"))
                    {
                        break;
                    }
                    else if(choice.equalsIgnoreCase("L"))
                    {
                        new LikeManager().LikeUser(temp.data.getUsername());
                    }
                    else
                    {
                        System.out.println("Enter valid input!");
                    }
                }
        }

    }
    public void displayMutualLikes()
    {
        String choiceFilter= InputUtils.promptUntilValid("Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:",input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
        CustomLinkedList.Node temp=null;
        if(choiceFilter.equalsIgnoreCase("NoFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
        {
            matchEngine.findMutualLikes();
            temp=matchEngine.mutualLikes.first;
        }
        else if(choiceFilter.equalsIgnoreCase("Age"))
        {
            int startAgeOfRange=takeAgeRangeInput();
            matchEngine.findMutualLikesSortedByAge(startAgeOfRange);
            temp=matchEngine.mutualLikesSortedByAge.first;

        }
        else if(choiceFilter.equalsIgnoreCase("City"))
        {
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.findMutualLikesSortedByCity(city);
            temp=matchEngine.mutualLikesSortedByCity.first;
        }
        else
        {
            int startAgeOfRange=takeAgeRangeInput();
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.findMatchesByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.mutualLikesSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println("No mutual likes found!");
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println("List of Mutual Likes :");
        else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println("List of Mutual Likes sorted by"+choiceFilter+":");
        else
            System.out.println("List of Mutual Likes sorted by Age And City:");
            while (true)
            {
                System.out.println("Contact information is only visible when user likes you back!");
                System.out.println();
                System.out.println();
                System.out.println("# Profile - "+count);
                System.out.println("Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name());
                System.out.println("Birth Date          : "+temp.data.getBirth_date());
                System.out.println("Age                 : "+temp.data.getAge()+" years");
                System.out.println("Height              : "+temp.data.getHeight()+" cm");
                System.out.println("Gender              : "+temp.data.getGender());
                System.out.println("Gender Preferences  : "+temp.data.getGender_preference());
                System.out.println("Dietary Preferences : "+temp.data.getDietary_choice());
                System.out.println("City                : "+temp.data.getCity());
                System.out.println("State               : "+temp.data.getState());
                System.out.println("Bio                 : "+temp.data.getBio());
                System.out.println();

                while(true)
                {
                    System.out.println("Press [O]pen Profile Picture/[S]kip");
                    String choice=sc.nextLine();
                    if(choice.equalsIgnoreCase("O"))
                    {
                        InputStream fis=temp.data.getImage_stream();
                        if(fis==null)
                        {
                            System.out.println("No image provided by user");
                            break;
                        }
                        else
                        {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs(); // create the folder if it doesn't exist
                            }

                            File imageFile=new File(dir,temp.data.getUsername()+".jpg");
                            try
                            {
                                FileOutputStream fos=new FileOutputStream(imageFile);
                                byte buffer[]=new byte[1024];
                                int bytesRead=fis.read(buffer);
                                while(bytesRead!=-1)
                                {
                                    fos.write(buffer,0,bytesRead);
                                    bytesRead=fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(choice.equalsIgnoreCase("S"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Enter valid input!");
                    }
                }

                System.out.println();

                System.out.println("[P]revious/[N]ext/[B]ack");

                String choice=sc.nextLine();

                if(choice.equalsIgnoreCase("P"))
                {
                    if(temp.prev==null)
                    {
                        System.out.println("No Previous User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("BAck to current match");
                        }
                    }
                    else{
                        temp=temp.prev;
                        count--;
                    }

                } else if (choice.equalsIgnoreCase("N")) {
                    if(temp.next==null)
                    {
                        System.out.println("No Next User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Back to current match");
                        }
                    }
                    else{
                        temp=temp.next;
                        count++;
                    }
                }
                else if(choice.equalsIgnoreCase("B"))
                {
                    break;
                }
                else
                {
                    System.out.println("Enter valid input!");
                }
            }
        }
    }
    public void displayWhoLikedMe()
    {
        String choiceFilter= InputUtils.promptUntilValid("Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:",input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
        CustomLinkedList.Node temp=null;
        if(choiceFilter.equalsIgnoreCase("NoFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
        {
            matchEngine.findUsersWhoLikedMe();
            temp=matchEngine.likedUser.first;
        }
        else if(choiceFilter.equalsIgnoreCase("Age"))
        {

            int startAgeOfRange=takeAgeRangeInput();
            matchEngine.findUsersWhoLikedMeSortedByAge(startAgeOfRange);
            temp=matchEngine.likedUserSortedByAge.first;

        }
        else if(choiceFilter.equalsIgnoreCase("City"))
        {
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.findUsersWhoLikedMeSortedByCity(city);
            temp=matchEngine.likedUserSortedByCity.first;
        }
        else
        {
            int startAgeOfRange=takeAgeRangeInput();
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.findUsersWhoLikedMeSortedByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.likedUserSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println("No likes found!");
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println("List of User Who Liked you :");
            else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println("List of User Who Liked you sorted by"+choiceFilter+":");
            else
            System.out.println("List of User Who Liked you sorted by Age And City:");
            while (true)
            {
                System.out.println("Contact information is only visible when user likes you back!");
                System.out.println();
                System.out.println();
                System.out.println("# Profile - "+count);
                System.out.println("Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name());
                System.out.println("Birth Date          : "+temp.data.getBirth_date());
                System.out.println("Age                 : "+temp.data.getAge()+" years");
                System.out.println("Height              : "+temp.data.getHeight()+" cm");
                System.out.println("Gender              : "+temp.data.getGender());
                System.out.println("Gender Preferences  : "+temp.data.getGender_preference());
                System.out.println("Dietary Preferences : "+temp.data.getDietary_choice());
                System.out.println("City                : "+temp.data.getCity());
                System.out.println("State               : "+temp.data.getState());
                System.out.println("Bio                 : "+temp.data.getBio());
                System.out.println();

                while(true)
                {
                    System.out.println("Press [O]pen Profile Picture/[S]kip");
                    String choice=sc.nextLine();
                    if(choice.equalsIgnoreCase("O"))
                    {
                        InputStream fis=temp.data.getImage_stream();
                        if(fis==null)
                        {
                            System.out.println("No image provided by user");
                            break;
                        }
                        else
                        {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs(); // create the folder if it doesn't exist
                            }

                            File imageFile=new File(dir,temp.data.getUsername()+".jpg");
                            try
                            {
                                FileOutputStream fos=new FileOutputStream(imageFile);
                                byte buffer[]=new byte[1024];
                                int bytesRead=fis.read(buffer);
                                while(bytesRead!=-1)
                                {
                                    fos.write(buffer,0,bytesRead);
                                    bytesRead=fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(choice.equalsIgnoreCase("S"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Enter valid input!");
                    }
                }

                System.out.println();

                System.out.println("[P]revious/[N]ext/[B]ack/[L]ike Profile");

                String choice=sc.nextLine();

                if(choice.equalsIgnoreCase("P"))
                {
                    if(temp.prev==null)
                    {
                        System.out.println("No Previous User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("BAck to current match");
                        }
                    }
                    else{
                        temp=temp.prev;
                        count--;
                    }

                } else if (choice.equalsIgnoreCase("N")) {
                    if(temp.next==null)
                    {
                        System.out.println("No Next User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Back to current match");
                        }
                    }
                    else{
                        temp=temp.next;
                        count++;
                    }
                }
                else if(choice.equalsIgnoreCase("B"))
                {
                    break;
                }
                else if(choice.equalsIgnoreCase("L"))
                {
                    new LikeManager().LikeUser(temp.data.getUsername());
                }
                else
                {
                    System.out.println("Enter valid input!");
                }
            }
        }
    }

    public void displayShortlisted()
    {
        String choiceFilter= InputUtils.promptUntilValid("Would you like to filter by [Age/City] or [Both]? or [No Filter]:",input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
        CustomLinkedList.Node temp=null;
        if(choiceFilter.equalsIgnoreCase("NoFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
        {
            matchEngine.shortListedProfile();
            temp=matchEngine.likedByUser.first;
        }
        else if(choiceFilter.equalsIgnoreCase("Age"))
        {

            int startAgeOfRange=takeAgeRangeInput();
            matchEngine.shortListedProfileSortedByAge(startAgeOfRange);
            temp=matchEngine.likedByUserSortedByAge.first;

        }
        else if(choiceFilter.equalsIgnoreCase("City"))
        {
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.shortListedProfileSortedByCity(city);
            temp=matchEngine.likedByUserSortedByCity.first;
        }
        else
        {
            int startAgeOfRange=takeAgeRangeInput();
            String city = InputUtils.promptUntilValid(
                    "Enter city: ",
                    s -> !s.isEmpty(),
                    () -> new GoBackException("User chose to go back!")
            );
            matchEngine.shortListedProfileSortedByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.likedByUserSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println("No likes found!");
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println("List of Users Liked By You :");
        else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println("List of Users Liked By You sorted by"+choiceFilter+":");
        else
            System.out.println("List of Users Liked By You sorted by Age And City:");
            while (true)
            {
                System.out.println("Contact information is only visible when user likes you back!");
                System.out.println();
                System.out.println();
                System.out.println("# Profile - "+count);
                System.out.println("Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name());
                System.out.println("Birth Date          : "+temp.data.getBirth_date());
                System.out.println("Age                 : "+temp.data.getAge()+" years");
                System.out.println("Height              : "+temp.data.getHeight()+" cm");
                System.out.println("Gender              : "+temp.data.getGender());
                System.out.println("Gender Preferences  : "+temp.data.getGender_preference());
                System.out.println("Dietary Preferences : "+temp.data.getDietary_choice());
                System.out.println("City                : "+temp.data.getCity());
                System.out.println("State               : "+temp.data.getState());
                System.out.println("Bio                 : "+temp.data.getBio());
                System.out.println();

                while(true)
                {
                    System.out.println("Press [O]pen Profile Picture/[S]kip");
                    String choice=sc.nextLine();
                    if(choice.equalsIgnoreCase("O"))
                    {
                        InputStream fis=temp.data.getImage_stream();
                        if(fis==null)
                        {
                            System.out.println("No image provided by user");
                            break;
                        }
                        else
                        {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs(); // create the folder if it doesn't exist
                            }

                            File imageFile=new File(dir,temp.data.getUsername()+".jpg");
                            try
                            {
                                FileOutputStream fos=new FileOutputStream(imageFile);
                                byte buffer[]=new byte[1024];
                                int bytesRead=fis.read(buffer);
                                while(bytesRead!=-1)
                                {
                                    fos.write(buffer,0,bytesRead);
                                    bytesRead=fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(choice.equalsIgnoreCase("S"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Enter valid input!");
                    }
                }

                System.out.println();

                System.out.println("[P]revious/[N]ext/[B]ack/[L]ike Profile");

                String choice=sc.nextLine();

                if(choice.equalsIgnoreCase("P"))
                {
                    if(temp.prev==null)
                    {
                        System.out.println("No Previous User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("BAck to current match");
                        }
                    }
                    else{
                        temp=temp.prev;
                        count--;
                    }

                } else if (choice.equalsIgnoreCase("N")) {
                    if(temp.next==null)
                    {
                        System.out.println("No Next User Data!");
                        System.out.println("Would you like to exit view matches method[B] or go back to current match profile[Y] ?");
                        String choice1= sc.nextLine();
                        if(choice1.equalsIgnoreCase("B"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Back to current match");
                        }
                    }
                    else{
                        temp=temp.next;
                        count++;
                    }
                }
                else if(choice.equalsIgnoreCase("B"))
                {
                    break;
                }
                else if(choice.equalsIgnoreCase("L"))
                {
                    new LikeManager().LikeUser(temp.data.getUsername());
                }
                else
                {
                    System.out.println("Enter valid input!");
                }
            }
        }
    }
    public int takeAgeRangeInput() throws GoBackException
    {

        int choice= Integer.parseInt(InputUtils.promptUntilValid("Enter age range: \n 1. 21-30 \n 2. 31-40 \n" +
                " 3. 41-50 \n 4. 51-60 \n 5. 61-70 \n " +
                "6. 71-80 \n 7. 81-90 \n 8. 91-100 \n", MatchDisplay::validAgeRange,()->new GoBackException("User chose to Back")));
        int startRange=(choice+1)*10+1;
        return startRange;
    }
     public static boolean validAgeRange(String choice)
    {
        int val=0;
        try {
            val=Integer.parseInt(choice);
            if(val>=1&&val<=8)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
          return false;
        }

    }

}