package project;

public class Set {
    private setState state;
    private int state1 = 999;
    private int state2 = 1000;
    
    public Set(){
        state = new Silver();
    }
    
    public void set(Customer c){
        int points = c.getPoints();
        
        if (points >= state2){
            state = new Gold();
        }
        else if (points <= state1) {
            state = new Silver();
        }
    }
    
    public setState getState() {
        return state;
    }
}
