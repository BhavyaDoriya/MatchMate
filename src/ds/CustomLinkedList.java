package ds;
import user.User;
import java.util.Scanner;
//username ki linked list

public class CustomLinkedList
{
    class Node
    {
        Node next,prev;
        User data;

        Node(User data)
        {
            this.data=data;
            next=null;
            prev=null;
        }
    }

    Node first=null;

    void insertAtFirst(User data)
    {
        Node n=new Node(data);
        if(first==null)
        {
            first=n;
        }
        else
        {
            n.next=first;
            first.prev=n;
            first=n;
        }
    }

    void insertAtLast(User data)
    {
        Node n=new Node(data);
        if(first==null)
        {
            first=n;
        }
        else
        {
            Node temp=first;
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            n.prev=temp;
            temp.next=n;
        }
    }

    Node deleteAtFirst()
    {
        Node returnNode=first;
        if(first==null)
        {
            System.out.println("No User");
            return returnNode;
        }
        else if(first.next==null)
        {
            first=null;
            return returnNode;
        }
        else
        {
            Node temp=first.next;
            first.next.prev=null;
            first.next=null;
            first=temp;
            return returnNode;
        }

    }

    Node remove(User data)
    {
        if(first==null)
        {
            System.out.println("No User");
            return null;
        }
        else if(first.data==data)
        {
            return deleteAtFirst();
        }
        else
        {
            Node temp=first;
            boolean check=false;
            while(temp!=null)
            {
                if(temp.data==data)
                {
                    check=true;
                    break;
                }
                temp=temp.next;
            }
            if(!check)
            {
                System.out.println("No such user exist");
                return null;
            }
            else
            {
                Node returnNode=temp;
                temp=temp.prev;
                temp.next=null;
                if(returnNode.next!=null)
                {
                    returnNode.next.prev=null;
                }
                returnNode.next=null;
                returnNode.prev=null;
                return returnNode;
            }
        }
    }

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        CustomLinkedList o=new CustomLinkedList();
    }

}