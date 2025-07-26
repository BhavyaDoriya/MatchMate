package match;
import ds.*;
import util.InputUtils;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class MatchDisplay {
    MatchEngine matchEngine=new MatchEngine();
    public  void displayMatches()
    {
        matchEngine.findMatches();
        Scanner sc=new Scanner(System.in);
        MatchEngine matchEngine=new MatchEngine();
        matchEngine.findMatches();
        CustomLinkedList.Node temp=matchEngine.LL.first;
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
                            System.out.println("BAck to current match");
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
        matchEngine.findMutualLikes();
        CustomLinkedList.Node temp=matchEngine.mutualLikes.first;
        if(temp==null)
        {
            System.out.println("No mutual likes found!");
            return;
        }
        int count=1;
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            System.out.println("Contact information is visible now!");
            System.out.println();
            System.out.println();
            System.out.println("# Profile - "+count);
            System.out.println("Username            : "+temp.data.getUsername());
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
            System.out.println("Phone No.           : "+temp.data.getMobile_number());
            System.out.println("Email               : "+temp.data.getEmail());

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
                        System.out.println("BAck to current match");
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
    public void displayWhoLikedMe()
    {
        matchEngine.findUsersWhoLikedMe();
        CustomLinkedList.Node temp=matchEngine.likedUser.first;
        if(temp==null)
        {
            System.out.println("No likes found!");
            return;
        }
        int count=1;
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            System.out.println("Contact information is only visible when you like user back!");
            System.out.println();
            System.out.println();
            System.out.println("# Profile - "+count);
            System.out.println("Username            : "+temp.data.getUsername());
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

            System.out.println("Do you want to like back this user? (Y/N)");
            String likeBackChoice = sc.nextLine();

            if (likeBackChoice.equalsIgnoreCase("Y")) {
                new LikeManager().LikeUser(temp.data.getUsername());
                System.out.println("You liked back " + temp.data.getUsername());
            } else {
                System.out.println("You skipped liking back.");
            }

            System.out.println();
            System.out.println("[P]revious / [N]ext / [B]ack to Menu");

            String choice = sc.nextLine();


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
                        System.out.println("BAck to current match");
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

    public void displayShortlisted()
    {
        matchEngine.shortListedProfile();
        CustomLinkedList.Node temp=matchEngine.likedByUser.first;
        if(temp==null)
        {
            System.out.println("No mutual likes found!");
            return;
        }
        int count=1;
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            System.out.println("User you liked!");
            System.out.println();
            System.out.println();
            System.out.println("# Profile - "+count);
            System.out.println("Username            : "+temp.data.getUsername());
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
                        System.out.println("BAck to current match");
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