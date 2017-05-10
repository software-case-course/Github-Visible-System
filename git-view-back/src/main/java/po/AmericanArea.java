package po;

public enum AmericanArea implements Area{
    Alabama("Alabama"),Alaska("Alaska"),Arizona("Arizona"),Arkansas("Arkansas"),California("California"),Colorado("Colorado"),
        Connecticut("Connecticut"),Delaware("Delaware"),Florida("Florida"),Georgia("Georgia"),Hawaii("Hawaii"),Idaho("Idaho"),
        Illinois("Illinois"),Indiana("Indiana"),Iowa("Iowa"),Kansas("Kansas"),Kentucky("Kentucky"),Louisiana("Louisiana"),
        Maine("Maine"),Maryland("Maryland"),Massachusetts("Massachusetts"),Michigan("Michigan"),Minnesota("Minnesota"),
        Mississippi("Mississippi"),Missouri("Missouri"),Montana("Montana"),Nebraska("Nebraska"),Nevada("Nevada"),
        Newhampshire("New+hampshire"),Newjersey("New+jersey"),Newmexico("New+mexico"),NewYork("New+York"),Wyoming("Wyoming"),
        NorthCarolina("North+Carolina"),NorthDakota("North+Dakota"),Ohio("Ohio"),Oklahoma("Oklahoma"),Oregon("Oregon"),
        Pennsylvania("Pennsylvania"),Rhodeisland("Rhode+island"),Southcarolina("South+carolina"),Southdakota("South+dakota"),
        Tennessee("Tennessee"),Texas("Texas"),Utah("Utah"),Vermont("Vermont"),Virginia("Virginia"),Washington("Washington"),
        WestVirginia("West Virginia"),Wisconsin("Wisconsin");
    String value;

    private AmericanArea(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String getName(){
        return value.replace('+', ' ');
    }
}