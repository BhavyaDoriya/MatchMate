package ds;
import user.User;
import java.util.Scanner;

public class CustomLinkedList
{
    public class Node
    {
        public Node next,prev;
        public User data;

        public Node(User data)
        {
            this.data=data;
            next=null;
            prev=null;
        }
    }

    public Node first=null;


   public void insertAtLast(User data)
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
    public void removeByUsername(String username) {
        if (this.first != null) {
            Node temp = this.first;
            if (this.first.data.getUsername().equals(username) && this.first.next == null) {
                this.first = null;
            } else if (this.first.data.getUsername().equals(username)) {
                Node del = this.first;
                this.first = this.first.next;
                this.first.prev = null;
                del.next = null;

            } else {
                while(temp != null && !temp.data.getUsername().equals(username)) {
                    temp = temp.next;
                }

                if (temp != null) {
                    if (temp.next != null) {
                        temp.prev.next = temp.next;
                        temp.next.prev = temp.prev;

                    } else {
                        temp.prev.next = null;

                    }

                    temp.next = null;
                    temp.prev = null;

                }
            }
        }
    }
    public boolean contains(User u)
    {
        Node temp=first;
        while (temp!=null)
        {
            if(temp.data.equals(u))
            {
                return true;
            }
            temp=temp.next;
        }
        return false;
    }

}