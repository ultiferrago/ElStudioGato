package edu.auburn.eng.csse.comp3710.team6;

/**
 * Created by Tyler Hoover on 4/25/15.
 */
public class Note {

    private String front; //What is on the front of the study card.
    private String back; //What is on the back of the study card.

    /**
     * Nothing to really do here.
     */
    public Note() {

    }

    public Note(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Sets the front text of this card.
     * @param text - Text for front of card.
     */
    public void setFront(String text) {
        front = text;
    }

    /**
     * Sets the back text of this card.
     * @param text - Text for back of card.
     */
    public void setBack(String text) {
        back = text;
    }

    /**
     * Gets text for front of card.
     * @return - front text.
     */
    public String getFront() {
        if (front == null) {
            return "";
        }
        return front;
    }

    /**
     * Gets text for back of card.
     * @return - back text.
     */
    public String getBack() {
        if (back == null) {
            return "";
        }
        return back;
    }



}
