package es.source.code.utils;

public class SingleIntent{

    private static SingleIntent singleInstance;
    private SingleIntent(){}

    public int[][] itemList = new int[4][20];

    public int[][] paidList = new int[4][20];

    public int[] posAnalysis = new int[50];


    public static SingleIntent getSingleInstance(){
        if (singleInstance == null){
            singleInstance = new SingleIntent();
            singleInstance.posAnalysisInit();
        }
        return singleInstance;
    }

    public void addItem(int i, int j){
        itemList[i][j] += 1;
    }

    public void deleteItem(int i, int j){

        if (itemList[i][j] != 0){
            itemList[i][j] -= 1;
        }
    }

    public void moveList(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 20; j++){
                paidList[i][j] = itemList[i][j];
                itemList[i][j] = 0;
            }
        }

    }

    public void alreadyPaid(){
        for (int i = 0; i< 4;i++){
            for (int j = 0; j< 20;j++){
                paidList[i][j] = 0;
            }
        }
    }

    public void positionChanged(int i, boolean bool){
        if (bool){
            posAnalysis[i] = 1;
        }else{
            posAnalysis[i] = -1;
        }
    }

    public void posAnalysisInit(){
        for (int i = 0; i < posAnalysis.length; i++){
            posAnalysis[i] = 0;
        }
    }


}
