package sahu.rohit.cuims2;

import java.io.Serializable;

public class Student implements Serializable {
    String title;
    String body;
    String subject;

    public Student(String title, String body, String subject)
    {
        this.title = title;
        this.body = body;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString(){
        return title;
    }
}
