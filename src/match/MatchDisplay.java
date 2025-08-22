package match;
import ExceptionHandling.GoBackException;
import ds.*;
import user.Session;
import user.UserManager;
import util.ConsoleColors;
import util.DatabaseConnector;
import util.InputUtils;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class MatchDisplay {
    MatchEngine matchEngine=new MatchEngine();
    public  void displayMatches() throws GoBackException
    {
        String choiceFilter= InputUtils.promptUntilValid(ConsoleColors.YELLOW_BOLD+"Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:"+ConsoleColors.RESET,input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
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
                        () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
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
                        () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
                );
                matchEngine.findMatchesByCityANDAge(startAgeOfRange,city);
                temp=matchEngine.matchesByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println(ConsoleColors.YELLOW+"No matches found!"+ConsoleColors.RESET);
        }
        else
        {
                System.out.println(ConsoleColors.YELLOW+"List of Matches:"+ConsoleColors.RESET);
                System.out.println();
                while (true)
                {
                    System.out.println(ConsoleColors.YELLOW+"NOTE:Contact information is only visible when user likes you back!"+ConsoleColors.RESET);

                    System.out.println();
                    System.out.println(ConsoleColors.GREEN_BOLD+"# Profile - "+count+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Birth Date          : "+temp.data.getBirth_date()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Age                 : "+temp.data.getAge()+" years"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Height              : "+temp.data.getHeight()+" cm"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Gender              : "+temp.data.getGender()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Gender Preferences  : "+temp.data.getGender_preference()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Dietary Preferences : "+temp.data.getDietary_choice()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"City                : "+temp.data.getCity()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"State               : "+temp.data.getState()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Bio                 : "+temp.data.getBio()+ConsoleColors.RESET);
                    double compatibility = calculateCompatibility(Session.getCurrentUsername(), temp.data.getUsername()+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN+"Compatibility Score : " + Math.round(compatibility*100.0)/100.0 + "%"+ConsoleColors.RESET);
                    System.out.println();

                    while(true)
                    {
                        System.out.println(ConsoleColors.YELLOW+"Press [O]pen Profile Picture/[S]kip"+ConsoleColors.RESET);
                        String choice=sc.nextLine();
                        if(choice.equalsIgnoreCase("O"))
                        {
                            InputStream fis=temp.data.getImage_stream();
                            if(fis==null)
                            {
                                System.out.println(ConsoleColors.YELLOW+"No image provided by user"+ConsoleColors.RESET);
                                break;
                            }
                            else
                            {
                                File dir = new File("C://profile_images");
                                if (!dir.exists()) {
                                    dir.mkdirs();
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
                        else if(choice.equalsIgnoreCase("B"))
                        {
                            return;
                        }
                        else
                        {
                            System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                        }
                    }

                    System.out.println();
                    while (true){
                    System.out.println(ConsoleColors.YELLOW+"[P]revious/[N]ext/[B]ack/[L]ike Profile/[U]nlike Profile/[D]ownload profile"+ConsoleColors.RESET);

                    String choice=sc.nextLine();

                    if(choice.equalsIgnoreCase("P"))
                    {
                        if(temp.prev==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Previous User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                                System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                                break;
                        }
                        else{
                            temp=temp.prev;
                            count--;
                            break;
                        }

                    } else if (choice.equalsIgnoreCase("N")) {
                        if(temp.next==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Next User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.next;
                            count++;
                            break;
                        }
                    }
                    else if(choice.equalsIgnoreCase("B"))
                    {
                        return;
                    }
                    else if(choice.equalsIgnoreCase("L"))
                    {
                        new LikeManager().LikeUser(temp.data.getUsername());
                    } else if (choice.equalsIgnoreCase("U")) {
                        new LikeManager().UnlikeUser(temp.data.getUsername());
                        matchEngine.removeUserFromAllLists(temp.data.getUsername());
                    } else if(choice.equalsIgnoreCase("D"))
                    {
                        MatchEngine m1=new MatchEngine();
                        m1.findMutualLikes();
                        if(m1.mutualLikes.contains(temp.data))
                        new UserManager().generateUserProfile(temp.data,true);
                        else
                            new UserManager().generateUserProfile(temp.data,false);
                    }
                    else
                    {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }
            }
        }

    }
    public void displayMutualLikes()
    {
        String choiceFilter= InputUtils.promptUntilValid(ConsoleColors.YELLOW_BOLD+"Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:"+ConsoleColors.RESET,input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
            );
            matchEngine.findMutualLikesSortedByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.mutualLikesSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println(ConsoleColors.YELLOW+"No mutual likes found!"+ConsoleColors.RESET);
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println(ConsoleColors.YELLOW+"List of Mutual Likes :"+ConsoleColors.RESET);
        else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println(ConsoleColors.YELLOW+"List of Mutual Likes sorted by"+choiceFilter+":"+ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.YELLOW+"List of Mutual Likes sorted by Age And City:"+ConsoleColors.RESET);
            System.out.println();
            while (true) {
                System.out.println(ConsoleColors.YELLOW+"Contact information is visible Now!"+ConsoleColors.RESET);
                System.out.println();
                System.out.println(ConsoleColors.GREEN_BOLD+"# Profile - "+count+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Birth Date          : "+temp.data.getBirth_date()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Age                 : "+temp.data.getAge()+" years"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Height              : "+temp.data.getHeight()+" cm"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender              : "+temp.data.getGender()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender Preferences  : "+temp.data.getGender_preference()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Dietary Preferences : "+temp.data.getDietary_choice()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"City                : "+temp.data.getCity()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"State               : "+temp.data.getState()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Bio                 : "+temp.data.getBio()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Contact             : " + temp.data.getMobile_number()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Email               : " + temp.data.getEmail()+ConsoleColors.RESET);
                double compatibility = calculateCompatibility(Session.getCurrentUsername(), temp.data.getUsername());
                System.out.println(ConsoleColors.CYAN+"Compatibility Score : " + Math.round(compatibility * 100.0) / 100.0 + "%"+ConsoleColors.RESET);
                System.out.println();

                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"Press [O]pen Profile Picture/[S]kip"+ConsoleColors.RESET);
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("O")) {
                        InputStream fis = temp.data.getImage_stream();
                        if (fis == null) {
                            System.out.println(ConsoleColors.YELLOW+"No image provided by user"+ConsoleColors.RESET);
                            break;
                        } else {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }

                            File imageFile = new File(dir, temp.data.getUsername() + ".jpg");
                            try {
                                FileOutputStream fos = new FileOutputStream(imageFile);
                                byte buffer[] = new byte[1024];
                                int bytesRead = fis.read(buffer);
                                while (bytesRead != -1) {
                                    fos.write(buffer, 0, bytesRead);
                                    bytesRead = fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (choice.equalsIgnoreCase("S")) {
                        break;
                    } else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }

                System.out.println();
                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"[P]revious/[N]ext/[B]ack/[U]nlike Profile/[D]ownload Profile"+ConsoleColors.RESET);

                    String choice = sc.nextLine();

                    if (choice.equalsIgnoreCase("P")) {
                        if(temp.prev==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Previous User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.YELLOW,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.prev;
                            count--;
                            break;
                        }

                    } else if (choice.equalsIgnoreCase("N")) {
                        if(temp.next==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Next User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.next;
                            count++;
                            break;
                        }
                    } else if (choice.equalsIgnoreCase("D")) {
                        new UserManager().generateUserProfile(temp.data, true);
                    } else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else if (choice.equalsIgnoreCase("U")) {
                        new LikeManager().UnlikeUser(temp.data.getUsername());
                        matchEngine.removeUserFromAllLists(temp.data.getUsername());
                    } else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }
            }
        }
    }
    public void displayWhoLikedMe()
    {
        String choiceFilter= InputUtils.promptUntilValid(ConsoleColors.YELLOW_BOLD+"Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:"+ConsoleColors.RESET,input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
            );
            matchEngine.findUsersWhoLikedMeSortedByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.likedUserSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println(ConsoleColors.YELLOW+"No likes found!"+ConsoleColors.RESET);
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println(ConsoleColors.YELLOW+"List of User Who Liked you :"+ConsoleColors.RESET);
            else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println(ConsoleColors.YELLOW+"List of User Who Liked you sorted by"+choiceFilter+":"+ConsoleColors.RESET);
            else
            System.out.println(ConsoleColors.YELLOW+"List of User Who Liked you sorted by Age And City:"+ConsoleColors.RESET);
            System.out.println();
            while (true) {
                System.out.println(ConsoleColors.YELLOW+"Contact information is only visible when user likes you back!"+ConsoleColors.RESET);
                System.out.println();
                System.out.println(ConsoleColors.GREEN_BOLD+"# Profile - "+count+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Birth Date          : "+temp.data.getBirth_date()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Age                 : "+temp.data.getAge()+" years"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Height              : "+temp.data.getHeight()+" cm"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender              : "+temp.data.getGender()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender Preferences  : "+temp.data.getGender_preference()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Dietary Preferences : "+temp.data.getDietary_choice()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"City                : "+temp.data.getCity()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"State               : "+temp.data.getState()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Bio                 : "+temp.data.getBio()+ConsoleColors.RESET);
                double compatibility = calculateCompatibility(Session.getCurrentUsername(), temp.data.getUsername()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Compatibility Score : " + Math.round(compatibility*100.0)/100.0 + "%"+ConsoleColors.RESET);
                System.out.println();

                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"Press [O]pen Profile Picture/[S]kip"+ConsoleColors.RESET);
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("O")) {
                        InputStream fis = temp.data.getImage_stream();
                        if (fis == null) {
                            System.out.println(ConsoleColors.YELLOW+"No image provided by user"+ConsoleColors.RESET);
                            break;
                        } else {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }

                            File imageFile = new File(dir, temp.data.getUsername() + ".jpg");
                            try {
                                FileOutputStream fos = new FileOutputStream(imageFile);
                                byte buffer[] = new byte[1024];
                                int bytesRead = fis.read(buffer);
                                while (bytesRead != -1) {
                                    fos.write(buffer, 0, bytesRead);
                                    bytesRead = fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (choice.equalsIgnoreCase("S")) {
                        break;
                    } else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }

                System.out.println();
                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"[P]revious/[N]ext/[B]ack/[L]ike Profile/[U]nlike Profile/[D]ownload profile"+ConsoleColors.RESET);

                    String choice = sc.nextLine();

                    if (choice.equalsIgnoreCase("P")) {
                        if(temp.prev==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Previous User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.prev;
                            count--;
                            break;
                        }

                    } else if (choice.equalsIgnoreCase("N")) {
                        if(temp.next==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Next User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.next;
                            count++;
                            break;
                        }
                    } else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else if (choice.equalsIgnoreCase("L")) {
                        new LikeManager().LikeUser(temp.data.getUsername());
                    }else if (choice.equalsIgnoreCase("U")) {
                        new LikeManager().UnlikeUser(temp.data.getUsername());
                        matchEngine.removeUserFromAllLists(temp.data.getUsername());
                    }
                    else if (choice.equalsIgnoreCase("D")) {
                        MatchEngine m1 = new MatchEngine();
                        m1.findMutualLikes();
                        if (m1.mutualLikes.contains(temp.data))
                            new UserManager().generateUserProfile(temp.data, true);
                        else
                            new UserManager().generateUserProfile(temp.data, false);
                    }
                    else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }
            }
        }
    }

    public void displayShortlisted()
    {
        String choiceFilter= InputUtils.promptUntilValid(ConsoleColors.YELLOW_BOLD+"Would you like to filter matches by [Age/City] or [Both]? or [No Filter]:"+ConsoleColors.RESET,input->input.equalsIgnoreCase("Age")||input.equalsIgnoreCase("City")||input.equalsIgnoreCase("Both")||input.equalsIgnoreCase("No Filter")||input.equalsIgnoreCase("Nofilter"),()->new RuntimeException("blah blah blah"));
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
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
                    () -> new GoBackException(ConsoleColors.YELLOW+"User chose to go back!"+ConsoleColors.RESET)
            );
            matchEngine.shortListedProfileSortedByCityANDAge(startAgeOfRange,city);
            temp=matchEngine.likedByUserSortedByCityANDAge.first;
        }
        Scanner sc=new Scanner(System.in);
        int count=1;
        if(temp==null)
        {
            System.out.println(ConsoleColors.YELLOW+"No likes found!"+ConsoleColors.RESET);
            return;
        }
        else
        {   if(choiceFilter.equalsIgnoreCase("NOFilter")||choiceFilter.equalsIgnoreCase("No Filter"))
            System.out.println(ConsoleColors.YELLOW+"List of Users Liked By You :"+ConsoleColors.RESET);
        else if(!choiceFilter.equalsIgnoreCase("Both"))
            System.out.println(ConsoleColors.YELLOW+"List of Users Liked By You sorted by"+choiceFilter+":"+ConsoleColors.RESET);
        else
            System.out.println(ConsoleColors.YELLOW+"List of Users Liked By You sorted by Age And City:"+ConsoleColors.RESET);
            System.out.println();
            while (true) {
                System.out.println(ConsoleColors.YELLOW+"Contact information is only visible when user likes you back!"+ConsoleColors.RESET);
                System.out.println();
                System.out.println(ConsoleColors.GREEN_BOLD+"# Profile - "+count+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Name                : "+temp.data.getFirst_name()+" "+temp.data.getLast_name()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Birth Date          : "+temp.data.getBirth_date()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Age                 : "+temp.data.getAge()+" years"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Height              : "+temp.data.getHeight()+" cm"+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender              : "+temp.data.getGender()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Gender Preferences  : "+temp.data.getGender_preference()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Dietary Preferences : "+temp.data.getDietary_choice()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"City                : "+temp.data.getCity()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"State               : "+temp.data.getState()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Bio                 : "+temp.data.getBio()+ConsoleColors.RESET);
                double compatibility = calculateCompatibility(Session.getCurrentUsername(), temp.data.getUsername()+ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN+"Compatibility Score : " + Math.round(compatibility*100.0)/100.0 + "%"+ConsoleColors.RESET);
                System.out.println("Compatibility Score : " + Math.round(compatibility * 100.0) / 100.0 + "%");
                System.out.println();

                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"Press [O]pen Profile Picture/[S]kip"+ConsoleColors.RESET);
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("O")) {
                        InputStream fis = temp.data.getImage_stream();
                        if (fis == null) {
                            System.out.println(ConsoleColors.YELLOW+"No image provided by user"+ConsoleColors.RESET);
                            break;
                        } else {
                            File dir = new File("C://profile_images");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }

                            File imageFile = new File(dir, temp.data.getUsername() + ".jpg");
                            try {
                                FileOutputStream fos = new FileOutputStream(imageFile);
                                byte buffer[] = new byte[1024];
                                int bytesRead = fis.read(buffer);
                                while (bytesRead != -1) {
                                    fos.write(buffer, 0, bytesRead);
                                    bytesRead = fis.read(buffer);
                                }
                                Desktop.getDesktop().open(imageFile);
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (choice.equalsIgnoreCase("S")) {
                        break;
                    } else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }

                System.out.println();
                while (true) {
                    System.out.println(ConsoleColors.YELLOW+"[P]revious/[N]ext/[B]ack/[U]nlike Profile/[D]ownload profile"+ConsoleColors.RESET);

                    String choice = sc.nextLine();

                    if (choice.equalsIgnoreCase("P")) {
                        if(temp.prev==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Previous User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.prev;
                            count--;
                            break;
                        }

                    } else if (choice.equalsIgnoreCase("N")) {
                        if(temp.next==null)
                        {
                            System.out.println(ConsoleColors.YELLOW+"No Next User Data!"+ConsoleColors.RESET);
                            String choice1;
                            try {
                                choice1= InputUtils.promptUntilValid(ConsoleColors.YELLOW+"Press [B] to go back to filtration page or press [C] to go back to current match profile ?"+ConsoleColors.RESET,s->s.equalsIgnoreCase("B")||s.equalsIgnoreCase("C"),()->new RuntimeException("Blah blah blah"));

                            } catch (RuntimeException e) {
                                return;
                            }
                            System.out.println(ConsoleColors.GREEN+"Back to current match"+ConsoleColors.RESET);
                            break;
                        }
                        else{
                            temp=temp.next;
                            count++;
                            break;
                        }
                    } else if (choice.equalsIgnoreCase("D")) {
                        MatchEngine m1 = new MatchEngine();
                        m1.findMutualLikes();
                        if (m1.mutualLikes.contains(temp.data))
                            new UserManager().generateUserProfile(temp.data, true);
                        else
                            new UserManager().generateUserProfile(temp.data, false);
                    }else if (choice.equalsIgnoreCase("U")) {
                        new LikeManager().UnlikeUser(temp.data.getUsername());
                        matchEngine.removeUserFromAllLists(temp.data.getUsername());
                    }
                    else if (choice.equalsIgnoreCase("B")) {
                        return;
                    } else {
                        System.out.println(ConsoleColors.RED+"Enter valid input!"+ConsoleColors.RESET);
                    }
                }
            }
        }
    }
    public int takeAgeRangeInput() throws GoBackException
    {

        int choice= Integer.parseInt(InputUtils.promptUntilValid("Enter age range:"+ConsoleColors.CYAN_BOLD+" +\n 1. 21-30 \n 2. 31-40 \n" +
                " 3. 41-50 \n 4. 51-60 \n 5. 61-70 \n " +
                "6. 71-80 \n 7. 81-90 \n 8. 91-100 \n"+ConsoleColors.RESET, MatchDisplay::validAgeRange,()->new GoBackException(ConsoleColors.YELLOW+"User chose to Back"+ConsoleColors.RESET)));
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
    private double calculateCompatibility(String user1, String user2) {
        String sql = "SELECT * FROM compatibility WHERE username = ?";
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps1 = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql)) {

            ps1.setString(1, user1);
            ps2.setString(1, user2);

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            if (rs1.next() && rs2.next()) {
                int total = 6, matches = 0;

                if (rs1.getString("travel").equalsIgnoreCase(rs2.getString("travel"))) matches++;
                if (rs1.getString("morning_person").equalsIgnoreCase(rs2.getString("morning_person"))) matches++;
                if (rs1.getString("sports").equalsIgnoreCase(rs2.getString("sports"))) matches++;
                if (rs1.getString("pets").equalsIgnoreCase(rs2.getString("pets"))) matches++;
                if (rs1.getString("books").equalsIgnoreCase(rs2.getString("books"))) matches++;
                if (rs1.getString("lifestyle").equalsIgnoreCase(rs2.getString("lifestyle"))) matches++;

                return (double) (matches * 100) / (double) total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}