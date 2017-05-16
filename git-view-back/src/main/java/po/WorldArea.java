package po;

public enum WorldArea implements Area{
    China("China"),USA("USA"),USA_1("United+States"),Hungary("Hungary"),Germany("Germany"),UK("UK"),France("France")
    ,Canada("Canada"),Spain("Spain"),India("India"),Japan("Japan"),Brazil("Brazil"),Russia("Russia"),Australia("Australia"),
    Netherlands("Netherlands"),Switzerland("Switzerland"),Ukraine("Ukraine"),Sweden("Sweden"),Italy("Italy"),Poland("Poland"),
    Estonia("Estonia"),Belgium("Belgium"),Greece("Greece"),Norway("Norway"),Mexico("Mexico");
    String value;

    private WorldArea(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String getName(){
        return value.replace('+', ' ');
    }
}