package model;

public class Customer {
    private String first, last, email ;

    public Customer( String f, String l, String e ){
        first = f;
        last = l;
        email = e;

        if ( ! email.toLowerCase().matches("[^@]+@[^@]+\\.com")) {
            throw new IllegalArgumentException();
        }
    }

    public void setEmail(String email) {
        if ( ! email.toLowerCase().matches("[^@]+@[^@]+\\.com")) {
            throw new IllegalArgumentException();
        }

        this.email = email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public String toString(){
        return first + " " + last + ": " + email;
    }
}
