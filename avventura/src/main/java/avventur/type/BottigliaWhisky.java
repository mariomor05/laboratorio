
package avventur.type;


public class BottigliaWhisky extends ObjectGame{
    private boolean piena=true;
    
    
    public BottigliaWhisky(int id, String name, String description, boolean piena,String posizione) {
         super(id, name, description,posizione);
         this.piena=piena;
    }
    
   public void setPiena(boolean piena){
       this.piena=piena;
   }
    
   public boolean getPiena(){
       return piena;
   } 
}
